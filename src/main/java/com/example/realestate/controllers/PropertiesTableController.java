package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import com.example.realestate.services.PropertyDAO;
import com.example.realestate.services.PropertyDAOImpl;
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
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class PropertiesTableController {
    @FXML
    private TableView<Property> PropertiesTable;
    @FXML
    private TableColumn<Property, Integer> PIDColumn;
    @FXML
    private TableColumn<Property, String> PNameColumn;
    @FXML
    private TableColumn<Property, String> PImageColumn;
    @FXML
    private TableColumn<Property, String> PPriceColumn;
    @FXML
    private TableColumn<Property, String> PLocationColumn;
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
    private TableColumn<Property, String> PDateColumn;
    @FXML
    private TableColumn<Property, Void> UpdatePColumn;
    @FXML
    private TableColumn<Property, Void> DeletePColumn;

    @FXML
    private Button homeButton;

    private final PropertyDAO propertyDAO = new PropertyDAOImpl();

    private static final Logger LOGGER = Logger.getLogger(PropertiesTableController.class.getName());

    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize() {
        // Bind data to columns
        PIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PImageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        PPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        PTypeColumn.setCellValueFactory(new PropertyValueFactory<>("propertyType"));
        NumberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        PFeaturesColumn.setCellValueFactory(new PropertyValueFactory<>("propertyFeatures"));
        PAreaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        PStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        PDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Adjust column widths
        PIDColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(100.0 / 2200)); // Adjust width as necessary
        PNameColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        PImageColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(250.0 / 2200));
        PLocationColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        PPriceColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        PTypeColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        NumberOfRoomsColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        PFeaturesColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(300.0 / 2200));
        PAreaColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        PStatusColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        PDateColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        UpdatePColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));
        DeletePColumn.prefWidthProperty().bind(PropertiesTable.widthProperty().multiply(200.0 / 2200));

    loadData();
        addDeleteButton();
        addUpdateButton();


    }

    public void loadData() {
        try {
            List<Property> properties = propertyDAO.getAllProperties();
            ObservableList<Property> observableList = FXCollections.observableList(properties);
            PropertiesTable.setItems(observableList);
        } catch (RuntimeException e) {
            System.err.println("Failed to load properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addDeleteButton() {
        DeletePColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color:#dc3545; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    Property property = getTableView().getItems().get(getIndex());
                    if (property != null) {
                        try {
                            propertyDAO.deleteProperty(property);
                            getTableView().getItems().remove(property);


                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Property '" + property.getName() + "' has been successfully deleted!");
                            alert.showAndWait();

                            System.out.println("Deleted property: " + property.getName());
                        } catch (RuntimeException e) {
                            System.err.println("Failed to delete property: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
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

    private void addUpdateButton() {
        UpdatePColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setStyle("-fx-background-color: #508aa8; -fx-text-fill: white;");
                updateButton.setOnAction(event -> {
                    Property property = getTableView().getItems().get(getIndex());
                    if (property != null) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/UpdatePro.fxml"));
                            Parent root = loader.load();
                            UpdateProController controller = loader.getController();
                            controller.setPropertyToUpdate(property);  // Pass the property to be updated
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root, 1280, 832);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });
    }

    // Reload the table after update
    public void reloadPropertiesTable() {
        loadData();
    }



    @FXML
    private void handleNavigateToHomeButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePageForAdmin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1400, 780);
            stage.setScene(scene);
            stage.show();

            System.out.println("Navigated to HomePage successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading HomePageForAdmin.fxml: " + e.getMessage());
        }
    }



    public void goToPF(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/realestate/views/AddProForm.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
}
}