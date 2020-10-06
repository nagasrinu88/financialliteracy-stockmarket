package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.model.Company;
import com.stockmarket.financialliteracy.model.User;
import com.stockmarket.financialliteracy.repository.neo4j.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<Company> getAllWatchListCompanies(String userName) {
        return userRepository.getAllWatchListCompanies(userName);
    }
}
