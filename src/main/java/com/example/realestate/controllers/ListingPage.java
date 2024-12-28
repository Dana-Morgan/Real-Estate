package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListingPage {
    @FXML
    private TextField sizeTextField;
    @FXML
    private TextField propertyTypeTextField;
    @FXML
    private TextField roomsTextField;
    @FXML
    private TextField areaTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private Button submitListingButton;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Property> propertyTable;

    @FXML
    private TableColumn<Property, String> imageColumn;
    @FXML
    private TableColumn<Property, String> nameColumn;
    @FXML
    private TableColumn<Property, String> locationColumn;
    @FXML
    private TableColumn<Property, String> priceColumn;
    @FXML
    private TableColumn<Property, String> propertyTypeColumn;
    @FXML
    private TableColumn<Property, Integer> numberOfRoomsColumn;
    @FXML
    private TableColumn<Property, String> propertyFeaturesColumn;
    @FXML
    private TableColumn<Property, String> areaColumn;
    @FXML
    private TableColumn<Property, String> statusColumn;
    @FXML
    private TableColumn<Property, String> dateColumn;

    private ObservableList<Property> propertyList;

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        propertyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("propertyType"));
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        propertyFeaturesColumn.setCellValueFactory(new PropertyValueFactory<>("propertyFeatures"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    try {
                        Image img = new Image("file:" + image);
                        imageView.setImage(img);
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        imageView.setPreserveRatio(true);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });

        propertyList = FXCollections.observableArrayList();
        propertyTable.setItems(propertyList);

        loadDataFromDatabase(null);
    }

    @FXML
    private void searchProperties() {
        String size = sizeTextField.getText();
        String propertyType = propertyTypeTextField.getText();
        String rooms = roomsTextField.getText();
        String area = areaTextField.getText();
        String price = priceTextField.getText();
        String date = dateTextField.getText();

        String query = "SELECT * FROM property WHERE 1=1";

        if (!size.isEmpty()) query += " AND size LIKE ?";
        if (!propertyType.isEmpty()) query += " AND property_type LIKE ?";
        if (!rooms.isEmpty()) query += " AND number_of_rooms = ?";
        if (!area.isEmpty()) query += " AND area LIKE ?";
        if (!price.isEmpty()) query += " AND price <= ?";
        if (!date.isEmpty()) query += " AND date LIKE ?";

        loadDataFromDatabase(query, size, propertyType, rooms, area, price, date);
    }

    private void loadDataFromDatabase(String query, String... params) {
        String url = "jdbc:mysql://localhost:40000/realestate";
        String user = "root";
        String password = "Ruaa@276";

        propertyList.clear();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement;
            if (query == null) {
                preparedStatement = connection.prepareStatement("SELECT * FROM property");
            } else {
                preparedStatement = connection.prepareStatement(query);
                int index = 1;
                for (String param : params) {
                    if (param != null && !param.isEmpty()) {
                        preparedStatement.setString(index++, "%" + param + "%");
                    }
                }
            }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
