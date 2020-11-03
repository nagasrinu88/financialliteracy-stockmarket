package com.stockmarket.financialliteracy.security.oauth2;

import com.stockmarket.financialliteracy.security.AuthProvider;
import com.stockmarket.financialliteracy.security.oauth2.user.GoogleOAuth2UserInfo;
import com.stockmarket.financialliteracy.security.oauth2.user.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.name())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new RuntimeException("Login with " + registrationId + " is not supported yet.");
        }
    }
}