package com.example.realestate.models;

import java.util.Arrays;
import java.util.List;

public class Property {
    private static final List<String> PROPERTY_TYPES = Arrays.asList("Residential", "Commercial", "Industrial");
    private static final List<String> STATUSES = Arrays.asList("Available", "Sold", "Rented");

    private String properties; // JSON string containing image, name, location, and price
    private String propertyType;
    private int numberOfRooms;
    private String propertyFeatures;
    private String area;
    private String status;

    public Property(PropertyDetails details, String propertyType, String status, int numberOfRooms, String propertyFeatures, String area) {
        if (!PROPERTY_TYPES.contains(propertyType)) {
            throw new IllegalArgumentException("Invalid property type. Must be one of: " + PROPERTY_TYPES);
        }
        if (!STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status. Must be one of: " + STATUSES);
        }

        this.properties = details.toString(); // Convert details to JSON string
        this.propertyType = propertyType;
        this.status = status;
        this.numberOfRooms = numberOfRooms;
        this.propertyFeatures = propertyFeatures;
        this.area = area;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(PropertyDetails details) {
        this.properties = details.toString();
    }
}