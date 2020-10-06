package com.stockmarket.financialliteracy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class FinancialliteracyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialliteracyApplication.class, args);
	}

}
