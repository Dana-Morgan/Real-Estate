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
import com.example.realestate.models.Agreement;

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

        backAD.setOnAction(event -> navigateTo("/com/example/realestate/views/AgreementTable.fxml", "Agreement Table"));
        saveAD.setOnAction(event -> navigateTo("/com/example/realestate/views/AgreementTable.fxml", "Agreement Table"));
    }


    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) backAD.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }

    public void setAgreementDetails(Agreement agreement) {
        displayID.setText(agreement.getDisplayID());
        customerID.setText(agreement.getCustomerID());
        propertyID.setText(agreement.getPropertyID());
        offerType.setValue(agreement.getOfferType());
        offerStatus.setValue(agreement.getOfferStatus());
        presentationDate.setValue(agreement.getPresentationDate());
        additionalNotesAD.setText(agreement.getAdditionalNotes());
    }
}
