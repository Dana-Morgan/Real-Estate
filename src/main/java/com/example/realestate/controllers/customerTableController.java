package com.example.realestate.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class customerTableController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button AddNewCustomer_btn;
    @FXML
    private Label CustomerTableL;

    @FXML
    private void initialize() {
        AddNewCustomer_btn.setOnAction(event -> {
            try {
                navigateToAddCustomerDetails();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void navigateToAddCustomerDetails() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/customerTable.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) AddNewCustomer_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Customer Details");
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}