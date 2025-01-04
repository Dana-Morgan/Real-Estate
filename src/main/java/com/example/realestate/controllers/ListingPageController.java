package com.example.realestate.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;

public class ListingPageController {

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    public void initialize() {

        sortComboBox.setItems(FXCollections.observableArrayList("default", "price", "area"));
    }
}