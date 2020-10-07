package com.stockmarket.financialliteracy.repository.postgres;

import com.stockmarket.financialliteracy.model.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {
}
