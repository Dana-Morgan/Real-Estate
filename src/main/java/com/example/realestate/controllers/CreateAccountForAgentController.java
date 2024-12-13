package com.example.realestate.controllers;

import com.example.realestate.models.Agent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class CreateAccountForAgentController {

    @FXML
    private TableColumn<Agent, Integer> idTabel;
    @FXML
    private TableColumn<Agent, String> NameColumn;
    @FXML
    private TableColumn<Agent, String> EmailColumn;
    @FXML
    private TableColumn<Agent, String> PhoneColumn;
    @FXML
    private TableColumn<Agent, String> LNColumn;

    @FXML
    private Button CreateBut;

    @FXML
    private TextField EmailSignUp;

    @FXML
    private TextField IdSignUp;

    @FXML
    private TextField LicenseSignUp;

    @FXML
    private TextField NameSignUp;

    @FXML
    private PasswordField PasswordSignUp;

    @FXML
    private TextField PhoneSignUp;

    @FXML
    private Text backbutSignup;

    @FXML
    private Button canclebut;

    @FXML
    private Button backbut1;
    @FXML
    void createAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePage.fxml"));
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
    void backbut(ActionEvent event) {
        try {
            // Navigate back to the Login page by getting the previous stage (LoginController's stage)
            Stage currentStage = (Stage) backbut1.getScene().getWindow();

            // Go back to the Login page (previous scene)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePage.fxml"));
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

