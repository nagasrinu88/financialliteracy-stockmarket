package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.model.DailySecurityPrice;
import com.stockmarket.financialliteracy.service.DailySecurityPriceService;
import com.stockmarket.financialliteracy.util.CSVParserUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    private final DailySecurityPriceService dailySecurityPriceService;

    @PostMapping(value = "/uploadDailySecurityPrices", consumes = "multipart/form-data")
    @Operation(description = "Upload Daily Security Prices")
    public ResponseEntity uploadDailySecurityPrices(
            @RequestParam("file") MultipartFile file,
            HttpServletResponse response
    ) throws IOException {
        try {
            List<Map<String, String>> invalidRecords = new ArrayList<>();
            List<DailySecurityPrice> dailySecurityPrices = CSVParserUtil.parseCSVFileAsDailySecurityPrices(file.getInputStream(), invalidRecords);
            if (Objects.nonNull(dailySecurityPrices) && dailySecurityPrices.size() > 0) {
                dailySecurityPriceService.saveDailySecurityPrices(dailySecurityPrices);
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
