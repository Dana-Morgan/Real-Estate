package com.example.realestate.controllers;

import com.example.realestate.models.Agreement;
import com.example.realestate.services.AgreementDOA;
import com.example.realestate.services.AgreementDOAImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgreementTableController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AgreementTableController.class.getName());

    private AgreementDOA agreementDOA = new AgreementDOAImpl();

    @FXML
    private TableView<Agreement> agreementTable;



    @FXML
    private TableColumn<Agreement, Integer> displayIDColumn;

    @FXML
    private TableColumn<Agreement, Integer> customerIDColumn;

    @FXML
    private TableColumn<Agreement, Integer> propertyIDColumn;

    @FXML
    private TableColumn<Agreement, String> offerTypeColumn;

    @FXML
    private TableColumn<Agreement, String> offerStatusColumn;

    @FXML
    private TableColumn<Agreement, LocalDate> presentationDateColumn;

    @FXML
    private TableColumn<Agreement, String> additionalNotesColumn;

    @FXML
    private TableColumn<Agreement, String> updateColumn;

    @FXML
    private TableColumn<Agreement, String> deleteColumn;

    @FXML
    private TextField displayIDField;

    @FXML
    private TextField customerIDField;

    @FXML
    private TextField propertyIDField;

    @FXML
    private TextField offerTypeField;

    @FXML
    private TextField offerStatusField;

    @FXML
    private DatePicker presentationDateField;

    @FXML
    private TextArea additionalNotesArea;

    @FXML
    private Button addAgreementbtn;

    @FXML
    private Button deleteAgreementButton;

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

        updateColumn.setCellFactory(column -> {
            TableCell<Agreement, String> cell = new TableCell<Agreement, String>() {
                private final Button updateButton = new Button("Update");

                {
                    updateButton.setStyle("-fx-background-color: #89B7E7; -fx-text-fill: black;");
                    updateButton.setOnAction(event -> {
                        Agreement agreement = getTableView().getItems().get(getIndex());
                        handleUpdateAgreementPage(agreement);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                    }
                }
            };
            return cell;
        });

        deleteColumn.setCellFactory(column -> {
            TableCell<Agreement, String> cell = new TableCell<Agreement, String>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setStyle("-fx-background-color: #DC3545; -fx-text-fill: black;");
                    deleteButton.setOnAction(event -> {
                        Agreement agreement = getTableView().getItems().get(getIndex());
                        handleDeleteAgreement(agreement);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        });

        displayIDColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        customerIDColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        propertyIDColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        offerTypeColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        offerStatusColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        presentationDateColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        additionalNotesColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        updateColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));
        deleteColumn.prefWidthProperty().bind(agreementTable.widthProperty().divide(9));

        agreementTable.getColumns().forEach(column -> column.setResizable(true));

        refreshTable();
    }

    @FXML
    private void handleAddAgreement() {
        try {
            int displayID = Integer.parseInt(displayIDField.getText());
            int customerID = Integer.parseInt(customerIDField.getText());
            int propertyID = Integer.parseInt(propertyIDField.getText());
            String offerType = offerTypeField.getText();
            String offerStatus = offerStatusField.getText();
            LocalDate presentationDate = presentationDateField.getValue();
            String additionalNotes = additionalNotesArea.getText();

            if (offerType.isEmpty() || offerStatus.isEmpty() || presentationDate == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields except Additional Notes are required.");
                return;
            }

            Agreement agreement = new Agreement( customerID, propertyID, offerType, offerStatus, presentationDate, additionalNotes);
            agreementDOA.save(agreement);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Agreement added successfully!");
            refreshTable();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter valid numbers for IDs.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add agreement. Please check your input.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAgreement(Agreement agreement) {
        if (agreement != null) {
            agreementDOA.delete(agreement);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Agreement deleted successfully!");
            Platform.runLater(this::refreshTable);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an agreement to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTable() {
        ObservableList<Agreement> agreementList = FXCollections.observableArrayList(agreementDOA.getAll());
        agreementTable.setItems(agreementList);
    }

    private void clearFields() {
        displayIDField.clear();
        customerIDField.clear();
        propertyIDField.clear();
        offerTypeField.clear();
        offerStatusField.clear();
        presentationDateField.setValue(null);
        additionalNotesArea.clear();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) addAgreementbtn.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }

    @FXML
    private void handleAddAgreementPage() {
        navigateTo("/com/example/realestate/views/AgreementDetails.fxml", "Add Agreement");
    }

    @FXML
    private void handleUpdateAgreementPage(Agreement agreement) {
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
}
