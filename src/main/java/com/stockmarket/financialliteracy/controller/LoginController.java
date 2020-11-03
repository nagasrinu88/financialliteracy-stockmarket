package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.forms.LogInForm;
import com.stockmarket.financialliteracy.model.User;
import com.stockmarket.financialliteracy.service.LogInService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LogInService logInService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LogInForm logInForm) {
        User user = logInService.doLogin(logInForm);
        return ResponseEntity.ok(user);
    }
}
