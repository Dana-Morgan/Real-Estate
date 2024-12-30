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
import java.sql.Date;
import java.util.ResourceBundle;

public class addCustomerDetailsController implements Initializable {
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
    private DatePicker AddDate;
    @FXML
    private Button AddCustomer_btn;
    @FXML
    private Button Back_btn;

    private CustomerDAO customerDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ActivityStatus.getItems().addAll("tenant", "Inactive", "Buyer");
        ActivityStatus.setValue("tenant");

        Preferences.getItems().addAll("house", "villa", "plot of land", "Chalet", "store");
        Preferences.setValue("house");

        customerDAO = new CustomerDAOimp();

        AddCustomer_btn.setOnAction(event -> addCustomer());
        Back_btn.setOnAction(event -> goBack());
    }

    @FXML
    private void addCustomer() {
        if (!validateInputs()) {
            return;
        }

        try {
            Customer customer = new Customer();

            customer.setCustomerName(customerName.getText().trim());
            customer.setCustomerEmail(customerEmail.getText().trim());
            customer.setCustomerPhoneNumber(customerPhoneNumber.getText().trim());
            customer.setCustomerActivityStatus(ActivityStatus.getValue());
            customer.setCustomerPrefernces(Preferences.getValue());
            customer.setAdditionNote(AdditionalNotes.getText().trim());

            if (AddDate.getValue() != null) {
                customer.setAddDate(Date.valueOf(AddDate.getValue()));
            } else {
                customer.setAddDate(new Date(System.currentTimeMillis()));
            }
            customerDAO.save(customer);
            showAlert("Success", "Customer added successfully!", Alert.AlertType.INFORMATION);

            clearFields();

        } catch (Exception e) {
            showAlert("Error", "Error in adding customer: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/customerTable.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) Back_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Table");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        customerName.clear();
        customerEmail.clear();
        customerPhoneNumber.clear();
        AdditionalNotes.clear();
        AddDate.setValue(null);
        ActivityStatus.setValue("tenant");
        Preferences.setValue("house");
    }

    private boolean validateInputs() {
        if (customerName.getText() == null || customerName.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Customer name is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (customerEmail.getText() == null || !customerEmail.getText().contains("@")) {
            showAlert("Validation Error", "A valid email is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (customerPhoneNumber.getText() == null || customerPhoneNumber.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Customer phone number is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (AddDate.getValue() == null) {
            showAlert("Validation Error", "Add date is required!", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
