package com.example.realestate.controllers;

import com.example.realestate.models.Interaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerInteractionTableController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CustomerInteractionTableController.class.getName());

    @FXML
    private TableView<Interaction> interactionTable;

    @FXML
    private TableColumn<Interaction, String> interactionIDColumn;

    @FXML
    private TableColumn<Interaction, String> customerIDColumn;

    @FXML
    private TableColumn<Interaction, String> interactionTypeColumn;

    @FXML
    private TableColumn<Interaction, LocalDate> interactionDateColumn;

    @FXML
    private TableColumn<Interaction, String> additionalNotesColumn;

    @FXML
    private TableColumn<Interaction, Void> updateColumn;

    @FXML
    private TableColumn<Interaction, Void> deleteColumn;

    @FXML
    private Button addInteractionbtn;

    private ObservableList<Interaction> interactionList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        interactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("interactionID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        interactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("interactionType"));
        interactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("interactionDate"));
        additionalNotesColumn.setCellValueFactory(new PropertyValueFactory<>("additionalNotes"));

        interactionList = FXCollections.observableArrayList(
                new Interaction("I001", "C123", "Phone Call", LocalDate.of(2023, 12, 1), "Discussed property details"),
                new Interaction("I002", "C124", "Email", LocalDate.of(2023, 12, 5), "Sent pricing info"),
                new Interaction("I003", "C125", "Meeting", LocalDate.of(2023, 12, 10), "Reviewed contracts")
        );

        interactionTable.setItems(interactionList);

        interactionIDColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        customerIDColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        interactionTypeColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        interactionDateColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        additionalNotesColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        updateColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        deleteColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));

        updateColumn.setCellFactory(col -> new TableCell<Interaction, Void>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> handleUpdate(getTableRow().getItem()));
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

        deleteColumn.setCellFactory(col -> new TableCell<Interaction, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> handleDelete(getTableRow().getItem()));
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

        addInteractionbtn.setOnAction(event -> navigateTo("/com/example/realestate/views/CustomerInteractionDetails.fxml", "Add Interaction"));
    }

    private void handleUpdate(Interaction interaction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/CustomerInteractionDetails.fxml"));
            Parent root = loader.load();
            CustomerInteractionDetailsController controller = loader.getController();
            controller.setInteractionDetails(interaction);
            Stage stage = (Stage) interactionTable.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle("Update Interaction");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load CustomerInteractionDetails.fxml", e);
        }
    }

    private void handleDelete(Interaction interaction) {
        interactionList.remove(interaction);
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) addInteractionbtn.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }
}
