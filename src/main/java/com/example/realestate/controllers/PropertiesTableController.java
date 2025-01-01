package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PropertiesTableController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button addPropertyBtn;
    @FXML
    private TableView<Property> PropertiesTable;
    @FXML
    private TableColumn<Property, String> PropertiesColumn;
    @FXML
    private TableColumn<Property, String> PTypeColumn;
    @FXML
    private TableColumn<Property, Integer> NumberOfRoomsColumn;
    @FXML
    private TableColumn<Property, String> PFeaturesColumn;
    @FXML
    private TableColumn<Property, String> PAreaColumn;
    @FXML
    private TableColumn<Property, String> PStatusColumn;
    @FXML
    private TableColumn<Property, Void> UpdatePColumn;
    @FXML
    private TableColumn<Property, Void> DeletePColumn;

    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize() {
        PropertiesColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(300.0 / 1240));
        PTypeColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(135.0 / 1240));
        NumberOfRoomsColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(150.0 / 1240));
        PFeaturesColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 1240));
        PAreaColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(120.0 / 1240));
        PStatusColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(135.0 / 1240));
        UpdatePColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(100.0 / 1240));
        DeletePColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(100.0 / 1240));


        addDeleteButton();
        addEditButton();
    }

    private void addDeleteButton() {
        DeletePColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    // Placeholder for delete logic
                    System.out.println("Delete button clicked!");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void addEditButton() {
        UpdatePColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Update");

            {
                editButton.setOnAction(event -> {
                    // Placeholder for edit logic
                    System.out.println("Edit button clicked!");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }




    public void goToPF(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/realestate/views/AddProForm.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
    }


}
