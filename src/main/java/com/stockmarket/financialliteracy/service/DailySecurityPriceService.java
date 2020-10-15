package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.model.DailySecurityPrice;
import com.stockmarket.financialliteracy.repository.postgres.DailySecurityPriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DailySecurityPriceService {

    private final ArchieveDailySecurityPriceService archieveDailySecurityPriceService;

    private final DailySecurityPriceRepository dailySecurityPriceRepository;

    public boolean saveDailySecurityPrices(List<DailySecurityPrice> dailySecurityPrices) {
        boolean result = false;
        if (archieveDailySecurityPriceService.archiveDailySecurityPrices()) {
            try {
                dailySecurityPriceRepository.saveAll(dailySecurityPrices);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<DailySecurityPrice> getPrices() {
        return (List<DailySecurityPrice>) dailySecurityPriceRepository.findAll();
    }
}
