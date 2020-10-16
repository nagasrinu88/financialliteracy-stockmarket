package com.stockmarket.financialliteracy.service;

import com.stockmarket.financialliteracy.forms.UserForm;
import com.stockmarket.financialliteracy.model.Company;
import com.stockmarket.financialliteracy.model.User;
import com.stockmarket.financialliteracy.repository.neo4j.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<Company> getAllWatchListCompanies(String userName) {
        return userRepository.getAllWatchListCompanies(userName);
    }

    @Transactional
    public User createUser(UserForm userForm) {
        String email = userForm.getEmail();
        String userName = email.split("@")[0];
        User user = User.builder()
                .userName(userName)
                .fullName(userForm.getFullName())
                .email(userForm.getEmail())
                .avatarUrl(userForm.getAvatarUrl())
                .build();
        userRepository.save(user);
        return user;
    }
}
