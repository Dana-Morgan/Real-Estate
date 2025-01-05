package com.example.realestate.services;

import com.example.realestate.models.Property;
import java.util.List;
import java.util.Map;

public interface PropertyDAO {
   List<Property> getAllProperties();
   void deleteProperty(Property property);
   List<Property> getRandomProperties(int limit);
   List<Property> searchProperties(Map<String, Object> searchCriteria);
   long getPropertyCount();
}