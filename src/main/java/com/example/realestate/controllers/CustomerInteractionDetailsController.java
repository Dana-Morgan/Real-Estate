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


public class CustomerInteractionDetailsController implements Initializable {
    @FXML
    private TextField ineractionID;

    @FXML
    private TextField customerID;

    @FXML
    private ChoiceBox<String> interactionType;

    @FXML
    private DatePicker interactionDate;

    @FXML
    private TextArea addtionalnotesCID;

    @FXML
    private Button saveCID;

    @FXML
    private Button backCID;

    @FXML
    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/CustomerInteractionDetails.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backCID.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle("CustomerInteractionDetails");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load CustomerInteractionDetails.fxml.");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        interactionType.getItems().addAll("calls", "follow-up", "inquiry");
        interactionType.setValue("calls");
        backCID.setOnAction(event -> handleBackButtonAction());
        saveCID.setOnAction(event -> openCustomerInteractionTable());

    }

private void openCustomerInteractionTable() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/CustomerInteractionTable.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 832);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Agreement Table");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Error: Unable to load AgreementTable.fxml. Check the file path or syntax.");
    }


}
}
