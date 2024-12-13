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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgreementTableController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AgreementTableController.class.getName());

    @FXML
    private Button backbtnAT;

    @FXML
    private Button addAgreementbtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backbtnAT.setOnAction(event -> navigateTo("/com/example/realestate/views/HomePage.fxml", "Home Page"));
        addAgreementbtn.setOnAction(event -> navigateTo("/com/example/realestate/views/AgreementDetails.fxml", "Add Agreement"));
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage;
            if ("Add Agreement".equals(title)) {
                stage = new Stage();
            } else {
                stage = (Stage) backbtnAT.getScene().getWindow();
            }

            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }
}
