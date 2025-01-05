package com.example.realestate.controllers;

import com.example.realestate.models.Customer;
import com.example.realestate.services.CustomerDAO;
import com.example.realestate.services.CustomerDAOimp;
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

public class UpdateProController implements Initializable {

    @FXML
    private TextField customerName;
    @FXML
    private TextField customerEmail;
    @FXML
    private TextField customerPhoneNumber;
    @FXML
    private ChoiceBox<String> ActivityStatus;
    @FXML
    private ChoiceBox<String> Preferences;
    @FXML
    private TextField AdditionalNotes;
    @FXML
    private Button cancelButton;

    private CustomerDAO customerDAO;
    private Customer customer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerDAO = new CustomerDAOimp();

        ActivityStatus.getItems().addAll("tenant", "Inactive", "Buyer");
        Preferences.getItems().addAll("house", "villa", "plot of land", "Chalet", "store");
    }

    public void setCustomerData(Customer customer) {
        this.customer = customer;

        customerName.setText(customer.getCustomerName());
        customerEmail.setText(customer.getCustomerEmail());
        customerPhoneNumber.setText(customer.getCustomerPhoneNumber());
        ActivityStatus.setValue(customer.getCustomerActivityStatus());
        Preferences.setValue(customer.getCustomerPrefernces());
        AdditionalNotes.setText(customer.getAdditionNote());
    }

    @FXML
    private void updateCustomer() {
        try {
            if (customerName.getText().isEmpty() || customerEmail.getText().isEmpty()) {
                showAlert("Validation Error", "Name and Email cannot be empty!", Alert.AlertType.ERROR);
                return;
            }
            customer.setCustomerName(customerName.getText().trim());
            customer.setCustomerEmail(customerEmail.getText().trim());
            customer.setCustomerPhoneNumber(customerPhoneNumber.getText().trim());
            customer.setCustomerActivityStatus(ActivityStatus.getValue());
            customer.setCustomerPrefernces(Preferences.getValue());
            customer.setAdditionNote(AdditionalNotes.getText().trim());

            customerDAO.update(customer);
            showAlert("Success", "Customer updated successfully!", Alert.AlertType.INFORMATION);
            goBack();
        } catch (Exception e) {
            showAlert("Error", "Error updating customer: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/customerTable.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Customer Table");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Error navigating back to customer table: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}