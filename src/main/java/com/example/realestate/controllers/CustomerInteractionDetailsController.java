package com.example.realestate.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerInteractionDetailsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CustomerInteractionDetailsController.class.getName());

    @FXML
    private TextField ineractionID;

    @FXML
    private TextField customerID;

    @FXML
    private ChoiceBox<String> interactionType;

    @FXML
    private DatePicker interactionDate;

    @FXML
    private TextArea addtionalnotesCID;

    @FXML
    private Button saveCID;

    @FXML
    private Button backCID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        interactionType.getItems().addAll("calls", "follow-up", "inquiry");
        interactionType.setValue("calls");
        backCID.setOnAction(event -> navigateTo("/com/example/realestate/views/CustomerInteractionTable.fxml", "Customer Interaction Table"));
        saveCID.setOnAction(event -> navigateTo("/com/example/realestate/views/CustomerInteractionTable.fxml", "Agreement Table"));
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage;
            if ("Agreement Table".equals(title)) {
                stage = new Stage();
            } else {
                stage = (Stage) backCID.getScene().getWindow();
            }

            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }
}
