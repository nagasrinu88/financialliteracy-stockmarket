package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.entity.UserCredential;
import com.stockmarket.financialliteracy.forms.SignupForm;
import com.stockmarket.financialliteracy.node.User;
import com.stockmarket.financialliteracy.repository.neo4j.UserRepository;
import com.stockmarket.financialliteracy.repository.postgres.UserCredentialRepository;
import com.stockmarket.financialliteracy.security.AuthProvider;
import com.stockmarket.financialliteracy.security.Role;
import com.stockmarket.financialliteracy.security.oauth2.user.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class AuthenticationService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(SignupForm signUpRequest) {

        String email = signUpRequest.getLoginId();
        String password = signUpRequest.getPassword();

        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            throw new RuntimeException("Email/Password shouldnot be empty");
        }

        UserCredential userCredential = userCredentialRepository.findByEmail(email).orElse(null);
        if (Objects.nonNull(userCredential)) {
            if (!userCredential.getProvider().equals(AuthProvider.valueOf(AuthProvider.NATIVE.name()))) {
                throw new RuntimeException("You already signed up with " +
                        userCredential.getProvider() + " account. Please use your " + userCredential.getProvider() +
                        " account to login.");
            }
            throw new RuntimeException("You already registered! Please login.");
        }

        saveUserCredentails("", email, password, AuthProvider.NATIVE);
        saveUserNode("", email, "");
    }

    private void saveUserCredentails(String fullName, String email, String password, AuthProvider authProvider) {

        UserCredential userCredential = new UserCredential();
        userCredential.setName(fullName);
        userCredential.setEmail(email);
        userCredential.setProvider(authProvider);
        userCredential.setRole(Role.USER);
        userCredential.setPassword(passwordEncoder.encode(password));

        userCredentialRepository.save(userCredential);
    }

    private void saveUserNode(String fullName, String email, String avatarUrl) {
        User existingUser = userRepository.findByEmail(email);
        if (Objects.isNull(existingUser)) {
            String userName = email.split("@")[0];
            User user = User.builder()
                    .userName(userName)
                    .fullName(fullName)
                    .email(email)
                    .avatarUrl(avatarUrl)
                    .build();
            userRepository.save(user);
        }
    }

    @Transactional
    public boolean processOAuth2User(OAuth2UserInfo oAuth2UserInfo) {

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }

        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserInfo.getProvider());

        UserCredential userCredential = userCredentialRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
        if (Objects.nonNull(userCredential)) {
            if (!userCredential.getProvider().equals(authProvider)) {
                throw new RuntimeException("You already signed up with " +
                        userCredential.getProvider() + " account. Please use your " + userCredential.getProvider() +
                        " account to login.");
            }
        } else {
            saveUserCredentails(oAuth2UserInfo.getName(), oAuth2UserInfo.getEmail(), "", authProvider);
            saveUserNode(oAuth2UserInfo.getName(), oAuth2UserInfo.getEmail(), oAuth2UserInfo.getImageUrl());
        }
        return true;
    }
}
