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

public class addCustomerDetailsController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField customerID;
    @FXML
    private Label CustomerIDLabel;
    @FXML
    private TextField customerName;
    @FXML
    private Label CustomerNameLabel;
    @FXML
    private TextField customerEmail;
    @FXML
    private Label CustomerEmailLabel;
    @FXML
    private TextField customerPhoneNumber;
    @FXML
    private Label CustomerPhoneNumberLabel;
    @FXML
    private ChoiceBox<String> ActivityStatus;
    @FXML
    private Label ActivityStatusLabel;
    @FXML
    private ChoiceBox<String> Preferences;
    @FXML
    private Label PreferencesLabel;
    @FXML
    private TextField AdditionalNotes;
    @FXML
    private Label AdditionalNotesLabel;
    @FXML
    private DatePicker AddDate;
    @FXML
    private Label AddDateLabel;
    @FXML
    private Button AddCustomer_btn;
    @FXML
    private Button Back_btn;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        ActivityStatus.getItems().addAll("tenant", "Inactive", "Buyer");
        ActivityStatus.setValue("tenant");

        Preferences.getItems().addAll("house", "villa", "plot of land", "Chalet", "store");
        Preferences.setValue("house");

        AddCustomer_btn.setOnAction(event -> addCustomer());
        Back_btn.setOnAction(event -> goBack());
    }

    @FXML
    private void addCustomer() {
        // TODO: Add logic to save the customer details
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
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}