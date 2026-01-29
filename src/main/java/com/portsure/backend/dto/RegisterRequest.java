package com.portsure.backend.dto;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String mobile;
    private String role;
    private String staffKey;

    public RegisterRequest() {}

    // --- MANUALLY ADDED GETTERS AND SETTERS ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStaffKey() { return staffKey; }
    public void setStaffKey(String staffKey) { this.staffKey = staffKey; }
}