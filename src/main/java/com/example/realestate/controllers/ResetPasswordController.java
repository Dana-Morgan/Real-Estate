package com.example.realestate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordController {

    @FXML
    private PasswordField ResetPasswordFeild;

    @FXML
    private Button ResetPasswordbut;

    @FXML
    private Text backbutReset;

    @FXML
    private Button canclebut;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Button backbut1;


    @FXML
    void handleResetPassword(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Login.fxml"));
            Parent newPageRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(newPageRoot);
            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            Stage currentStage = (Stage) canclebut.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void BackbutReset(ActionEvent event) {
        try {
            // Navigate back to the Login page by getting the previous stage (LoginController's stage)
            Stage currentStage = (Stage) backbut1.getScene().getWindow();

            // Go back to the Login page (previous scene)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Login.fxml"));
            Parent loginRoot = loader.load();

            // Set the new scene (Login page)
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
