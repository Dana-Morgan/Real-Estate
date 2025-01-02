package com.example.realestate.services;

import com.example.realestate.models.Agent;
import com.example.realestate.models.User;

import java.util.List;

public interface UserDOA {
    public void save(User user);
    public void update(User user);
    public void delete(User user);
    public List<User> getAll();
    public User getByEmail(String Email);
    User login(String email, String password);
    public boolean updatePassword(String email, String newPassword);
}
