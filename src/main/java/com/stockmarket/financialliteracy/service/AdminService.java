package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.model.DailySecurityPrice;
import com.stockmarket.financialliteracy.model.ListedSecurity;
import com.stockmarket.financialliteracy.repository.postgres.DailySecurityPriceRepository;
import com.stockmarket.financialliteracy.repository.postgres.ListedSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminService {

    private final ArchieveDailySecurityPriceService archieveDailySecurityPriceService;

    private final DailySecurityPriceRepository dailySecurityPriceRepository;

    private final ListedSecurityRepository listedSecurityRepository;

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

    public boolean saveListedSecurities(List<ListedSecurity> listedSecurities, String exchange) {
        boolean result = false;
        try {
            listedSecurities.stream().forEach(listedSecurity -> {
                listedSecurity.setExchange(exchange);
                listedSecurity.setCompanyName(listedSecurity.getCompanyName().toUpperCase());
            });
            listedSecurityRepository.saveAll(listedSecurities);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
