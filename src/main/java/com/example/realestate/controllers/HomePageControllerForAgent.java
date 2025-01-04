package com.example.realestate.controllers;

import com.example.realestate.services.*;
import com.example.realestate.models.Property;
import com.example.realestate.utils.SessionManager;
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
import java.util.List;

public class HomePageControllerForAgent {

    private Stage stage;
    private Scene scene;

    private final PropertyDAO propertyDAO;
    private final AgentDAO agentDAO;
    private final UserDOA userDAO;

    @FXML
    private TilePane tilePane;

    @FXML
    private Label propertyCountLabel;

    @FXML
    private Label agentCountLabel;

    @FXML
    private Label userCountLabel;

    private String userRole;

    public HomePageControllerForAgent() {
        this.propertyDAO = new PropertyDAOImpl();
        this.agentDAO = new AgentDAOImpl();
        this.userDAO = new UserDOAImpl();
    }

    @FXML
    public void initialize() {
        loadPropertiesFromDatabase();
        loadPropertyCount();
        loadAgentCount();
        loadUserCount();
    }
 @FXML
    private void loadUserCount() {
        try {
            long userCount = userDAO.getUserCount();
            userCountLabel.setText(String.valueOf(userCount));
        } catch (Exception e) {
            userCountLabel.setText("Error");
            e.printStackTrace();
        }
    }
@FXML
    private void loadAgentCount() {
        try {
            long count = agentDAO.getAgentCount();
            agentCountLabel.setText(String.valueOf(count));
        } catch (Exception e) {
            agentCountLabel.setText("Error");
            e.printStackTrace();
        }
    }
@FXML
    private void loadPropertyCount() {
        try {
            long count = propertyDAO.getPropertyCount();
            propertyCountLabel.setText(String.valueOf(count));
        } catch (Exception e) {
            propertyCountLabel.setText("Error");
            e.printStackTrace();
        }
    }

    private void loadPropertiesFromDatabase() {
        try {
            List<Property> properties = propertyDAO.getRandomProperties(3);
            for (Property property : properties) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/com/example/realestate/views/Card.fxml")
                    );
                    AnchorPane card = loader.load();
                    updateCardComponents(card, property);
                    tilePane.getChildren().add(card);
                } catch (IOException e) {
                    showAlert("Error", "Failed to load property card: " + e.getMessage());
                    e.printStackTrace();
                }
            }
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
        ImageView imageView = (ImageView) card.lookup("#imageView");

        if (nameLabel != null) {
            nameLabel.setText(property.getName() != null ? property.getName() : "");
        }

        if (priceLabel != null) {
            String priceStr = property.getPrice();
            if (priceStr != null && !priceStr.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceStr);
                    priceLabel.setText(String.format("$%.2f", price));
                } catch (NumberFormatException e) {
                    priceLabel.setText("Price not available");
                }
            } else {
                priceLabel.setText("Price not available");
            }
        }

        if (statusLabel != null) {
            statusLabel.setText(property.getStatus() != null ? property.getStatus() : "Status not available");
        }

        if (areaLabel != null) {
            String areaStr = property.getArea();
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

        if (imageView != null && property.getImage() != null) {
            try {
                Image image = new Image(property.getImage());
                imageView.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + property.getImage());
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
        loadPage(event, "/com/example/realestate/views/CustomerTable.fxml");
    }

    @FXML
    public void goToCustomerInteractionTable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/InteractionTable.fxml"));
            Parent root = loader.load();
            InteractionTableController controller = loader.getController();
            controller.setUserRole(SessionManager.getUserRole());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load InteractionTable.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goToAgreementTable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgreementTable.fxml"));
            Parent root = loader.load();
            AgreementTableController controller = loader.getController();
            controller.setUserRole(SessionManager.getUserRole());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load AgreementTable.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goToLoginPage(ActionEvent event) {
        loadPage(event, "/com/example/realestate/views/Login.fxml");
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }
}