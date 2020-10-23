package com.stockmarket.financialliteracy.repository.postgres;

import com.stockmarket.financialliteracy.entity.DailySecurityPrice;
import org.springframework.data.repository.CrudRepository;

public interface DailySecurityPriceRepository extends CrudRepository<DailySecurityPrice, Long> {
}
