package com.example.realestate.controllers;

import javafx.event.ActionEvent;
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



    public void navigateToAddCustomerDetails(ActionEvent event) throws IOException {
        Stage stage = (Stage) AddNewCustomer_btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/addCustomerDetails.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Customer Details");
        stage.show();

    }

}