package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.entity.ListedSecurity;
import com.stockmarket.financialliteracy.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/listedSecurities")
    @Operation(description = "Get all Listed Securities")
    public ResponseEntity getAllListedSecurities(@RequestParam("searchStr") String searchStr) {
        if (StringUtils.hasText(searchStr) && searchStr.length() < 2) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(companyService.getAllListedSecurities(searchStr));
    }
}
