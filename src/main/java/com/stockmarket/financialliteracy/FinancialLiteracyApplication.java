package com.stockmarket.financialliteracy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = {"com.stockmarket.financialliteracy.repository.neo4j"})
@EnableJpaRepositories(basePackages = {"com.stockmarket.financialliteracy.repository.postgres"})
public class FinancialLiteracyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialLiteracyApplication.class, args);
    }

}
