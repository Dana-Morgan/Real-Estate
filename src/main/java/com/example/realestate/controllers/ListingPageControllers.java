package com.example.realestate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import com.example.realestate.models.Property;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListingPageControllers {
    @FXML
    private TilePane tilePane;

    @FXML
    private TextField propertyTypeTextField, roomsTextField, areaTextField,
            priceTextField, locationTextField, statusTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button searchButton;

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @FXML
    public void initialize() {
        loadAllProperties();
        searchButton.setOnAction(event -> searchProperties());
    }

    private void loadAllProperties() {
        loadProperties("FROM Property");
    }

    private void searchProperties() {
        tilePane.getChildren().clear();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            StringBuilder hql = new StringBuilder("FROM Property WHERE 1=1");
            Map<String, Object> parameters = new HashMap<>();

            String propertyType = propertyTypeTextField.getText().trim();
            String rooms = roomsTextField.getText().trim();
            String area = areaTextField.getText().trim();
            String price = priceTextField.getText().trim();
            String location = locationTextField.getText().trim();
            String status = statusTextField.getText().trim();
            String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

            if (!propertyType.isEmpty()) {
                hql.append(" AND LOWER(propertyType) LIKE :propertyType");
                parameters.put("propertyType", "%" + propertyType.toLowerCase() + "%");
            }

            if (!rooms.isEmpty()) {
                try {
                    int roomCount = Integer.parseInt(rooms);
                    hql.append(" AND numberOfRooms = :roomCount");
                    parameters.put("roomCount", roomCount);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid room number format: " + rooms);
                }
            }

            if (!area.isEmpty()) {
                hql.append(" AND LOWER(area) LIKE :area");
                parameters.put("area", "%" + area.toLowerCase() + "%");
            }
            if (!price.isEmpty()) {
                try {
                    String cleanedPrice = price.replaceAll("[^\\d.]", "");
                    if (!cleanedPrice.isEmpty()) {
                        hql.append(" AND price = :price");
                        parameters.put("price", cleanedPrice);
                    }
                } catch (Exception e) {
                    System.err.println("Price error: " + e.getMessage());
                }
            }
            if (!location.isEmpty()) {
                hql.append(" AND location LIKE :location");
                parameters.put("location", "%" + location + "%");
            }


            if (!status.isEmpty()) {
                hql.append(" AND LOWER(status) LIKE :status");
                parameters.put("status", "%" + status.toLowerCase() + "%");
            }

            if (datePicker.getValue() != null) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(datePicker.getValue());
                hql.append(" AND date = :date");
                parameters.put("date", sqlDate);
            }

            Query<Property> query = session.createQuery(hql.toString(), Property.class);
            parameters.forEach(query::setParameter);

            List<Property> properties = query.list();

            if (properties.isEmpty()) {
                showAlert("Information", "No properties found matching your search criteria.");
                return;
            }

            for (Property property : properties) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Card.fxml"));
                    AnchorPane card = loader.load();
                    updateCardComponents(card, property);
                    tilePane.getChildren().add(card);
                } catch (IOException e) {
                    showAlert("Error", "Failed to load property card template: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            showAlert("Database Error", "Failed to perform search: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadProperties(String hqlQuery) {
        tilePane.getChildren().clear();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Property> properties = session.createQuery(hqlQuery, Property.class).getResultList();

            if (properties.isEmpty()) {
                showAlert("Information", "No properties found.");
                return;
            }

            for (Property property : properties) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Card.fxml"));
                    AnchorPane card = loader.load();
                    updateCardComponents(card, property);
                    tilePane.getChildren().add(card);
                } catch (IOException e) {
                    showAlert("Error", "Failed to load property card template: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            showAlert("Database Error", "Failed to load properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateCardComponents(AnchorPane card, Property property) {
        Label nameLabel = (Label) card.lookup("#nameLabel");
        Label priceLabel = (Label) card.lookup("#priceLabel");
        Label statusLabel = (Label) card.lookup("#statusLabel");
        Label areaLabel = (Label) card.lookup("#areaLabel");
        Label descriptionLabel = (Label) card.lookup("#descriptionLabel");
        Label locationLabel = (Label) card.lookup("#locationLabel");
        Label roomCountLabel = (Label) card.lookup("#roomCountLabel");
        Label propertyTypeLabel = (Label) card.lookup("#propertyTypeLabel");
        ImageView imageView = (ImageView) card.lookup("#imageView");

        nameLabel.setText(property.getName());
        priceLabel.setText("$" + property.getPrice());
        statusLabel.setText(property.getStatus());
        areaLabel.setText(property.getArea() + " m²");
        locationLabel.setText(property.getLocation());
        roomCountLabel.setText(property.getNumberOfRooms() + " rooms");
        propertyTypeLabel.setText(property.getPropertyType());

        if (property.getPropertyFeatures() != null) {
            descriptionLabel.setText(property.getPropertyFeatures());
        }

        String imageUrl = property.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                imageView.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + imageUrl);
                imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/realestate/images/aqar.jpg")));
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
