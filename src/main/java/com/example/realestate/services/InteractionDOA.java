package com.example.realestate.services;

import com.example.realestate.models.Interaction;

import java.util.List;

public interface InteractionDOA {
    void save(Interaction interaction);
    void update(Interaction interaction);
    void delete(Interaction interaction);
    List<Interaction> getAll();
    Interaction getById(int interactionId);
    boolean isCustomerExist(int customerID); // دالة جديدة للتحقق من وجود العميل
}
