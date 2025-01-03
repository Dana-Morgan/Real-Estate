package com.example.realestate.controllers;

import com.example.realestate.models.Agent;
import com.example.realestate.models.User;
import com.example.realestate.services.AgentDOA;
import com.example.realestate.services.UserDOA;
import com.example.realestate.validation.ValiditionAgentAccount;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AgentUpdateController {

    @FXML
    public ChoiceBox<String> roleChoiceBox;
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;


    private UserDOA userDOA;
    private User user;

    public void setAgent(User user, UserDOA userDOA) {
        this.user = user;
        this.userDOA = userDOA;

        // Pre-fill fields with agent data
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        passwordField.setText(user.getPassword());
        roleChoiceBox.setValue(user.getRole());
    }


    @FXML
    private void onUpdateButtonClick() {
        // Validate agent object
        if (user == null) {
            showAlert("Error", "No agent selected for updating.");
            return;
        }

        // Update agent object with new values from input fields
        user.setName(nameField.getText());
        user.setEmail(emailField.getText());
        user.setPhone(phoneField.getText());
        user.setPassword(passwordField.getText());
        String selectedRole = roleChoiceBox.getValue();
        user.setRole(selectedRole);


        // Validate all inputs
        String validationMessage = ValiditionAgentAccount.validateAllInputs(
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                passwordField.getText(),
                selectedRole  // Assuming license is also a required input
        );

        if (validationMessage != null) {
            showAlert("Validation Error", validationMessage); // Show validation error
            return; // Stop update if validation fails
        }

        // Update agent in database
        try {
            userDOA.update(user); // Assuming update logic is correctly implemented in DAO
            showAlert("Success", "Agent updated successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to update agent: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Close the dialog (assume it's a separate stage)
        Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.close();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}