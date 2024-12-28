package com.example.realestate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ForgetPasswordController {

    @FXML
    private Button backButton;

    @FXML
    private void handleBackButton() {
        try {
            // Load the login scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Login.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
