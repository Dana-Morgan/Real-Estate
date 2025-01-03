package com.example.realestate.services;

import com.example.realestate.models.Customer;
import java.util.List;


public interface CustomerDAO {
    void save(Customer customer) ;
    List<Customer> getAllCustomers();
    void delete(Customer customer);

}