package com.example.realestate.models;

public class PropertyDetails {
    private String image;
    private String name;
    private String location;
    private String price;

    public PropertyDetails(String image, String name, String location, String price) {
        this.image = image;
        this.name = name;
        this.location = location;
        this.price = price;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "{" +
                "\"image\":\"" + image + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"location\":\"" + location + "\"," +
                "\"price\":\"" + price + "\"" +
                "}";
    }
}
