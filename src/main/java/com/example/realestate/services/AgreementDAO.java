package com.example.realestate.services;

import com.example.realestate.models.Agreement;

import java.util.List;

public interface AgreementDAO {
    void save(Agreement agreement);
    void update(Agreement agreement);
    void delete(Agreement agreement);
    List<Agreement> getAll();
    Agreement getById(int displayID);

    boolean isCustomerExists(int customerId);
    boolean isPropertyExists(int propertyId);
}
