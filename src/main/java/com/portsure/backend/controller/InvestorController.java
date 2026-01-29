package com.portsure.backend.controller;

import com.portsure.backend.model.Investor;
import com.portsure.backend.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/investors")
@CrossOrigin(origins = "http://localhost:3000")
public class InvestorController {

    @Autowired
    private PortfolioService portfolioService; // Reusing service for simplicity

    // URL: http://localhost:8080/investors/all
    @GetMapping("/all")
    public List<Investor> getAllInvestors() {
        return portfolioService.getAllInvestors();
    }
}