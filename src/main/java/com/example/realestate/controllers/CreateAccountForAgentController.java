package com.example.realestate.controllers;

import com.example.realestate.models.User;
import com.example.realestate.services.AgentDAOImpl;
import com.example.realestate.services.UserDOAImpl;
import com.example.realestate.validation.ValiditionAgentAccount;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;


public class CreateAccountForAgentController {

    @FXML
    public ChoiceBox<String> roleChoiceBox;

    @FXML
    public Button homeButton;

    @FXML
    private TableView<User> agentTabel;

    @FXML
    private TableColumn<User, Integer> idTabel;
    @FXML
    private TableColumn<User, String> NameColumn;
    @FXML
    private TableColumn<User, String> EmailColumn;
    @FXML
    private TableColumn<User, String> PhoneColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private Button CreateBut;

    @FXML
    private TextField EmailSignUp;

    @FXML
    private TextField IdSignUp;

    @FXML
    private TextField LicenseSignUp;

    @FXML
    private TextField NameSignUp;

    @FXML
    private PasswordField PasswordSignUp;

    @FXML
    private TextField PhoneSignUp;

    @FXML
    private Text backbutSignup;

    @FXML
    private Button canclebut;

    @FXML
    private TableColumn<User, Void> UpdateAgent;

    @FXML
    private TableColumn<User, Void> DeleteAgent;

    @FXML
    private Button backbut1;
    private AgentDAOImpl agentDOA = new AgentDAOImpl();
    private UserDOAImpl userDOA = new UserDOAImpl();
    //private ObservableList<Agent> agentList = FXCollections.observableArrayList();
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Initialize table columns
        idTabel.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        NameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        EmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
        PhoneColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
        //passwordColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPassword()));
        roleColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getRole()));
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Agent");
        roleChoiceBox.setItems(roles);
        //roleChoiceBox.setValue("Agent");
        addUpdateButtonToTable();
        addDeleteButtonToTable();
        // Load data into the table
        loadAgentData();
    }

    /*
    @FXML
    private void onUpdateButtonClick() {
        // Get the selected agent from TableView
        User selectedUser = agentTabel.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            // Show alert if no agent is selected
            showAlert("No agent selected", "Please select an agent to update.");
            return;
        }

        try {
            // Load the update form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgentUpdateForm.fxml"));
            Parent root = loader.load();

            // Get controller and pass the selected agent
            AgentUpdateController controller = loader.getController();
            controller.setAgent(selectedUser, new UserDOAImpl());

            // Show update form in a new stage
            Stage stage = new Stage();
            stage.setTitle("Update Agent");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the TableView after updating
            agentTabel.setItems(FXCollections.observableArrayList(new UserDOAImpl().getAll()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */




    @FXML
    void createAccount(ActionEvent event) {
        String name = NameSignUp.getText();
        String email = EmailSignUp.getText();
        String phone = PhoneSignUp.getText();
        String password = PasswordSignUp.getText();
        String role = roleChoiceBox.getValue();
        //String role = "Agent";

        // Validate inputs
        String validationMessage = ValiditionAgentAccount.validateAllInputs(name, email, phone, password, role);
        if (validationMessage != null) {
            showAlert("Validation Error", validationMessage); // Show validation error
            return; // Stop account creation if validation fails
        }

        // Check if email exists in the database
        if (userDOA.emailExists(email)) {
            showAlert("Error", "An account with this email already exists.");
            return; // Stop account creation if email already exists
        }
        // Create a new Agent object
        //Agent agent = new Agent();
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(role);
/*
        agent.setName(name);
        agent.setEmail(email);
        agent.setPhone(phone);
        agent.setPassword(password);
        agent.setLicenseNumber(license);


 */
        // Save the agent to the database
        //agentDOA.save(agent);
        userDOA.save(user);
        loadAgentData();
        clearInputFields(); // Clear input fields after saving
    }
    private void clearInputFields() {
        NameSignUp.clear();
        EmailSignUp.clear();
        PhoneSignUp.clear();
        PasswordSignUp.clear();
    }

    private void loadAgentData() {
        // Fetch data from the database
        List<User> users = userDOA.getAll(); // Fetch users from database
        // Update the ObservableList
        userList.setAll(users);
        // Set the ObservableList to the ListView
        agentTabel.setItems(userList);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addUpdateButtonToTable() {
        UpdateAgent.setCellFactory(column -> {
            return new TableCell<User, Void>() {
                private final Button updateButton = new Button("Update");

                {
                    updateButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        updateAgent(user);
                        loadAgentData();
                        //onUpdateButtonClick();
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
            };
        });
    }

    private void addDeleteButtonToTable() {
        DeleteAgent.setCellFactory(column -> {
            return new TableCell<User, Void>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        deleteAgent(user); // Handle delete logic
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
            };
        });
    }


    private void updateAgent(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgentUpdateForm.fxml"));
            Parent root = loader.load();

            // Pass the selected agent to the update form controller
            AgentUpdateController controller = loader.getController();
            controller.setAgent(user, new UserDOAImpl());

            // Show the update form in a new stage
            Stage stage = new Stage();
            stage.setTitle("Update Agent");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the TableView after updating
            // agentTabel.setItems(FXCollections.observableArrayList(new AgentDOAImpl().getAll()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void deleteAgent(User user) {
        // Implement the logic to delete the agent
        userDOA.delete(user);  // Assuming you have a delete method in the DAO
        loadAgentData(); // Refresh table
        System.out.println("Deleted agent: " + user.getName());
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            Stage currentStage = (Stage) canclebut.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void backbut(ActionEvent event) {
        try {
            // Navigate back to the Login page by getting the previous stage (LoginController's stage)
            Stage currentStage = (Stage) backbut1.getScene().getWindow();

            // Go back to the Login page (previous scene)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePageForAdmin.fxml"));
            Parent loginRoot = loader.load();

            // Set the new scene (Login page)
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHomeButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePageForAdmin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) homeButton.getScene().getWindow();
            Scene scene = new Scene(root, 1400, 780);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(root.minWidth(-1));
            stage.setMinHeight(root.minHeight(-1));
            stage.setTitle("Home Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

