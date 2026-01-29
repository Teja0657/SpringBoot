package com.portsure.backend.dto;

public class LoginRequest {
    private String emailOrId;
    private String password;
    private String role;

    public LoginRequest() {}

    // --- MANUALLY ADDED GETTERS AND SETTERS ---
    public String getEmailOrId() { return emailOrId; }
    public void setEmailOrId(String emailOrId) { this.emailOrId = emailOrId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}