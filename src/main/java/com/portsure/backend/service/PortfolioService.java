package com.portsure.backend.service;

import com.portsure.backend.model.Investor;
import com.portsure.backend.model.Portfolio;
import com.portsure.backend.repository.InvestorRepository;
import com.portsure.backend.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Autowired
    private InvestorRepository investorRepo;

    // Create a new Portfolio
    public Portfolio createPortfolio(String investorId, Portfolio inputPortfolio) {
        // 1. Check Allocation Logic (Must be 100%)
        // FIXED: Renamed variable to remove space (valid Java syntax)
        int totalPrecise = inputPortfolio.getEquityPercent() + inputPortfolio.getBondPercent() + inputPortfolio.getDerivativePercent();

        if (totalPrecise != 100) {
            throw new RuntimeException("Total allocation must be exactly 100%. Current: " + totalPrecise + "%");
        }

        // 2. Fetch Investor to check balance
        Investor investor = investorRepo.findById(investorId)
                .orElseThrow(() -> new RuntimeException("Investor not found"));

        // 3. Calculate currently used balance
        List<Portfolio> existingPortfolios = portfolioRepo.findByInvestor_Id(investorId);
        double usedAmount = existingPortfolios.stream()
                .mapToDouble(Portfolio::getInvestmentAmount)
                .sum();

        // 4. Check if they have enough money
        if (usedAmount + inputPortfolio.getInvestmentAmount() > investor.getTotalBalance()) {
            throw new RuntimeException("Insufficient Balance! Limit: " + investor.getTotalBalance());
        }

        // 5. Save Logic
        inputPortfolio.setPortfolioId("PF-" + System.currentTimeMillis());
        inputPortfolio.setStatus("PENDING");
        inputPortfolio.setInvestor(investor); // Link to the user

        return portfolioRepo.save(inputPortfolio);
    }

    // Get all portfolios for an investor
    public List<Portfolio> getMyPortfolios(String investorId) {
        return portfolioRepo.findByInvestor_Id(investorId);
    }

    // --- MANAGER ACTIONS ---

    public Portfolio approvePortfolio(String portfolioId) {
        Portfolio p = portfolioRepo.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        p.setStatus("APPROVED");
        return portfolioRepo.save(p);
    }

    public void rejectPortfolio(String portfolioId) {
        if(portfolioRepo.existsById(portfolioId)) {
            portfolioRepo.deleteById(portfolioId);
        } else {
            throw new RuntimeException("Portfolio not found");
        }
    }

    // For Directory View (Managers need to see everyone)
    public List<Investor> getAllInvestors() {
        return investorRepo.findAll();
    }
}