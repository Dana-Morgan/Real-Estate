package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomePageController {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField sizeTextField, propertyTypeTextField, roomsTextField, areaTextField, priceTextField, dateTextField;

    @FXML
    private Button submitListingButton, searchButton;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TableView<Property> propertyTable;

    private ObservableList<Property> propertyList = FXCollections.observableArrayList();

    // البحث عن العقارات بناءً على المدخلات
    @FXML
    private void searchProperties() {
        String size = sizeTextField.getText();
        String propertyType = propertyTypeTextField.getText();
        String rooms = roomsTextField.getText();
        String area = areaTextField.getText();
        String price = priceTextField.getText();
        String date = dateTextField.getText();

        propertyList.clear(); // تفريغ الجدول قبل عرض النتائج الجديدة

        String url = "jdbc:mysql://localhost:40000/realestate";
        String user = "root";
        String password = "Ruaa@276";

        String query = "SELECT * FROM property WHERE " +
                "(name LIKE ? OR ? = '') AND " +
                "(property_type LIKE ? OR ? = '') AND " +
                "(number_of_rooms = ? OR ? = '') AND " +
                "(area LIKE ? OR ? = '') AND " +
                "(price LIKE ? OR ? = '') AND " +
                "(date LIKE ? OR ? = '')";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + size + "%");
            preparedStatement.setString(2, size);
            preparedStatement.setString(3, "%" + propertyType + "%");
            preparedStatement.setString(4, propertyType);
            preparedStatement.setString(5, rooms);
            preparedStatement.setString(6, rooms);
            preparedStatement.setString(7, "%" + area + "%");
            preparedStatement.setString(8, area);
            preparedStatement.setString(9, "%" + price + "%");
            preparedStatement.setString(10, price);
            preparedStatement.setString(11, "%" + date + "%");
            preparedStatement.setString(12, date);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Property property = new Property(
                        resultSet.getString("image"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getString("price"),
                        resultSet.getString("property_type"),
                        resultSet.getString("status"),
                        resultSet.getInt("number_of_rooms"),
                        resultSet.getString("property_features"),
                        resultSet.getString("area"),
                        resultSet.getString("date")
                );
                propertyList.add(property);
            }

            propertyTable.setItems(propertyList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToListingPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("src/main/resources/com/example/realestate/views/ListingPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
    }

    public void goToHome(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/HomePage.fxml");
    }

    public void goToListing(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/ListingPage.fxml");
    }

    public void goToCustomerTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/customerTable.fxml");
    }

    public void goToCustomerInteractionTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/CustomerInteractionTable.fxml");
    }

    public void goToAgreementTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/AgreementTable.fxml");
    }

    public void goTopropertiesTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/propertiesTable.fxml");
    }

    public void goToAgentTable(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/Create-Account.fxml");
    }

    public void goToLoginPage(ActionEvent event) throws IOException {
        loadPage(event, "/com/example/realestate/views/Login.fxml");
    }

    private void loadPage(ActionEvent event, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
    }
}


