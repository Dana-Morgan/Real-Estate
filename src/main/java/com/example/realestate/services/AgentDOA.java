package com.example.realestate.services;

import com.example.realestate.models.Agent;

import java.util.List;

public interface AgentDOA {
    public void save(Agent agent);
    public void update(Agent agent);
    public void delete(Agent agent);
    public List<Agent> getAll(Agent agent);
    public Agent getByEmail(String Email);

}
