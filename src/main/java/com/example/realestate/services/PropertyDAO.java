package com.example.realestate.services;

import com.example.realestate.models.Property;

import java.util.List;

public interface PropertyDAO {
   List<Property> getAllProperties();
   void deleteProperty(Property property);
}
