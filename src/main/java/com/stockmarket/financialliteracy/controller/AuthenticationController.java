package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.forms.LogInForm;
import com.stockmarket.financialliteracy.forms.SignupForm;
import com.stockmarket.financialliteracy.security.AuthProvider;
import com.stockmarket.financialliteracy.security.JSWTTokenService;
import com.stockmarket.financialliteracy.security.oauth2.OAuth2UserInfoFactory;
import com.stockmarket.financialliteracy.security.oauth2.tokenverify.GoogleTokenVerifier;
import com.stockmarket.financialliteracy.security.oauth2.user.OAuth2UserInfo;
import com.stockmarket.financialliteracy.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JSWTTokenService tokenProvider;

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LogInForm loginRequest) {
        Authentication authentication;
        String email;

        String loginProvider = loginRequest.getLoginProvider();

        if (StringUtils.hasText(loginProvider) && loginProvider.equalsIgnoreCase(AuthProvider.GOOGLE.name())) {
            Map<String, Object> loginPayload = loginRequest.getLoginPayload();

            String idToken = (String) ((Map<String, Object>) loginPayload.get("_token")).get("idToken");

            // google id token integrity check
            googleTokenVerifier.verifyToken(idToken);

            Map<String, Object> profile = (Map<String, Object>) loginPayload.get("_profile");
            OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(AuthProvider.GOOGLE.name(), profile);
            authenticationService.processOAuth2User(oAuth2UserInfo);

            email = oAuth2UserInfo.getEmail();
            authentication = authenticationManager.authenticate(
                    new PreAuthenticatedAuthenticationToken(email, "N/A", null)
            );
        } else {
            email = loginRequest.getLoginId();
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword())
            );
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(email);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public void registerUser(@Valid @RequestBody SignupForm signUpRequest, HttpServletResponse response) {
        authenticationService.registerUser(signUpRequest);
        response.setStatus(HttpStatus.CREATED.value());
    }
}