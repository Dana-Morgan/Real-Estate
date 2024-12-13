package com.example.realestate.controllers;
import com.example.realestate.models.Property;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropertyTableController {

    @FXML
    private TableView<Property> propertyTable;

    @FXML
    public void initialize() {
        // Define Table Columns
        TableColumn<Property, String> propertiesCol = new TableColumn<>("Properties");
        TableColumn<Property, String> typeCol = new TableColumn<>("Property Type");
        TableColumn<Property, Integer> roomsCol = new TableColumn<>("Number Of Rooms");
        TableColumn<Property, String> featuresCol = new TableColumn<>("Property Features");
        TableColumn<Property, String> areaCol = new TableColumn<>("Area");
        TableColumn<Property, String> statusCol = new TableColumn<>("Property Status");

        // Assign data properties
        propertiesCol.setCellValueFactory(new PropertyValueFactory<>("propertyName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("propertyType"));
        roomsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        featuresCol.setCellValueFactory(new PropertyValueFactory<>("propertyFeatures"));
        areaCol.setCellValueFactory(new PropertyValueFactory<>("area"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Add Columns to TableView
        propertyTable.getColumns().addAll(propertiesCol, typeCol, roomsCol, featuresCol, areaCol, statusCol);

        // Add sample data
        propertyTable.getItems().addAll(
                new Property("Ave Home", "Residential", 5, "Spacious family home with a modern kitchen and backyard garden.", "250 m²", "Available"),
                new Property("Modern Office", "Commercial", 3, "Modern office with internet connection and furnished workspace.", "120 m²", "Rented"),
                new Property("Green Plot", "Land", 0, "Flat green land with trees and ready for development.", "500 m²", "Sold"),
                new Property("Beach Villa", "Vacation", 4, "Beachfront villa with a private pool and terrace.", "300 m²", "Pending")
        );
    }
}
