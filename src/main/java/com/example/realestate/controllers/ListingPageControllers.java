package com.example.realestate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListingPageControllers {

    @FXML
    private TilePane tilePane;

    @FXML
    private TextField propertyTypeTextField, roomsTextField, areaTextField, priceTextField, locationTextField, statusTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button searchButton;

    private final String DB_URL = "jdbc:mysql://localhost:40000/realestate";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Ruaa@276";

    @FXML
    public void initialize() {
        // Load all properties when the page initializes
        loadAllProperties();
        searchButton.setOnAction(event -> searchProperties());
    }

    private void loadAllProperties() {
        loadProperties("SELECT * FROM realestate.property");
    }

    private void searchProperties() {
        tilePane.getChildren().clear();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Build the dynamic search query with prepared statements to prevent SQL injection
            StringBuilder queryBuilder = new StringBuilder(
                    "SELECT * FROM realestate.property WHERE 1=1"
            );

            List<Object> parameters = new ArrayList<>();

            // Get search values and trim whitespace
            String propertyType = propertyTypeTextField.getText().trim();
            String rooms = roomsTextField.getText().trim();
            String area = areaTextField.getText().trim();
            String price = priceTextField.getText().trim();
            String location = locationTextField.getText().trim();
            String status = statusTextField.getText().trim();
            String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

            // Add conditions for each non-empty field
            if (!propertyType.isEmpty()) {
                queryBuilder.append(" AND LOWER(property_type) LIKE LOWER(?)");
                parameters.add("%" + propertyType + "%");
            }

            if (!rooms.isEmpty()) {
                try {
                    int roomCount = Integer.parseInt(rooms);
                    queryBuilder.append(" AND number_of_rooms = ?");
                    parameters.add(roomCount);
                } catch (NumberFormatException e) {

                    System.err.println("Invalid room number format: " + rooms);
                }
            }

            if (!area.isEmpty()) {
                queryBuilder.append(" AND LOWER(area) LIKE LOWER(?)");
                parameters.add("%" + area + "%");
            }
            if (!price.isEmpty()) {
                try {
                    String cleanedPrice = price.replaceAll("[^\\d.]", "");

                    if (!cleanedPrice.isEmpty()) {
                        double searchPrice = Double.parseDouble(cleanedPrice);

                        queryBuilder.append(" AND price = ?");
                        parameters.add(searchPrice);
                    } else {
                        System.err.println("Invalid price format: " + price);
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Error parsing price: " + e.getMessage());
                }
            }


            if (!location.isEmpty()) {
                queryBuilder.append(" AND LOWER(location) LIKE LOWER(?)");
                parameters.add("%" + location + "%");
            }

            if (!status.isEmpty()) {
                queryBuilder.append(" AND LOWER(status) LIKE LOWER(?)");
                parameters.add("%" + status + "%");
            }

            if (!date.isEmpty()) {
                queryBuilder.append(" AND date = ?");
                parameters.add(date);
            }

            // Prepare and execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Card.fxml"));
                    AnchorPane card = loader.load();

                    // Update card with property data
                    Label nameLabel = (Label) card.lookup("#nameLabel");
                    Label priceLabel = (Label) card.lookup("#priceLabel");
                    Label statusLabel = (Label) card.lookup("#statusLabel");
                    Label areaLabel = (Label) card.lookup("#areaLabel");
                    Label descriptionLabel = (Label) card.lookup("#descriptionLabel");
                    Label locationLabel = (Label) card.lookup("#locationLabel");
                    Label roomCountLabel = (Label) card.lookup("#roomCountLabel");
                    Label propertyTypeLabel = (Label) card.lookup("#propertyTypeLabel");
                    ImageView imageView = (ImageView) card.lookup("#imageView");

                    // Set the data
                    nameLabel.setText(resultSet.getString("name"));
                    priceLabel.setText("$" + resultSet.getString("price"));
                    statusLabel.setText(resultSet.getString("status"));
                    areaLabel.setText(resultSet.getString("area") + " m²");
                    locationLabel.setText(resultSet.getString("location"));
                    roomCountLabel.setText(resultSet.getString("number_of_rooms") + " rooms");
                    propertyTypeLabel.setText(resultSet.getString("property_type"));

                    if (resultSet.getString("property_features") != null) {
                        descriptionLabel.setText(resultSet.getString("property_features"));
                    }

                    // Load image
                    String imageUrl = resultSet.getString("image");
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        try {
                            Image image = new Image(imageUrl);
                            imageView.setImage(image);
                        } catch (Exception e) {
                            System.err.println("Failed to load image: " + imageUrl);
                            imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/realestate/images/aqar.jpg")));
                        }
                    }

                    tilePane.getChildren().add(card);

                } catch (IOException e) {
                    showAlert("Error", "Failed to load property card template: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (!hasResults) {
                showAlert("Information", "No properties found matching your search criteria.");
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to perform search: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadProperties(String query) {
        tilePane.getChildren().clear();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Card.fxml"));
                    AnchorPane card = loader.load();

                    // Update card with property data
                    Label nameLabel = (Label) card.lookup("#nameLabel");
                    Label priceLabel = (Label) card.lookup("#priceLabel");
                    Label statusLabel = (Label) card.lookup("#statusLabel");
                    Label areaLabel = (Label) card.lookup("#areaLabel");
                    Label descriptionLabel = (Label) card.lookup("#descriptionLabel");
                    Label locationLabel = (Label) card.lookup("#locationLabel");
                    Label roomCountLabel = (Label) card.lookup("#roomCountLabel");
                    Label propertyTypeLabel = (Label) card.lookup("#propertyTypeLabel"); // New label for property type
                    ImageView imageView = (ImageView) card.lookup("#imageView");

                    // Set the data
                    nameLabel.setText(resultSet.getString("name"));
                    priceLabel.setText("$" + resultSet.getString("price"));
                    statusLabel.setText(resultSet.getString("status"));
                    areaLabel.setText(resultSet.getString("area") + " m²");

                    // Set additional data
                    locationLabel.setText(resultSet.getString("location"));
                    roomCountLabel.setText(resultSet.getString("number_of_rooms") + " rooms");
                    propertyTypeLabel.setText(resultSet.getString("property_type")); // Set property type

                    // Set description if it exists in your database
                    if (resultSet.getString("property_features") != null) {
                        descriptionLabel.setText(resultSet.getString("property_features"));
                    }

                    // Load image
                    String imageUrl = resultSet.getString("image");
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        try {
                            Image image = new Image(imageUrl);
                            imageView.setImage(image);
                        } catch (Exception e) {
                            System.err.println("Failed to load image: " + imageUrl);
                            // Set default image
                            imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/realestate/images/aqar.jpg")));
                        }
                    }

                    tilePane.getChildren().add(card);

                } catch (IOException e) {
                    showAlert("Error", "Failed to load property card template: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (!hasResults) {
                showAlert("Information", "No properties found matching your search criteria.");
            }

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}