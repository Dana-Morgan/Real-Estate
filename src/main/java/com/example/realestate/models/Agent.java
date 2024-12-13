package com.example.realestate.models;

public class Agent {
    private int AgentId;
    private String name;
    private int Email;
    private int Phone;
    private String Password;
    private String LicenseNumber;


    public Agent(int AgentId, String name, int email, int phone, String password, String licenseNumber) {
        this.AgentId = AgentId;
        this.name = name;
        Email = email;
        Phone = phone;
        Password = password;
        LicenseNumber = licenseNumber;
    }

    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int AgentId) {
        this.AgentId = AgentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmail() {
        return Email;
    }

    public void setEmail(int email) {
        Email = email;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }
}
