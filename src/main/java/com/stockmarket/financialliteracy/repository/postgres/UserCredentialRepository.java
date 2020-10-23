package com.stockmarket.financialliteracy.repository.postgres;

import com.stockmarket.financialliteracy.entity.UserCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserCredentialRepository extends CrudRepository<UserCredential, Long> {
    Optional<UserCredential> findByEmail(String email);
}
