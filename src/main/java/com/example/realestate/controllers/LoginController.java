package com.example.realestate.controllers;

import com.example.realestate.models.Admin;
import com.example.realestate.models.Agent;
import com.example.realestate.services.AdminDOAImpl;
import com.example.realestate.services.AgentDOAImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField EmailIDLogin;

    @FXML
    private Text forgetbut;

    @FXML
    private Button loginbut;

    @FXML
    private PasswordField passordLogin;
    private AgentDOAImpl agentDAO = new AgentDOAImpl(); // Instantiate AgentDOAImpl
    private AdminDOAImpl adminDOA = new AdminDOAImpl(); // Instantiate AgentDOAImpl
    private MouseEvent event;

    @FXML
    void handleResetPassword(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/forgetpassword.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) ((Text) event.getSource()).getScene().getWindow();

            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void loginbutton(ActionEvent event) {
        String email = EmailIDLogin.getText();
        String password = passordLogin.getText();

        // Call login method to check credentials
        Agent agent = agentDAO.login(email, password);
        Admin admin = adminDOA.login(email, password);

        if (agent != null || admin != null ) {
            // If login is successful, navigate to the HomePage
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
        } else {
            // If login fails, show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password. Please try again.");
            alert.showAndWait();
        }
    }

}
