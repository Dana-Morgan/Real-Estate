package com.example.realestate.controllers;

import com.example.realestate.models.Interaction;
import com.example.realestate.models.Customer;

import com.example.realestate.services.InteractionDAO;
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

public class InteractionDetailsController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(InteractionDetailsController.class.getName());

    private InteractionDAO interactionDAO = new InteractionDOAImpl();
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

        backCID.setOnAction(event -> navigateTo("/com/example/realestate/views/InteractionTable.fxml", "Customer Interaction Table"));

        saveCID.setOnAction(event -> handleSaveInteraction());
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) backCID.getScene().getWindow();

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

            int customerIDInt = Integer.parseInt(customerIDValue);

            Customer customer = interactionDAO.findCustomerById(customerIDInt);
            if (customer == null) {
                showAlert(Alert.AlertType.ERROR, "Customer Not Found", "The customer ID does not exist. Please use an existing customer ID.");
                return;
            }

            if (currentInteraction == null) {
                Interaction interaction = new Interaction();
                interaction.setCustomer(customer);
                interaction.setInteractionType(interactionTypeValue);
                interaction.setInteractionDate(interactionDateValue);

                interaction.setAdditionalNotes(additionalNotesValue);

                interactionDAO.save(interaction);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Interaction added successfully!");
            } else {
                boolean isModified = false;

                if (!currentInteraction.getCustomer().equals(customer)) {
                    currentInteraction.setCustomer(customer);
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
                    interactionDAO.update(currentInteraction);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Interaction updated successfully!");
                } else {
                    showAlert(Alert.AlertType.WARNING, "No Changes", "No changes detected to update.");
                }
            }

            navigateTo("/com/example/realestate/views/InteractionTable.fxml", "Customer Interaction Table");
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

    public void setInteractionDetails(Interaction interaction) {
        if (interaction != null) {
            this.currentInteraction = interaction;
            customerID.setText(String.valueOf(interaction.getCustomer().getCustomerId()));
            interactionType.setValue(interaction.getInteractionType());
            interactionDate.setValue(interaction.getInteractionDate());
            addtionalnotesCID.setText(interaction.getAdditionalNotes());
        }
    }
}
