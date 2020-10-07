package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.model.Company;
import com.stockmarket.financialliteracy.model.Price;
import com.stockmarket.financialliteracy.repository.postgres.PriceRepository;
import com.stockmarket.financialliteracy.service.PriceService;
import com.stockmarket.financialliteracy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final PriceService priceService;


    @GetMapping(value = "{userName}/watchlistCompanies")
    @Operation(description = "Get all watchList companies")
    public List<Company> getAllWatchListCompanies(@PathVariable String userName) {
        return userService.getAllWatchListCompanies(userName);
    }

    //TODO Remove this
    @GetMapping(value = "/prices")
    @Operation(description = "Get all Prices")
    public List<Price> getPrices() {
        return priceService.getPrices();
    }


}
