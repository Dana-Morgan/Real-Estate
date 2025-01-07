package com.example.realestate.services;

import com.example.realestate.models.Agreement;
import com.example.realestate.models.Customer;
import com.example.realestate.models.Property;

import java.util.List;

public interface AgreementDAO {
    void save(Agreement agreement);
    void update(Agreement agreement);
    void delete(Agreement agreement);
    List<Agreement> getAll();
    Agreement getById(int displayID);

    boolean isCustomerExists(int customerId);
    boolean isPropertyExists(int propertyId);
    Customer findCustomerById(int customerID);
    Property findPropertyById(int propertyID);
}