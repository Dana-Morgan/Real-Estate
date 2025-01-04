package com.example.realestate.services;

import com.example.realestate.models.Admin;

public interface AdminDAO {
    //public void save(Admin admin);

    //public List<Admin> getAll();
    //public Admin getByEmail(String Email);
    Admin login(String email, String password);

}
