package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import com.example.realestate.services.PropertyDAO;
import com.example.realestate.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import com.example.realestate.services.PropertyDAOImpl;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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

    private final PropertyDAO propertyDAO = new PropertyDAOImpl();


    private String userRole;
    @FXML
    public Button homeButton;

    @FXML
    public void initialize() {
        loadAllProperties();
        searchButton.setOnAction(event -> searchProperties());
    }

    private void loadAllProperties() {
        List<Property> properties = propertyDAO.getAllProperties();
        if (properties != null && !properties.isEmpty()) {
            displayProperties(properties);
        } else {
            showAlert("Information", "No properties found.");
        }
    }
    private void searchProperties() {
        Map<String, Object> filters = new HashMap<>();

        // Get and clean input values
        String propertyType = propertyTypeTextField.getText().trim();
        String rooms = roomsTextField.getText().trim();
        String area = areaTextField.getText().trim();
        String price = priceTextField.getText().trim();
        String location = locationTextField.getText().trim();
        String status = statusTextField.getText().trim();

        // Add filters only if they're not empty
        if (!propertyType.isEmpty()) {
            filters.put("LOWER(propertyType)", propertyType.toLowerCase());
        }

        if (!rooms.isEmpty()) {
            try {
                filters.put("numberOfRooms", Integer.parseInt(rooms));
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid room number format");
                return;
            }
        }

        if (!area.isEmpty()) {
            filters.put("LOWER(area)", area.toLowerCase());
        }

        if (!price.isEmpty()) {
            try {
                String cleanedPrice = price.replaceAll("[^\\d.]", "");
                if (!cleanedPrice.isEmpty()) {
                    filters.put("price", Double.parseDouble(cleanedPrice));
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid price format");
                return;
            }
        }

        if (!location.isEmpty()) {
            filters.put("LOWER(location)", location.toLowerCase());
        }

        if (!status.isEmpty()) {
            filters.put("LOWER(status)", status.toLowerCase());
        }

        if (datePicker.getValue() != null) {
            filters.put("date", java.sql.Date.valueOf(datePicker.getValue()));
        }

        try {
            List<Property> properties = propertyDAO.searchProperties(filters);
            if (properties != null && !properties.isEmpty()) {
                displayProperties(properties);
            } else {
                showAlert("Information", "No properties found matching your search criteria.");
            }
        } catch (RuntimeException e) {
            showAlert("Error", "Failed to search properties: " + e.getMessage());
        }
    }

    private void displayProperties(List<Property> properties) {
        tilePane.getChildren().clear();
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
    }

    private void updateCardComponents(AnchorPane card, Property property) {
        // تحديث الـ Labels الأخرى
        Label nameLabel = (Label) card.lookup("#nameLabel");
        Label priceLabel = (Label) card.lookup("#priceLabel");
        Label statusLabel = (Label) card.lookup("#statusLabel");
        Label areaLabel = (Label) card.lookup("#areaLabel");
        Label locationLabel = (Label) card.lookup("#locationLabel");
        Label roomCountLabel = (Label) card.lookup("#roomCountLabel");
        Label propertyTypeLabel = (Label) card.lookup("#propertyTypeLabel");
        Label idLabel = (Label) card.lookup("#idLabel"); // إضافة الـ Label لعرض ID
        ImageView imageView = (ImageView) card.lookup("#imageView");

        nameLabel.setText(property.getName());
        priceLabel.setText("$" + property.getPrice());
        statusLabel.setText(property.getStatus());
        areaLabel.setText(property.getArea() + " m²");
        locationLabel.setText(property.getLocation());
        roomCountLabel.setText(property.getNumberOfRooms() + " rooms");
        propertyTypeLabel.setText(property.getPropertyType());
        idLabel.setText("ID: " + property.getId()); // عرض الـ ID في البطاقة

        String imageUrl = property.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                imageView.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + imageUrl);
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void handleNavigateToHomeButton(ActionEvent event) {
        userRole = SessionManager.getUserRole();

        System.out.println("User role from session: " + userRole);

        if (userRole == null) {
            System.out.println("User role is null! Make sure to set it before initialization.");
        } else {
            System.out.println("User role: " + userRole);
        }
        if (Objects.equals(SessionManager.getUserRole(), "Admin")) {
            System.out.println("Navigating to Admin home page");
            navigateTo("/com/example/realestate/views/HomePageForAdmin.fxml", "Admin Home Page");
        } else if (Objects.equals(SessionManager.getUserRole(), "Agent")) {
            System.out.println("Navigating to Agent home page");
            navigateTo("/com/example/realestate/views/HomePageForAgent.fxml", "Agent Home Page");
        }


    }
    private void navigateTo(String fxmlPath, String title) {
        System.out.println("Entering load FXML function.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        try {
            Parent root = loader.load();
            System.out.println("FXML file loaded successfully.");
            Stage stage = (Stage) homeButton.getScene().getWindow();
            Scene scene;
            scene = new Scene(root, 1400, 780);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));

            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading FXML: " + e.getMessage());
}
}}
