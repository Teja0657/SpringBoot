package com.portsure.backend.repository;

import com.portsure.backend.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    // Fetches all portfolios for a specific investor ID
    List<Portfolio> findByInvestor_Id(String investorId);
}