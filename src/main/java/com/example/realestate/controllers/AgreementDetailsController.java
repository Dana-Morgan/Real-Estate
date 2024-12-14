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

public class AgreementDetailsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AgreementDetailsController.class.getName());

    @FXML
    private TextField displayID;

    @FXML
    private TextField customerID;

    @FXML
    private TextField propertyID;

    @FXML
    private ChoiceBox<String> offerType;

    @FXML
    private ChoiceBox<String> offerStatus;

    @FXML
    private DatePicker presentationDate;

    @FXML
    private TextArea additionalNotesAD;

    @FXML
    private Button saveAD;

    @FXML
    private Button backAD;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        offerType.getItems().addAll("Sale", "Rent", "Lease");
        offerType.setValue("Sale");

        offerStatus.getItems().addAll("Pending", "Accepted", "Rejected");
        offerStatus.setValue("Pending");

        backAD.setOnAction(event -> loadPreviousView());
        saveAD.setOnAction(event -> saveAgreement());
    }

    private void loadPreviousView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgreementTable.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backAD.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load AgreementTable.fxml", e);
        }
    }

    private void saveAgreement() {
        String displayId = displayID.getText();
        String customerId = customerID.getText();
        String propertyId = propertyID.getText();
        String selectedOfferType = offerType.getValue();
        String selectedOfferStatus = offerStatus.getValue();
        String presentationDateValue = presentationDate.getValue().toString();
        String additionalNotes = additionalNotesAD.getText();

        System.out.println("Saving Agreement:");
        System.out.println("Display ID: " + displayId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Property ID: " + propertyId);
        System.out.println("Offer Type: " + selectedOfferType);
        System.out.println("Offer Status: " + selectedOfferStatus);
        System.out.println("Presentation Date: " + presentationDateValue);
        System.out.println("Additional Notes: " + additionalNotes);
    }
}
