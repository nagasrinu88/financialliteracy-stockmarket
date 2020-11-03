package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.node.Company;
import com.stockmarket.financialliteracy.node.User;
import com.stockmarket.financialliteracy.repository.neo4j.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<Company> getAllWatchListCompanies(String email) {
        return userRepository.getAllWatchListCompanies(email);
    }
}
