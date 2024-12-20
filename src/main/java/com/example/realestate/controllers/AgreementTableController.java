package com.example.realestate.controllers;

import com.example.realestate.models.Agreement;
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
import javafx.stage.Stage;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;


public class AgreementTableController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AgreementTableController.class.getName());

    @FXML
    private TableView<Agreement> agreementTable;

    @FXML
    private TableColumn<Agreement, String> displayIDColumn;

    @FXML
    private TableColumn<Agreement, String> customerIDColumn;

    @FXML
    private TableColumn<Agreement, String> propertyIDColumn;

    @FXML
    private TableColumn<Agreement, String> offerTypeColumn;

    @FXML
    private TableColumn<Agreement, String> offerStatusColumn;

    @FXML
    private TableColumn<Agreement, String> presentationDateColumn;

    @FXML
    private TableColumn<Agreement, String> additionalNotesColumn;

    @FXML
    private TableColumn<Agreement, Void> updateColumn;

    @FXML
    private TableColumn<Agreement, Void> deleteColumn;

    @FXML
    private Button addAgreementbtn;

    private ObservableList<Agreement> agreementList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayIDColumn.setCellValueFactory(new PropertyValueFactory<>("displayID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        propertyIDColumn.setCellValueFactory(new PropertyValueFactory<>("propertyID"));
        offerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("offerType"));
        offerStatusColumn.setCellValueFactory(new PropertyValueFactory<>("offerStatus"));
        presentationDateColumn.setCellValueFactory(new PropertyValueFactory<>("presentationDate"));
        additionalNotesColumn.setCellValueFactory(new PropertyValueFactory<>("additionalNotes"));

        agreementList = FXCollections.observableArrayList(
                new Agreement("A001", "C123", "P101", "Rent", "Active", LocalDate.of(2023, 12, 1), "Important details"),
                new Agreement("A002", "C124", "P102", "Sale", "Pending", LocalDate.of(2023, 12, 5), "Additional notes")
        );

        agreementTable.setItems(agreementList);

        displayIDColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(7));
        customerIDColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(7));
        propertyIDColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(7));
        offerTypeColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(7));
        offerStatusColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(7));
        presentationDateColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(7));
        additionalNotesColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(7));

        updateColumn.setCellFactory(col -> new TableCell<Agreement, Void>() {
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

        deleteColumn.setCellFactory(col -> new TableCell<Agreement, Void>() {
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

        addAgreementbtn.setOnAction(event -> navigateTo("/com/example/realestate/views/AgreementDetails.fxml", "Add Agreement"));
    }

    private void handleUpdate(Agreement agreement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgreementDetails.fxml"));
            Parent root = loader.load();
            AgreementDetailsController controller = loader.getController();
            controller.setAgreementDetails(agreement);
            Stage stage = (Stage) agreementTable.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle("Update Agreement");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load AgreementDetails.fxml", e);
        }
    }

    private void handleDelete(Agreement agreement) {
        agreementList.remove(agreement);
        System.out.println("Deleted: " + agreement);
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) addAgreementbtn.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }
}