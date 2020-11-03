package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.entity.DailySecurityPrice;
import com.stockmarket.financialliteracy.entity.ListedSecurity;
import com.stockmarket.financialliteracy.security.CurrentUser;
import com.stockmarket.financialliteracy.security.UserPrincipal;
import com.stockmarket.financialliteracy.service.AdminService;
import com.stockmarket.financialliteracy.util.CSVParserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping(value = "/uploadDailySecurityPrices", consumes = "multipart/form-data")
    @Operation(description = "Upload Daily Security Prices", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity uploadDailySecurityPrices(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<Map<String, String>> invalidRecords = new ArrayList<>();
            List<DailySecurityPrice> dailySecurityPrices = CSVParserUtil.parseCSVFileAsDailySecurityPrices(file.getInputStream(), invalidRecords);
            if (Objects.nonNull(dailySecurityPrices) && !dailySecurityPrices.isEmpty()) {
                adminService.saveDailySecurityPrices(dailySecurityPrices);
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/{exchange}/uploadListedSecurities", consumes = "multipart/form-data")
    @Operation(description = "Upload Listed Securities", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity uploadListedSecurities(
            @RequestParam("file") MultipartFile file,
            @PathVariable("exchange") String exchange
    ) {
        try {
            List<Map<String, String>> invalidRecords = new ArrayList<>();
            List<ListedSecurity> listedSecurities = CSVParserUtil.parseCSVFileAsListedSecurities(file.getInputStream(), invalidRecords);
            if (Objects.nonNull(listedSecurities) && !listedSecurities.isEmpty()) {
                adminService.saveListedSecurities(listedSecurities, exchange.toUpperCase());
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/me")
    @Operation(description = "Test Admin Role", security = {@SecurityRequirement(name = "bearer-key")})
    public String hello(@Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {
        return userPrincipal.getEmail() + " is admin! ";
    }
}
