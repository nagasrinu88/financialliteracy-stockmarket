package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.entity.ListedSecurity;
import com.stockmarket.financialliteracy.repository.postgres.ListedSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private ListedSecurityRepository listedSecurityRepository;

    public Page<ListedSecurity> getAllListedSecurities(String searchStr) {
        PageRequest pageRequest = PageRequest.of(0, 100);
        return listedSecurityRepository.findAllBySearchString(searchStr.toUpperCase(), pageRequest);
    }
}
