package com.example.realestate.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private static final List<String> PROPERTY_TYPES = Arrays.asList("Residential", "Commercial", "Industrial");
    private static final List<String> STATUSES = Arrays.asList("Available", "Sold", "Rented");

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private String price;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "property_features")
    private String propertyFeatures;

    @Column(name = "area")
    private String area;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private String date;

    public Property() {
    }

    public Property(String image, String name, String location, String price, String propertyType, String status, Integer numberOfRooms, String propertyFeatures, String area, String date) {
        if (!PROPERTY_TYPES.contains(propertyType)) {
            throw new IllegalArgumentException("Invalid property type. Must be one of: " + PROPERTY_TYPES);
        }
        if (!STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status. Must be one of: " + STATUSES);
        }

        this.image = image;
        this.name = name;
        this.location = location;
        this.price = price;
        this.propertyType = propertyType;
        this.status = status;
        this.numberOfRooms = numberOfRooms;
        this.propertyFeatures = propertyFeatures;
        this.area = area;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
