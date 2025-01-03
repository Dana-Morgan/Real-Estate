package com.example.realestate.controllers;

import com.example.realestate.models.Interaction;
import com.example.realestate.services.InteractionDOA;
import com.example.realestate.services.InteractionDOAImpl;
import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InteractionTableController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(InteractionTableController.class.getName());
    private InteractionDOA interactionDOA = new InteractionDOAImpl();

    @FXML private TableView<Interaction> interactionTable;
    @FXML private TableColumn<Interaction, Integer> interactionIDColumn, customerIDColumn;
    @FXML private TableColumn<Interaction, String> interactionTypeColumn, additionalNotesColumn;
    @FXML private TableColumn<Interaction, LocalDate> interactionDateColumn;
    @FXML private TableColumn<Interaction, String> updateColumn, deleteColumn;

    @FXML private DatePicker interactionDateField, interactionDateSearchField;
    @FXML private TextArea additionalNotesArea;
    @FXML private Button addInteractionbtn, deleteInteractionButton, searchButton;
    @FXML private ChoiceBox<String> interactionTypeSearchField;
    @FXML private TextField interactionIDSearchField, customerIDSearchField;

    private ObservableList<Interaction> interactionList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        initializeChoiceBoxes();
        initializeButtons();
        refreshTable();
    }

    private void initializeColumns() {
        interactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("interactionID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        interactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("interactionType"));
        interactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("interactionDate"));
        additionalNotesColumn.setCellValueFactory(new PropertyValueFactory<>("additionalNotes"));

        configureTableColumnActions();
        bindColumnWidth();
    }

    private void bindColumnWidth() {
        interactionTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double columnWidth = newWidth.doubleValue() / 5;
            interactionIDColumn.setPrefWidth(columnWidth);
            customerIDColumn.setPrefWidth(columnWidth);
            interactionTypeColumn.setPrefWidth(columnWidth);
            interactionDateColumn.setPrefWidth(columnWidth);
            additionalNotesColumn.setPrefWidth(columnWidth);
            updateColumn.setPrefWidth(columnWidth);
            deleteColumn.setPrefWidth(columnWidth);
        });
    }

    private void initializeChoiceBoxes() {
        interactionTypeSearchField.getItems().addAll("calls", "follow-up", "inquiry");
    }

    private void initializeButtons() {
        addInteractionbtn.setOnAction(event -> handleAddInteractionPage());
        searchButton.setOnAction(event -> handleSearch());
    }

    private void configureTableColumnActions() {
        updateColumn.setCellFactory(col -> createTableCellWithButton("Update", this::handleUpdateInteractionPage));
        deleteColumn.setCellFactory(col -> createTableCellWithButton("Delete", this::handleDeleteInteraction));
    }

    private TableCell<Interaction, String> createTableCellWithButton(String buttonText, ButtonHandler handler) {
        return new TableCell<Interaction, String>() {
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
        void handle(Interaction interaction);
    }

    private boolean areFieldsValid() {
        if (interactionIDSearchField.getText().isEmpty() || customerIDSearchField.getText().isEmpty() ||
                interactionTypeSearchField.getValue().isEmpty() || interactionDateField.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all required fields.");
            return false;
        }
        return true;
    }

    private void handleDeleteInteraction(Interaction interaction) {
        if (interaction != null) {
            interactionDOA.delete(interaction);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Interaction deleted successfully!");
            Platform.runLater(this::refreshTable);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an interaction to delete.");
        }
    }

    private void handleUpdateInteractionPage(Interaction interaction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/InteractionDetails.fxml"));
            Parent root = loader.load();
            InteractionDetailsController controller = loader.getController();
            controller.setInteractionDetails(interaction);

            Stage stage = (Stage) interactionTable.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));
            stage.setTitle("Update Interaction");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load InteractionDetails.fxml", e);
        }
    }

    @FXML
    private void handleSearch() {
        String interactionIDInput = interactionIDSearchField.getText().trim();
        String customerIDInput = customerIDSearchField.getText().trim();
        LocalDate dateInput = interactionDateSearchField.getValue();
        String interactionTypeInput = interactionTypeSearchField.getValue();

        List<Interaction> filteredInteractions = interactionDOA.getAll().stream()
                .filter(interaction -> matchesSearchCriteria(interaction, interactionIDInput, customerIDInput, dateInput, interactionTypeInput))
                .collect(Collectors.toList());

        interactionTable.setItems(FXCollections.observableList(filteredInteractions));
    }

    private boolean matchesSearchCriteria(Interaction interaction, String interactionIDInput, String customerIDInput, LocalDate dateInput, String interactionTypeInput) {
        return (interactionIDInput.isEmpty() || String.valueOf(interaction.getInteractionID()).contains(interactionIDInput)) &&
                (customerIDInput.isEmpty() || String.valueOf(interaction.getCustomerID()).contains(customerIDInput)) &&
                (dateInput == null || interaction.getInteractionDate().equals(dateInput)) &&
                (interactionTypeInput == null || interaction.getInteractionType().equalsIgnoreCase(interactionTypeInput));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTable() {
        interactionList = FXCollections.observableArrayList(interactionDOA.getAll());
        interactionTable.setItems(interactionList);
    }

    private void clearFields() {
        interactionIDSearchField.clear();
        customerIDSearchField.clear();
        interactionTypeSearchField.getSelectionModel().clearSelection();
        interactionDateField.setValue(null);
        additionalNotesArea.clear();
    }

    @FXML
    private void handleHomeButtonAction() {
        navigateTo("/com/example/realestate/views/HomePage.fxml", "Home Page");
    }

    @FXML
    private void handleAddInteractionPage() {
        navigateTo("/com/example/realestate/views/InteractionDetails.fxml", "Add Interaction");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) addInteractionbtn.getScene().getWindow();
            if (title.equals("Add Interaction")) {
                Scene scene = new Scene(root, 600, 780);
            stage.setScene(scene);}

         else {
            Scene scene = new Scene(root, 1400, 780);
           stage.setScene(scene);}

            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));

            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }
}
