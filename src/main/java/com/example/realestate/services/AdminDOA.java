package com.example.realestate.services;

import com.example.realestate.models.Admin;
import com.example.realestate.models.Agent;

import java.util.List;

public interface AdminDOA {
    //public void save(Admin admin);

    //public List<Admin> getAll();
    //public Admin getByEmail(String Email);
    Admin login(String email, String password);

}
