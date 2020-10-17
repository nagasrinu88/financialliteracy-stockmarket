package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.model.Company;
import com.stockmarket.financialliteracy.model.ListedSecurity;
import com.stockmarket.financialliteracy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping(value = "{userName}/watchlistCompanies")
    @Operation(description = "Get all watchList companies")
    public List<Company> getAllWatchListCompanies(@PathVariable String userName) {
        return userService.getAllWatchListCompanies(userName);
    }

    @GetMapping(value = "watchlist/listedSecurities")
    @Operation(description = "Get all Listed Securities")
    public List<ListedSecurity> getAllListedSecurities(@RequestParam("searchStr") String searchStr) {
        if (StringUtils.hasText(searchStr) && searchStr.length() > 1) {

        }
        return userService.getAllListedSecurities(searchStr);
    }
}
