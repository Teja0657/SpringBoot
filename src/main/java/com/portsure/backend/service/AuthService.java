package com.portsure.backend.service;

import com.portsure.backend.dto.LoginRequest;
import com.portsure.backend.dto.RegisterRequest;
import com.portsure.backend.model.Investor;
import com.portsure.backend.model.Staff;
import com.portsure.backend.repository.InvestorRepository;
import com.portsure.backend.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private InvestorRepository investorRepo;

    @Autowired
    private StaffRepository staffRepo;

    private static final String STAFF_SECRET = "PORTSURE";

    // --- REGISTER LOGIC ---
    public Object register(RegisterRequest req) {
        if (req.getRole().equals("INVESTOR")) {
            if (investorRepo.existsByEmail(req.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            Investor inv = new Investor();
            inv.setId("INV-" + System.currentTimeMillis()); // Generates INV-1724...
            inv.setName(req.getName());
            inv.setEmail(req.getEmail());
            inv.setPassword(req.getPassword());
            inv.setMobile(req.getMobile());
            inv.setTotalBalance(100000.0); // Default Balance

            return investorRepo.save(inv);

        } else {
            // Staff Registration (Manager/Compliance)
            if (!STAFF_SECRET.equals(req.getStaffKey())) {
                throw new RuntimeException("Invalid Staff Security Key!");
            }
            Staff staff = new Staff();
            String prefix = req.getRole().equals("MANAGER") ? "MGR-" : "CMP-";
            staff.setId(prefix + System.currentTimeMillis());
            staff.setName(req.getName());
            staff.setEmail(req.getEmail());
            staff.setPassword(req.getPassword());
            staff.setMobile(req.getMobile());
            staff.setRole(req.getRole());
            staff.setSecurityKey(req.getStaffKey());

            return staffRepo.save(staff);
        }
    }

    // --- LOGIN LOGIC ---
    public Object login(LoginRequest req) {
        if ("INVESTOR".equals(req.getRole())) {
            // 1. Try finding by Email
            Optional<Investor> inv = investorRepo.findByEmail(req.getEmailOrId());
            // 2. If not found by email, try finding by ID (User can login with either)
            if (inv.isEmpty()) {
                inv = investorRepo.findById(req.getEmailOrId());
            }

            if (inv.isPresent() && inv.get().getPassword().equals(req.getPassword())) {
                return inv.get();
            }
        } else {
            // Check Staff Table
            Optional<Staff> staff = staffRepo.findByEmail(req.getEmailOrId());
            if (staff.isEmpty()) {
                staff = staffRepo.findById(req.getEmailOrId());
            }

            if (staff.isPresent() && staff.get().getPassword().equals(req.getPassword())
                    && staff.get().getRole().equals(req.getRole())) {
                return staff.get();
            }
        }
        throw new RuntimeException("Invalid Credentials");
    }
}