package com.stockmarket.financialliteracy.forms;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class SignupForm {
    private String loginId;
    private String password;
    private String loginProvider;
    private Map<String, Object> loginPayload;
}
