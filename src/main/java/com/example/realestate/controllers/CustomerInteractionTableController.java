package com.example.realestate.controllers;

import com.example.realestate.models.Interaction;
import com.example.realestate.services.InteractionDOA;
import com.example.realestate.services.InteractionDOAImpl;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerInteractionTableController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CustomerInteractionTableController.class.getName());

    private InteractionDOA interactionDOA = new InteractionDOAImpl();

    @FXML
    private TableView<Interaction> interactionTable;

    @FXML
    private TableColumn<Interaction, Integer> interactionIDColumn;

    @FXML
    private TableColumn<Interaction, Integer> customerIDColumn;

    @FXML
    private TableColumn<Interaction, String> interactionTypeColumn;

    @FXML
    private TableColumn<Interaction, LocalDate> interactionDateColumn;

    @FXML
    private TableColumn<Interaction, String> additionalNotesColumn;

    @FXML
    private TableColumn<Interaction, String> updateColumn;

    @FXML
    private TableColumn<Interaction, String> deleteColumn;

    @FXML
    private TextField customerIDField;

    @FXML
    private TextField interactionTypeField;

    @FXML
    private DatePicker interactionDateField;

    @FXML
    private TextArea additionalNotesArea;

    @FXML
    private Button addInteractionbtn;

    @FXML
    private Button deleteInteractionButton;

    private ObservableList<Interaction> interactionList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // تعيين الخصائص لكل عمود في الـ TableView
        interactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("interactionID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        interactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("interactionType"));
        interactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("interactionDate"));
        additionalNotesColumn.setCellValueFactory(new PropertyValueFactory<>("additionalNotes"));

        // تعيين زر "تحديث" في العمود
        updateColumn.setCellFactory(column -> {
            TableCell<Interaction, String> cell = new TableCell<Interaction, String>() {
                private final Button updateButton = new Button("Update");

                {
                    updateButton.setOnAction(event -> {
                        Interaction interaction = getTableView().getItems().get(getIndex());
                        handleUpdateInteractionPage(interaction);
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

        // تعيين زر "حذف" في العمود
        deleteColumn.setCellFactory(column -> {
            TableCell<Interaction, String> cell = new TableCell<Interaction, String>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        Interaction interaction = getTableView().getItems().get(getIndex());
                        handleDeleteInteractionPage(interaction);
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

        // ربط العرض مع عرض الـ TableView بشكل ديناميكي
        interactionIDColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        customerIDColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        interactionTypeColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        interactionDateColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        additionalNotesColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        updateColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));
        deleteColumn.prefWidthProperty().bind(interactionTable.widthProperty().divide(7));

        interactionTable.getColumns().forEach(column -> column.setResizable(true));

        refreshTable();
    }

    @FXML
    private void handleAddInteraction() {
        try {
            int customerID = Integer.parseInt(customerIDField.getText());
            String interactionType = interactionTypeField.getText();
            LocalDate interactionDate = interactionDateField.getValue();
            String additionalNotes = additionalNotesArea.getText();

            Interaction interaction = new Interaction(customerID, interactionType, interactionDate, additionalNotes);
            interactionDOA.save(interaction);  // استخدام InteractionDOA لحفظ التفاعل

            refreshTable();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteInteraction() {
        Interaction selectedInteraction = interactionTable.getSelectionModel().getSelectedItem();
        if (selectedInteraction != null) {
            interactionDOA.delete(selectedInteraction);  // حذف التفاعل من قاعدة البيانات

            refreshTable();
        }
    }

    private void refreshTable() {
        ObservableList<Interaction> interactionList = FXCollections.observableArrayList(interactionDOA.getAll());  // استخدام InteractionDOA لجلب جميع التفاعلات
        interactionTable.setItems(interactionList);
    }

    private void clearFields() {
        customerIDField.clear();
        interactionTypeField.clear();
        interactionDateField.setValue(null);
        additionalNotesArea.clear();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) addInteractionbtn.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 832));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: Unable to load " + fxmlPath, e);
        }
    }

    @FXML
    private void handleAddInteractionPage() {
        navigateTo("/com/example/realestate/views/CustomerInteractionDetails.fxml", "Add Interaction");
    }

    @FXML
    private void handleUpdateInteractionPage(Interaction interaction) {
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

    @FXML
    private void handleDeleteInteractionPage(Interaction interaction) {
        // حذف التفاعل من قاعدة البيانات
        interactionDOA.delete(interaction);

        // تحديث الجدول بعد الحذف
        refreshTable();
    }
}
