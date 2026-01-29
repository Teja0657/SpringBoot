package com.portsure.backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @Column(name = "portfolio_id")
    private String portfolioId;

    @Column(nullable = false)
    private String name;

    @Column(name = "investment_amount", nullable = false)
    private Double investmentAmount;

    private String status;

    @Column(name = "created_date")
    private String date;

    @Column(name = "equity_percent")
    private int equityPercent;

    @Column(name = "bond_percent")
    private int bondPercent;

    @Column(name = "derivative_percent")
    private int derivativePercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_id", nullable = false)
    @JsonIgnore
    private Investor investor;

    public Portfolio() {}

    // --- MANUALLY ADDED GETTERS AND SETTERS ---
    public String getPortfolioId() { return portfolioId; }
    public void setPortfolioId(String portfolioId) { this.portfolioId = portfolioId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getInvestmentAmount() { return investmentAmount; }
    public void setInvestmentAmount(Double investmentAmount) { this.investmentAmount = investmentAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getEquityPercent() { return equityPercent; }
    public void setEquityPercent(int equityPercent) { this.equityPercent = equityPercent; }

    public int getBondPercent() { return bondPercent; }
    public void setBondPercent(int bondPercent) { this.bondPercent = bondPercent; }

    public int getDerivativePercent() { return derivativePercent; }
    public void setDerivativePercent(int derivativePercent) { this.derivativePercent = derivativePercent; }

    public Investor getInvestor() { return investor; }
    public void setInvestor(Investor investor) { this.investor = investor; }
}