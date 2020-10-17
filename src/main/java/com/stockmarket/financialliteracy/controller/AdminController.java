package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.model.DailySecurityPrice;
import com.stockmarket.financialliteracy.model.ListedSecurity;
import com.stockmarket.financialliteracy.service.AdminService;
import com.stockmarket.financialliteracy.util.CSVParserUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    @Operation(description = "Upload Daily Security Prices")
    public ResponseEntity uploadDailySecurityPrices(
            @RequestParam("file") MultipartFile file,
            HttpServletResponse response
    ) throws IOException {
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
    @Operation(description = "Upload Listed Securities")
    public ResponseEntity uploadListedSecurities(
            @RequestParam("file") MultipartFile file,
            @PathVariable("exchange") String exchange,
            HttpServletResponse response
    ) throws IOException {
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
}
