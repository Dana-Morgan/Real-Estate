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

public class AgreementDetailsController implements Initializable {

    @FXML
    private TextField displayID;

    @FXML
    private TextField customerID;

    @FXML
    private TextField propertyID;

    @FXML
    private ChoiceBox<String> offerType;

    @FXML
    private ChoiceBox<String> offerStatus;

    @FXML
    private DatePicker presentationDate;

    @FXML
    private TextArea additionalNotesAD;

    @FXML
    private Button saveAD;

    @FXML
    private Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        offerType.getItems().addAll("Sale", "Rent", "Lease");
        offerType.setValue("Sale");

        offerStatus.getItems().addAll("Pending", "Accepted", "Rejected");
        offerStatus.setValue("Pending");

        saveAD.setOnAction(event -> openAgreementTable());
    }

    private void openAgreementTable() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgreementTable.fxml"));
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
