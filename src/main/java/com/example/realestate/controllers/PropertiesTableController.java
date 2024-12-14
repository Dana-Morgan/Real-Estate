package com.example.realestate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PropertiesTableController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button addPropertyBtn;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}