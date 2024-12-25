package com.example.realestate.controllers;

import com.example.realestate.models.Agent;
import com.example.realestate.services.AgentDOA;
import com.example.realestate.validation.ValiditionAgentAccount;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgentUpdateController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField LCField;

    private AgentDOA agentDOA;
    private Agent agent;

    public void setAgent(Agent agent, AgentDOA agentDOA) {
        this.agent = agent;
        this.agentDOA = agentDOA;

        // Pre-fill fields with agent data
        nameField.setText(agent.getName());
        emailField.setText(agent.getEmail());
        phoneField.setText(agent.getPhone());
        passwordField.setText(agent.getPassword());
        LCField.setText(agent.getName());
    }


    @FXML
    private void onUpdateButtonClick() {
        // Validate agent object
        if (agent == null) {
            showAlert("Error", "No agent selected for updating.");
            return;
        }

        // Update agent object with new values from input fields
        agent.setName(nameField.getText());
        agent.setEmail(emailField.getText());
        agent.setPhone(phoneField.getText());
        agent.setPassword(passwordField.getText());
        agent.setLicenseNumber(LCField.getText());

        // Validate all inputs
        String validationMessage = ValiditionAgentAccount.validateAllInputs(
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                passwordField.getText(),
                LCField.getText() // Assuming license is also a required input
        );

        if (validationMessage != null) {
            showAlert("Validation Error", validationMessage); // Show validation error
            return; // Stop update if validation fails
        }

        // Update agent in database
        try {
            agentDOA.update(agent); // Assuming update logic is correctly implemented in DAO
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