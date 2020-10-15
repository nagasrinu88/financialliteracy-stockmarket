package com.stockmarket.financialliteracy.repository.postgres;

import com.stockmarket.financialliteracy.model.DailySecurityPrice;
import org.springframework.data.repository.CrudRepository;

public interface DailySecurityPriceRepository extends CrudRepository<DailySecurityPrice, Long> {
}
