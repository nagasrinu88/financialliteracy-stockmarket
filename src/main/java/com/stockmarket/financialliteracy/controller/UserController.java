package com.stockmarket.financialliteracy.controller;

import com.stockmarket.financialliteracy.model.Company;
import com.stockmarket.financialliteracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{userName}/watchlistCompanies", method = RequestMethod.GET)
    @ResponseBody
    public List<Company> getAllWatchListCompanies(@PathVariable String userName) {
        return userService.getAllWatchListCompanies(userName);
    }

}
