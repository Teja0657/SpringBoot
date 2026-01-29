package com.portsure.backend.repository;

import com.portsure.backend.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvestorRepository extends JpaRepository<Investor, String> {
    // Custom method to find a user by email (used for Login)
    Optional<Investor> findByEmail(String email);

    // Checks if an email already exists (used for Registration)
    boolean existsByEmail(String email);
}