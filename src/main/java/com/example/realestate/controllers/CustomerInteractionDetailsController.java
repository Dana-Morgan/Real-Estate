package com.example.realestate.controllers;

import com.example.realestate.models.Interaction;
import com.example.realestate.services.InteractionDOA;
import com.example.realestate.services.InteractionDOAImpl;
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

public class CustomerInteractionDetailsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CustomerInteractionDetailsController.class.getName());

    private InteractionDOA interactionDOA = new InteractionDOAImpl();
    private Interaction currentInteraction;

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

        saveCID.setOnAction(event -> handleSaveInteraction());
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) backCID.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }

    private void handleSaveInteraction() {
        try {
            String customerIDValue = customerID.getText().trim();
            String interactionTypeValue = interactionType.getValue();
            LocalDate interactionDateValue = interactionDate.getValue();
            String additionalNotesValue = addtionalnotesCID.getText().trim();

            if (customerIDValue.isEmpty() || interactionTypeValue == null || interactionDateValue == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields except Additional Notes are required.");
                return;
            }

            if (currentInteraction == null) {
                Interaction interaction = new Interaction(Integer.parseInt(customerIDValue), interactionTypeValue, interactionDateValue, additionalNotesValue);
                interactionDOA.save(interaction);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Interaction added successfully!");
            } else {
                boolean isModified = false;

                if (currentInteraction.getCustomerID() != Integer.parseInt(customerIDValue)) {
                    currentInteraction.setCustomerID(Integer.parseInt(customerIDValue));
                    isModified = true;
                }

                if (!currentInteraction.getInteractionType().equals(interactionTypeValue)) {
                    currentInteraction.setInteractionType(interactionTypeValue);
                    isModified = true;
                }

                if (!currentInteraction.getInteractionDate().equals(interactionDateValue)) {
                    currentInteraction.setInteractionDate(interactionDateValue);
                    isModified = true;
                }

                if (!currentInteraction.getAdditionalNotes().equals(additionalNotesValue)) {
                    currentInteraction.setAdditionalNotes(additionalNotesValue);
                    isModified = true;
                }

                if (isModified) {
                    interactionDOA.update(currentInteraction);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Interaction updated successfully!");
                } else {
                    showAlert(Alert.AlertType.WARNING, "No Changes", "No changes detected to update.");
                }
            }

            navigateTo("/com/example/realestate/views/CustomerInteractionTable.fxml", "Customer Interaction Table");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input. Please check the data.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setInteractionDetails(Interaction interaction) {
        if (interaction != null) {
            this.currentInteraction = interaction;
            customerID.setText(String.valueOf(interaction.getCustomerID()));
            interactionType.setValue(interaction.getInteractionType());
            interactionDate.setValue(interaction.getInteractionDate());
            addtionalnotesCID.setText(interaction.getAdditionalNotes());
        }
    }
}
