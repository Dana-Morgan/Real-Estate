package com.example.realestate.models;

import javax.persistence.*;


@Entity
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue
    private int AgentId;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String Email;

    @Column(name="phone")
    private String Phone;

    @Column(name="password")
    private String Password;

    @Column(name="licensenumber")
    private String LicenseNumber;

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
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
