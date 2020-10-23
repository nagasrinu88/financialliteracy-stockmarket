package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.entity.ListedSecurity;
import com.stockmarket.financialliteracy.repository.postgres.ListedSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private ListedSecurityRepository listedSecurityRepository;

    public List<ListedSecurity> getAllListedSecurities(String searchStr) {
        return listedSecurityRepository.findAllBySearchString(searchStr.toUpperCase());
    }
}
