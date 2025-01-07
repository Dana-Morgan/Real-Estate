package com.example.realestate.services;

import com.example.realestate.models.Agent;

import java.util.List;

public interface AgentDAO {
    public void save(Agent agent);
    public void update(Agent agent);
    public void delete(Agent agent);
    public List<Agent> getAll();
    public Agent getByEmail(String Email);
    Agent login(String email, String password);
    public boolean updatePassword(String email, String newPassword);

}
