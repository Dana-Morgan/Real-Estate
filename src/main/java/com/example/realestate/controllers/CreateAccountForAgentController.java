package com.example.realestate.controllers;

import com.example.realestate.models.Agent;
import com.example.realestate.services.AgentDOAImpl;
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
        addUpdateButtonToTable();
        addDeleteButtonToTable();

        // Load data into the table
        loadAgentData();
    }
    @FXML
    void createAccount(ActionEvent event) {
        Agent agent = new Agent();
        agent.setName(NameSignUp.getText());
        agent.setEmail(EmailSignUp.getText());
        agent.setPhone(PhoneSignUp.getText());
        agent.setPassword(PasswordSignUp.getText());
        agent.setLicenseNumber(LicenseSignUp.getText());

        agentDOA.save(agent);
        loadAgentData();
        agentDOA.getAll();
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePage.fxml"));
            Parent newPageRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(newPageRoot);
            stage.setScene(scene);
            stage.show();

             */
    }
    private void loadAgentData() {
        // Fetch data from the database
        List<Agent> agents = agentDOA.getAll();
        agentList.setAll(agents);
        agentTabel.setItems(agentList);
    }
    private void addUpdateButtonToTable() {
        UpdateAgent.setCellFactory(column -> {
            return new TableCell<Agent, Void>() {
                private final Button updateButton = new Button("Update");

                {
                    updateButton.setOnAction(event -> {
                        Agent agent = getTableView().getItems().get(getIndex());
                        updateAgent(agent); // Handle update logic
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
        // Implement the logic to update the agent
        System.out.println("Updating agent: " + agent.getName());
        // You can open a new dialog with pre-filled values or update inline
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

