package com.example.realestate.controllers;

import com.example.realestate.models.Agreement;
import com.example.realestate.services.AgreementDOA;
import com.example.realestate.services.AgreementDOAImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgreementDetailsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AgreementDetailsController.class.getName());

    private AgreementDOA agreementDOA = new AgreementDOAImpl();
    private Agreement currentAgreement;

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

        saveAD.setOnAction(event -> handleSaveAgreement());
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) backAD.getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));

            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }

    public void handleSaveAgreement() {
        try {
            String customerIDValue = customerID.getText().trim();
            String propertyIDValue = propertyID.getText().trim();
            String offerTypeValue = offerType.getValue();
            String offerStatusValue = offerStatus.getValue();
            LocalDate presentationDateValue = presentationDate.getValue();
            String additionalNotesValue = additionalNotesAD.getText().trim();

            if (customerIDValue.isEmpty() || propertyIDValue.isEmpty()
                    || offerTypeValue == null || offerStatusValue == null || presentationDateValue == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields except Additional Notes are required.");
                return;
            }

            // التحقق من وجود الكستمر والبروبرتي
            boolean isCustomerExists = agreementDOA.isCustomerExists(Integer.parseInt(customerIDValue));
            boolean isPropertyExists = agreementDOA.isPropertyExists(Integer.parseInt(propertyIDValue));

            if (!isCustomerExists) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Customer ID does not exist.");
                return;
            }

            if (!isPropertyExists) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Property ID does not exist.");
                return;
            }

            if (currentAgreement == null) {
                Agreement agreement = new Agreement(
                        Integer.parseInt(customerIDValue),
                        Integer.parseInt(propertyIDValue),
                        offerTypeValue,
                        offerStatusValue,
                        presentationDateValue,
                        additionalNotesValue
                );
                agreementDOA.save(agreement);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Agreement added successfully!");
            } else {
                // تحديث الاتفاقية الحالية
                // ... (كود التحديث يبقى كما هو)
            }

            navigateTo("/com/example/realestate/views/AgreementTable.fxml", "Agreement Table");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input. Please check the data.");
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred during save operation", e);
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred. Please try again." + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setAgreementDetails(Agreement agreement) {
        if (agreement != null) {
            this.currentAgreement = agreement;
            customerID.setText(String.valueOf(agreement.getCustomerID()));
            propertyID.setText(String.valueOf(agreement.getPropertyID()));
            offerType.setValue(agreement.getOfferType());
            offerStatus.setValue(agreement.getOfferStatus());
            presentationDate.setValue(agreement.getPresentationDate());
            additionalNotesAD.setText(agreement.getAdditionalNotes());
        }
    }
}
