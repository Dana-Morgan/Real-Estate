package com.example.realestate.models;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue
    private int AdminId;

    @Column(name="name")
    private String name;

    @Column(name= "password")
    private String password;

    @Column(name="email")
    private String email;

    public int getId() {
        return AdminId;
    }

    public void setId(int AdminId) {
        this.AdminId = AdminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
