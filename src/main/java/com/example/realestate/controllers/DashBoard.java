package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashBoard {

    private Stage stage;
    private Scene scene;
    @FXML
    private TilePane tilePane;

    @FXML
    private AnchorPane propertyCardTemplate;

    private List<Property> propertyList = new ArrayList<>();

    @FXML
    public void initialize() {
        loadDataFromDatabase();
    }


    private void loadDataFromDatabase() {
        String url = "jdbc:mysql://localhost:40000/realestate";
        String username = "root";
        String password = "Ruaa@276";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM realestate.property ORDER BY RAND() LIMIT 3")) {

            while (resultSet.next()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Card.fxml"));
                    AnchorPane card = loader.load();

                    updateCardComponents(card, resultSet);

                    tilePane.getChildren().add(card);

                } catch (IOException e) {
                    showAlert("Error", "Failed to load property card template: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateCardComponents(AnchorPane card, ResultSet resultSet) throws SQLException {
        Label nameLabel = (Label) card.lookup("#nameLabel");
        Label priceLabel = (Label) card.lookup("#priceLabel");
        Label statusLabel = (Label) card.lookup("#statusLabel");
        Label areaLabel = (Label) card.lookup("#areaLabel");
        ImageView imageView = (ImageView) card.lookup("#imageView");

        if (nameLabel != null) {
            String name = resultSet.getString("name");
            nameLabel.setText(name != null ? name : "");
        }

        if (priceLabel != null) {
            double price = resultSet.getDouble("price");
            priceLabel.setText(resultSet.wasNull() ? "Price not available" : String.format("$%.2f", price));
        }

        if (statusLabel != null) {
            String status = resultSet.getString("status");
            statusLabel.setText(status != null ? status : "Status not available");
        }

        if (areaLabel != null) {
            String areaStr = resultSet.getString("area");
            if (areaStr != null && !areaStr.isEmpty()) {
                try {
                    double area = Double.parseDouble(areaStr);
                    areaLabel.setText(String.format("%.1f m²", area));
                } catch (NumberFormatException e) {
                    areaLabel.setText("Area not available");
                }
            } else {
                areaLabel.setText("Area not available");
            }
        }

        if (imageView != null) {
            String imageUrl = resultSet.getString("image");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    Image image = new Image(imageUrl);
                    imageView.setImage(image);
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + imageUrl);
                }
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void loadPage(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1280, 832);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + fxmlFile);
            e.printStackTrace();
        }
    }

    @FXML
    public void goToListing(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/Listing.fxml");
    }

    @FXML
    public void goToCustomerTable(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/customerTable.fxml");
    }

    @FXML
    public void goToCustomerInteractionTable(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/CustomerInteractionTable.fxml");
    }

    @FXML
    public void goToAgreementTable(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/AgreementTable.fxml");
    }

    @FXML
    public void goToPropertiesTable(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/propertiesTable.fxml");
    }

    @FXML
    public void goToAgentTable(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/Create-Account.fxml");
    }

    @FXML
    public void goToLoginPage(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/Login.fxml");
    }
}
