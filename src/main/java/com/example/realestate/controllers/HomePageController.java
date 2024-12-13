package com.example.realestate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    private Stage stage;
    private Scene scene;
    public void goToListingPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/realestate/views/ListingPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
    }
    public void goToHome(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/HomePage.fxml");
    }

    public void goToListing(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/ListingPage.fxml");
    }
    public void goToCustomerTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/customerTable.fxml");
    }
    public void goToCustomerInteractionTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/CustomerInteractionTable.fxml");
    }
    public void goToAgreementTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/AgreementTable.fxml");
    }
    public void goTopropertiesTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/propertiesTable.fxml");
    }
    public void goToAgentTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/Create-Account.fxml");
    }
    private void loadPage(ActionEvent event, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
    }
}


