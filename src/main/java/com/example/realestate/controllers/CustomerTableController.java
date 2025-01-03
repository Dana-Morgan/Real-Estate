package com.example.realestate.controllers;

import com.example.realestate.models.Customer;
import com.example.realestate.services.CustomerDAO;
import com.example.realestate.services.CustomerDAOimp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerTableController implements Initializable {

    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> customerActivityStatusCol;
    @FXML
    private TableColumn<Customer, String> customerAddDateCol;
    @FXML
    private TableColumn<Customer, String> customerEmailCol;
    @FXML
    private TableColumn<Customer, String> customerPhoneCol;
    @FXML
    private TableColumn<Customer, String> customerPreferenceCol;
    @FXML
    private TableColumn<Customer, String> customerAdditionalNoteCol;
    @FXML
    private TableColumn<Customer, Void> deleteColumn;
    @FXML
    private Button AddNewCustomer_btn;

    private CustomerDAO customerDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDAO = new CustomerDAOimp();

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        customerAddDateCol.setCellValueFactory(new PropertyValueFactory<>("addDate"));
        customerAdditionalNoteCol.setCellValueFactory(new PropertyValueFactory<>("additionNote"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerActivityStatusCol.setCellValueFactory(new PropertyValueFactory<>("customerActivityStatus"));
        customerPreferenceCol.setCellValueFactory(new PropertyValueFactory<>("customerPrefernces"));

        loadCustomers();
        addDeleteButtonToTable();
    }

    public void navigateToAddCustomerDetails(ActionEvent event) throws IOException {
        Stage stage = (Stage) AddNewCustomer_btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AddCustomerDetails.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Customer Details");
        stage.show();
    }

    private void loadCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        ObservableList<Customer> customerList = FXCollections.observableArrayList(customers);
        CustomerTable.setItems(customerList);
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            {
                deleteButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    deleteCustomer(customer);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(deleteButton);
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                }
            }
        });
    }


    private void deleteCustomer(Customer customer) {
        try {
            if (confirmDelete()) {
                customerDAO.delete(customer);

                CustomerTable.getItems().remove(customer);
            }
        } catch (Exception e) {
            showAlert("Error", "Error deleting customer: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this customer?");
        return alert.showAndWait().filter(ButtonType.OK::equals).isPresent();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}