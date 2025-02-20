package com.example.realestate.controllers;

import com.example.realestate.models.Agreement;
import com.example.realestate.services.AgreementDAO;
import com.example.realestate.services.AgreementDAOImpl;
import java.io.File;
import java.awt.Desktop;
import java.io.IOException;

import com.example.realestate.utils.SessionManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;



public class AgreementTableController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(AgreementTableController.class.getName());
    private AgreementDAO agreementDAO = new AgreementDAOImpl();

    @FXML private TableView<Agreement> agreementTable;
    @FXML private TableColumn<Agreement, Integer> displayIDColumn, customerIDColumn, propertyIDColumn;
    @FXML private TableColumn<Agreement, String> offerTypeColumn, offerStatusColumn, additionalNotesColumn;
    @FXML private TableColumn<Agreement, LocalDate> presentationDateColumn;
    @FXML private TableColumn<Agreement, String> updateColumn, deleteColumn, pdfFileColumn;
    @FXML private TableColumn<Agreement, String> customerNameColumn, propertyNameColumn;

    @FXML private DatePicker presentationDateField, agreementDateSearchField;
    @FXML private TextArea additionalNotesArea;
    @FXML private Button addAgreementbtn, deleteAgreementButton, searchButton;
    @FXML private ChoiceBox<String> offerTypeChoiceBox, offerStatusChoiceBox;
    @FXML private TextField displayIDSearchField, customerNameSearchField, propertyNameSearchField;
    @FXML private TextField propertyIDSearchField;
    @FXML private TextField customerIDSearchField;

    private ObservableList<Agreement> agreementList;
    private String userRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRole = SessionManager.getUserRole();

        System.out.println("User role from session: " + userRole);

        if (userRole == null) {
            System.out.println("User role is null! Make sure to set it before initialization.");
        } else {
            System.out.println("User role: " + userRole);
        }

        initializeColumns();
        initializeChoiceBoxes();
        initializeButtons();
        refreshTable();
    }

    private void initializeColumns() {
        displayIDColumn.setCellValueFactory(new PropertyValueFactory<>("displayID"));
        customerIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCustomer().getCustomerId()));
        propertyIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProperty().getId()));

        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        propertyNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProperty().getName()));

        offerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("offerType"));
        offerStatusColumn.setCellValueFactory(new PropertyValueFactory<>("offerStatus"));
        presentationDateColumn.setCellValueFactory(new PropertyValueFactory<>("presentationDate"));
        additionalNotesColumn.setCellValueFactory(new PropertyValueFactory<>("additionalNotes"));

        pdfFileColumn.setCellValueFactory(new PropertyValueFactory<>("pdfPath"));
        pdfFileColumn.setCellFactory(param -> new TableCell<Agreement, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button openButton = new Button("Open PDF");
                    openButton.setOnAction(event -> openPDF(item));
                    setGraphic(openButton);
                }
            }
        });

        updateColumn.setCellFactory(col -> createTableCellWithButton("Update", this::handleUpdateAgreementPage));
        deleteColumn.setCellFactory(col -> createTableCellWithButton("Delete", this::handleDeleteAgreement));

        bindColumnWidth();
    }

    private void bindColumnWidth() {
        agreementTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double columnWidth = newWidth.doubleValue() / 11;
            displayIDColumn.setPrefWidth(columnWidth);
            customerNameColumn.setPrefWidth(columnWidth);
            propertyNameColumn.setPrefWidth(columnWidth);
            offerTypeColumn.setPrefWidth(columnWidth);
            offerStatusColumn.setPrefWidth(columnWidth);
            presentationDateColumn.setPrefWidth(columnWidth);
            additionalNotesColumn.setPrefWidth(columnWidth);
            updateColumn.setPrefWidth(columnWidth);
            deleteColumn.setPrefWidth(columnWidth);
        });
    }

    private void initializeChoiceBoxes() {
        offerTypeChoiceBox.getItems().addAll("Sale", "Rent", "Lease");
        offerStatusChoiceBox.getItems().addAll("Pending", "Accepted", "Rejected");
    }

    private void initializeButtons() {
        addAgreementbtn.setOnAction(event -> handleAddAgreementPage());
        searchButton.setOnAction(event -> handleSearch());
    }

    private TableCell<Agreement, String> createTableCellWithButton(String buttonText, ButtonHandler handler) {
        return new TableCell<Agreement, String>() {
            private final Button button = new Button(buttonText);

            {
                if (buttonText.equals("Update")) {
                    button.setStyle("-fx-background-color: #508aa8; -fx-text-fill: #ffffff;");
                } else if (buttonText.equals("Delete")) {
                    button.setStyle("-fx-background-color: #dc3545; -fx-text-fill: #ffffff;");
                }
                button.setOnAction(event -> handler.handle(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        };
    }

    private interface ButtonHandler {
        void handle(Agreement agreement);
    }

    private void handleDeleteAgreement(Agreement agreement) {
        if (agreement != null) {
            agreementDAO.delete(agreement);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Agreement deleted successfully!");
            Platform.runLater(this::refreshTable);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an agreement to delete.");
        }
    }

    private void handleUpdateAgreementPage(Agreement agreement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgreementDetails.fxml"));
            Parent root = loader.load();
            AgreementDetailsController controller = loader.getController();
            controller.setAgreementDetails(agreement);

            Stage stage = (Stage) agreementTable.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));
            stage.setTitle("Update Agreement");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load AgreementDetails.fxml", e);
        }
    }


    @FXML
    private void handleSearch() {
        String displayIDInput = displayIDSearchField.getText().trim();
        String customerIDInput = customerIDSearchField.getText().trim();
        String propertyIDInput = propertyIDSearchField.getText().trim();
        String customerNameInput = customerNameSearchField.getText().trim();
        String propertyNameInput = propertyNameSearchField.getText().trim();
        LocalDate dateInput = agreementDateSearchField.getValue();
        String offerTypeInput = offerTypeChoiceBox.getValue();
        String offerStatusInput = offerStatusChoiceBox.getValue();

        List<Agreement> filteredAgreements = agreementDAO.getAll().stream()
                .filter(agreement -> matchesSearchCriteria(agreement, displayIDInput, customerIDInput, propertyIDInput, customerNameInput, propertyNameInput, dateInput, offerTypeInput, offerStatusInput))
                .collect(Collectors.toList());

        agreementTable.setItems(FXCollections.observableList(filteredAgreements));
    }



    private boolean matchesSearchCriteria(Agreement agreement, String displayIDInput, String customerIDInput, String propertyIDInput, String customerNameInput, String propertyNameInput, LocalDate dateInput, String offerTypeInput, String offerStatusInput) {
        return (displayIDInput.isEmpty() || String.valueOf(agreement.getDisplayID()).contains(displayIDInput)) &&
                (customerIDInput.isEmpty() || String.valueOf(agreement.getCustomer().getCustomerId()).contains(customerIDInput)) &&
                (propertyIDInput.isEmpty() || String.valueOf(agreement.getProperty().getId()).contains(propertyIDInput)) &&
                (customerNameInput.isEmpty() || agreement.getCustomer().getCustomerName().toLowerCase().contains(customerNameInput.toLowerCase())) &&
                (propertyNameInput.isEmpty() || agreement.getProperty().getName().toLowerCase().contains(propertyNameInput.toLowerCase())) &&
                (dateInput == null || agreement.getPresentationDate().equals(dateInput)) &&
                (offerTypeInput == null || agreement.getOfferType().equalsIgnoreCase(offerTypeInput)) &&
                (offerStatusInput == null || agreement.getOfferStatus().equalsIgnoreCase(offerStatusInput));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTable() {
        agreementList = FXCollections.observableArrayList(agreementDAO.getAll());
        agreementTable.setItems(agreementList);
    }

    private void clearFields() {
        displayIDSearchField.clear();
        customerIDSearchField.clear();
        propertyIDSearchField.clear();
        offerTypeChoiceBox.getSelectionModel().clearSelection();
        offerStatusChoiceBox.getSelectionModel().clearSelection();
        presentationDateField.setValue(null);
        additionalNotesArea.clear();
    }

    @FXML
    private void handleHomeButtonAction() {
        if (Objects.equals(SessionManager.getUserRole(), "Admin")) {
            navigateTo("/com/example/realestate/views/HomePageForAdmin.fxml", "Admin Home Page");
        } else if (Objects.equals(SessionManager.getUserRole(), "Agent")) {
            navigateTo("/com/example/realestate/views/HomePageForAgent.fxml", "Agent Home Page");
        }
    }

    @FXML
    private void handleAddAgreementPage() {
        navigateTo("/com/example/realestate/views/AgreementDetails.fxml", "Add Agreement");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) addAgreementbtn.getScene().getWindow();

            Scene scene;
            if ("Add Agreement".equals(title)) {
                scene = new Scene(root, 600, 800);
            } else {
                scene = new Scene(root, 1400, 780);
            }

            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));

            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }

    private void openPDF(String filePath) {
        if (filePath != null) {
            try {
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    showAlert(Alert.AlertType.ERROR, "File Not Found", "PDF file not found.");
                }
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to open the PDF file.");
                LOGGER.log(Level.SEVERE, "Error: Unable to open the PDF file", e);
            }
        }
    }


    public void setUserRole(String role) {
        this.userRole = role;
    }
}
