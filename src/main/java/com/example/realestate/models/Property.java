package com.example.realestate.models;

public class Property {
    private String propertyName;
    private String propertyType;
    private int numberOfRooms;
    private String propertyFeatures;
    private String area;
    private String status;

    public Property(String propertyName, String propertyType, int numberOfRooms, String propertyFeatures, String area, String status) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.numberOfRooms = numberOfRooms;
        this.propertyFeatures = propertyFeatures;
        this.area = area;
        this.status = status;
    }

    // Getters and Setters
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getPropertyFeatures() {
        return propertyFeatures;
    }

    public void setPropertyFeatures(String propertyFeatures) {
        this.propertyFeatures = propertyFeatures;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
