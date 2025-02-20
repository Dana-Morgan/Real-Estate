package com.example.realestate.controllers;

import com.example.realestate.models.Agreement;
import com.example.realestate.models.Customer;
import com.example.realestate.models.Property;
import com.example.realestate.services.AgreementDAO;
import com.example.realestate.services.AgreementDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgreementDetailsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AgreementDetailsController.class.getName());

    private AgreementDAO agreementDAO = new AgreementDAOImpl();
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

    @FXML
    private TextField pdfFilePath;

    @FXML
    private Button uploadPDF;

    @FXML
    private TextField customerName;
    @FXML
    private TextField propertyName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        offerType.getItems().addAll("Sale", "Rent", "Lease");
        offerType.setValue("Sale");

        offerStatus.getItems().addAll("Pending", "Accepted", "Rejected");
        offerStatus.setValue("Pending");

        backAD.setOnAction(event -> navigateTo("/com/example/realestate/views/AgreementTable.fxml", "Agreement Table"));

        saveAD.setOnAction(event -> handleSaveAgreement());

        uploadPDF.setOnAction(event -> handleUploadPDF());
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
            String pdfPath = pdfFilePath.getText().trim();

            if (customerIDValue.isEmpty() || propertyIDValue.isEmpty()
                    || offerTypeValue == null || offerStatusValue == null || presentationDateValue == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields except Additional Notes are required.");
                return;
            }

            int customerIDInt = Integer.parseInt(customerIDValue);
            int propertyIDInt = Integer.parseInt(propertyIDValue);

            Customer customer = agreementDAO.findCustomerById(customerIDInt);
            Property property = agreementDAO.findPropertyById(propertyIDInt);


            if (customer == null) {
                showAlert(Alert.AlertType.ERROR, "Customer Not Found", "The customer ID does not exist.");
                return;
            }

            if (property == null) {
                showAlert(Alert.AlertType.ERROR, "Property Not Found", "The property ID does not exist.");
                return;
            }

            if (currentAgreement == null) {
                Agreement agreement = new Agreement();
                agreement.setCustomer(customer);
                agreement.setProperty(property);
                agreement.setOfferType(offerTypeValue);
                agreement.setOfferStatus(offerStatusValue);
                agreement.setPresentationDate(presentationDateValue);
                agreement.setAdditionalNotes(additionalNotesValue);
                agreement.setPdfPath(pdfPath);

                agreementDAO.save(agreement);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Agreement added successfully!");
            } else {
                boolean isModified = false;

                if (!currentAgreement.getCustomer().equals(customer)) {
                    currentAgreement.setCustomer(customer);
                    isModified = true;
                }

                if (!currentAgreement.getProperty().equals(property)) {
                    currentAgreement.setProperty(property);
                    isModified = true;
                }

                if (!currentAgreement.getOfferType().equals(offerTypeValue)) {
                    currentAgreement.setOfferType(offerTypeValue);
                    isModified = true;
                }

                if (!currentAgreement.getOfferStatus().equals(offerStatusValue)) {
                    currentAgreement.setOfferStatus(offerStatusValue);
                    isModified = true;
                }

                if (!currentAgreement.getPresentationDate().equals(presentationDateValue)) {
                    currentAgreement.setPresentationDate(presentationDateValue);
                    isModified = true;
                }

                if (!currentAgreement.getAdditionalNotes().equals(additionalNotesValue)) {
                    currentAgreement.setAdditionalNotes(additionalNotesValue);
                    isModified = true;
                }

                if (!currentAgreement.getPdfPath().equals(pdfPath)) {
                    currentAgreement.setPdfPath(pdfPath);
                    isModified = true;
                }

                if (isModified) {
                    agreementDAO.update(currentAgreement);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Agreement updated successfully!");
                } else {
                    showAlert(Alert.AlertType.WARNING, "No Changes", "No changes detected to update.");
                }
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
            customerID.setText(String.valueOf(agreement.getCustomer().getCustomerId()));
            propertyID.setText(String.valueOf(agreement.getProperty().getId()));
            offerType.setValue(agreement.getOfferType());
            offerStatus.setValue(agreement.getOfferStatus());
            presentationDate.setValue(agreement.getPresentationDate());
            additionalNotesAD.setText(agreement.getAdditionalNotes());
            pdfFilePath.setText(agreement.getPdfPath());


        }
    }

    public void handleUploadPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(pdfFilePath.getScene().getWindow());

        if (file != null) {
            pdfFilePath.setText(file.getAbsolutePath());
        }
    }
}
