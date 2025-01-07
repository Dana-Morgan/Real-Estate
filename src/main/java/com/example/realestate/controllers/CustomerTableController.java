package com.example.realestate.controllers;

import com.example.realestate.models.Customer;
import com.example.realestate.services.CustomerDAO;
import com.example.realestate.services.CustomerDAOimp;
import com.example.realestate.utils.SessionManager;
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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class CustomerTableController implements Initializable {

    @FXML
    public Button homeButton;
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
    private TableColumn<Customer, Void> updateColumn;
    @FXML
    private Button AddNewCustomer_btn;

    private CustomerDAO customerDAO;
    private static final Logger LOGGER = Logger.getLogger(AgreementTableController.class.getName());
    private String userRole;

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
        addUpdateButtonToTable();
    }

    public void navigateToAddCustomerDetails() throws IOException {
        Stage stage = (Stage) AddNewCustomer_btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/addCustomerDetails.fxml"));
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

    private void addUpdateButtonToTable() {
        updateColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            {
                updateButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    navigateToUpdateCustomerDetails(customer);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(updateButton);
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                }
            }
        });
    }

    private void navigateToUpdateCustomerDetails(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/UpdateCustomerDetails.fxml"));
            Parent root = loader.load();
            UpdateCustomerDetailsController controller = loader.getController();
            controller.setCustomerData(customer);
            Stage stage = (Stage) CustomerTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Customer Details");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Error navigating to update customer: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void handleHomeButtonAction(ActionEvent actionEvent) {
        userRole = SessionManager.getUserRole();

        System.out.println("User role from session: " + userRole);

        if (userRole == null) {
            System.out.println("User role is null! Make sure to set it before initialization.");
        } else {
            System.out.println("User role: " + userRole);
        }
        if (Objects.equals(SessionManager.getUserRole(), "Admin")) {
            System.out.println("Navigating to Admin home page");
            navigateTo("/com/example/realestate/views/HomePageForAdmin.fxml", "Admin Home Page");
        } else if (Objects.equals(SessionManager.getUserRole(), "Agent")) {
            System.out.println("Navigating to Agent home page");
            navigateTo("/com/example/realestate/views/HomePageForAgent.fxml", "Agent Home Page");
        }


    }
    private void navigateTo(String fxmlPath, String title) {
        System.out.println("Entering load FXML function.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        try {
            Parent root = loader.load();
            System.out.println("FXML file loaded successfully.");
            Stage stage = (Stage) homeButton.getScene().getWindow();
            Scene scene;
                scene = new Scene(root, 1400, 780);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));

            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }
}





