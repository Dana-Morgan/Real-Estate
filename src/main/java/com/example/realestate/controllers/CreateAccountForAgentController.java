package com.example.realestate.controllers;

import com.example.realestate.models.Agent;
import com.example.realestate.services.AgentDOAImpl;
import com.example.realestate.validation.ValiditionAgentAccount;
import com.mysql.cj.Session;
import com.mysql.cj.x.protobuf.MysqlxSession;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private TableView<Agent> agentTabel;

    @FXML
    private TableColumn<Agent, Integer> idTabel;
    @FXML
    private TableColumn<Agent, String> NameColumn;
    @FXML
    private TableColumn<Agent, String> EmailColumn;
    @FXML
    private TableColumn<Agent, String> PhoneColumn;
    @FXML
    private TableColumn<Agent, String> LNColumn;
    @FXML
    private TableColumn<Agent, String> passwordColumn;

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
    private TableColumn<Agent, Void> UpdateAgent;

    @FXML
    private TableColumn<Agent, Void> DeleteAgent;

    @FXML
    private Button backbut1;
    private AgentDOAImpl agentDOA = new AgentDOAImpl();
    private ObservableList<Agent> agentList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Initialize table columns
        idTabel.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getAgentId()));
        NameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        EmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
        PhoneColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
        LNColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLicenseNumber()));
        passwordColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPassword()));
        addUpdateButtonToTable();
        addDeleteButtonToTable();

        // Load data into the table
        loadAgentData();
    }
    @FXML
    private void onUpdateButtonClick() {
        // Get the selected agent from TableView
        Agent selectedAgent = agentTabel.getSelectionModel().getSelectedItem();
        /*
        if (selectedAgent == null) {
            // Show alert if no agent is selected
            showAlert("No agent selected", "Please select an agent to update.");
            return;
        }

         */

        try {
            // Load the update form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgentUpdateForm.fxml"));
            Parent root = loader.load();

            // Get controller and pass the selected agent
            AgentUpdateController controller = loader.getController();
            controller.setAgent(selectedAgent, new AgentDOAImpl());

            // Show update form in a new stage
            Stage stage = new Stage();
            stage.setTitle("Update Agent");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the TableView after updating
            agentTabel.setItems(FXCollections.observableArrayList(new AgentDOAImpl().getAll()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void createAccount(ActionEvent event) {
        String name = NameSignUp.getText();
        String email = EmailSignUp.getText();
        String phone = PhoneSignUp.getText();
        String password = PasswordSignUp.getText();
        String license = LicenseSignUp.getText();

        // Validate inputs
        String validationMessage = ValiditionAgentAccount.validateAllInputs(name, email, phone, password, license);
        if (validationMessage != null) {
            showAlert("Validation Error", validationMessage); // Show validation error
            return; // Stop account creation if validation fails
        }

        // Check if email exists in the database
        if (agentDOA.emailExists(email)) {
            showAlert("Error", "An account with this email already exists.");
            return; // Stop account creation if email already exists
        }

        // Create a new Agent object
        Agent agent = new Agent();
        agent.setName(name);
        agent.setEmail(email);
        agent.setPhone(phone);
        agent.setPassword(password);
        agent.setLicenseNumber(license);

        // Save the agent to the database
        agentDOA.save(agent);
        loadAgentData();
        clearInputFields(); // Clear input fields after saving
    }
    private void clearInputFields() {
        NameSignUp.clear();
        EmailSignUp.clear();
        PhoneSignUp.clear();
        PasswordSignUp.clear();
        LicenseSignUp.clear();
    }
    private void loadAgentData() {
        // Fetch data from the database
        List<Agent> agents = agentDOA.getAll();
        agentList.setAll(agents);
        agentTabel.setItems(agentList);
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
            return new TableCell<Agent, Void>() {
                private final Button updateButton = new Button("Update");

                {
                    updateButton.setOnAction(event -> {
                        Agent agent = getTableView().getItems().get(getIndex());
                        updateAgent(agent);
                        onUpdateButtonClick();
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
            return new TableCell<Agent, Void>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        Agent agent = getTableView().getItems().get(getIndex());
                        deleteAgent(agent); // Handle delete logic
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


    private void updateAgent(Agent agent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/AgentUpdateForm.fxml"));
            Parent root = loader.load();

            // Pass the selected agent to the update form controller
            AgentUpdateController controller = loader.getController();
            controller.setAgent(agent, new AgentDOAImpl());

            // Show the update form in a new stage
            Stage stage = new Stage();
            stage.setTitle("Update Agent");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the TableView after updating
            agentTabel.setItems(FXCollections.observableArrayList(new AgentDOAImpl().getAll()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void deleteAgent(Agent agent) {
        // Implement the logic to delete the agent
        agentDOA.delete(agent);  // Assuming you have a delete method in the DAO
        loadAgentData(); // Refresh table
        System.out.println("Deleted agent: " + agent.getName());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePage.fxml"));
            Parent loginRoot = loader.load();

            // Set the new scene (Login page)
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

