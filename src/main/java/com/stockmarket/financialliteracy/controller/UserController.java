package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.node.Company;
import com.stockmarket.financialliteracy.security.CurrentUser;
import com.stockmarket.financialliteracy.security.UserPrincipal;
import com.stockmarket.financialliteracy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/watchlistCompanies")
    @Operation(description = "Get all watchList companies", security = {@SecurityRequirement(name = "bearer-key")})
    public List<Company> getAllWatchListCompanies(@Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {
        String email = userPrincipal.getEmail();
        return userService.getAllWatchListCompanies(email);
    }
}