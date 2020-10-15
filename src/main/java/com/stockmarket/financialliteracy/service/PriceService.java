package com.stockmarket.financialliteracy.service;


import com.stockmarket.financialliteracy.model.Price;
import com.stockmarket.financialliteracy.repository.postgres.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public List<Price> getPrices(){
        return (List<Price>) priceRepository.findAll();
    }
}
