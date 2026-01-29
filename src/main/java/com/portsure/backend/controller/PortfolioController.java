package com.portsure.backend.controller;

import com.portsure.backend.model.Portfolio;
import com.portsure.backend.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
@CrossOrigin(origins = "http://localhost:3000")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    // 1. Create Portfolio (Investor)
    // URL: http://localhost:8080/portfolios/add?investorId=INV-123
    @PostMapping("/add")
    public ResponseEntity<?> createPortfolio(@RequestParam String investorId, @RequestBody Portfolio portfolio) {
        try {
            Portfolio created = portfolioService.createPortfolio(investorId, portfolio);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Get My Portfolios (Investor)
    // URL: http://localhost:8080/portfolios/get?investorId=INV-123
    @GetMapping("/get")
    public List<Portfolio> getMyPortfolios(@RequestParam String investorId) {
        return portfolioService.getMyPortfolios(investorId);
    }

    // 3. Approve Portfolio (Manager)
    // URL: http://localhost:8080/portfolios/approve/PF-123
    @PutMapping("/approve/{portfolioId}")
    public ResponseEntity<?> approvePortfolio(@PathVariable String portfolioId) {
        try {
            return ResponseEntity.ok(portfolioService.approvePortfolio(portfolioId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. Reject Portfolio (Manager)
    // URL: http://localhost:8080/portfolios/reject/PF-123
    @DeleteMapping("/reject/{portfolioId}")
    public ResponseEntity<?> rejectPortfolio(@PathVariable String portfolioId) {
        try {
            portfolioService.rejectPortfolio(portfolioId);
            return ResponseEntity.ok("Portfolio Rejected and Deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}