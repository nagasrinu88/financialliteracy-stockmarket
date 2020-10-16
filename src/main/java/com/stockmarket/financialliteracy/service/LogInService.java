package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.forms.LogInForm;
import com.stockmarket.financialliteracy.forms.SignupForm;
import com.stockmarket.financialliteracy.forms.UserForm;
import com.stockmarket.financialliteracy.model.User;
import com.stockmarket.financialliteracy.repository.neo4j.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@AllArgsConstructor
public class LogInService {

    private UserRepository userRepository;
    private UserService userService;

    public User doLogin(LogInForm logInForm) {
        User user = null;
        if ("google".equalsIgnoreCase(logInForm.getLoginProvider())) {
            Map<String, Object> loginPayload = logInForm.getLoginPayload();
            Map<String, Object> profile = (Map<String, Object>) loginPayload.get("_profile");
            String email = (String) profile.get("email");

            user = userRepository.findByEmail(email);
            // if user not exists then creating
            if (user == null) {
                UserForm userForm = new UserForm();
                userForm.setEmail((String) profile.get("email"));
                userForm.setFullName((String) profile.get("name"));
                userForm.setAvatarUrl((String) profile.get("profilePicURL"));

                user = userService.createUser(userForm);
            }
        }
        return user;
    }

    @Transactional
    public User doSignup(SignupForm signupForm) {
        User user = null;
        if ("google".equalsIgnoreCase(signupForm.getLoginProvider())) {
            Map<String, Object> loginPayload = signupForm.getLoginPayload();
            Map<String, Object> profile = (Map<String, Object>) loginPayload.get("_profile");
            String email = (String) profile.get("email");

            String userName = email.split("@")[0];
            user = User.builder()
                    .userName(userName)
                    .fullName((String) profile.get("name"))
                    .email(email)
                    .avatarUrl((String) profile.get("profilePicURL"))
                    .build();
            userRepository.save(user);
        }
        return user;
    }
}
