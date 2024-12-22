package com.example.realestate.controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProFormController implements Initializable {

    @FXML
    public Button AddPro_btn;

    @FXML
    public TextField Area;

    @FXML
    public Button Back_btn;

    @FXML
    public TextField Feature;

    @FXML
    public TextField Location;

    @FXML
    public TextField NumberOfRooms;

    @FXML
    public TextField Price;

    @FXML
    public ChoiceBox<String> PropertyType;

    @FXML
    public ChoiceBox<String> SellingType;

    @FXML
    public TextField Title;

    @FXML
    public TextField UploadImage;
    public Button Update_btn;
    public TextField title;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PropertyType.getItems().addAll("Home", "building", "apartment");
        SellingType.getItems().addAll("Rent", "sale");
    }


    private static final List<Property> propertyList = new ArrayList<>();

    public static List<Property> getPropertyList() {
        return propertyList;
    }

    public void BackToProList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/realestate/views/propertiesTable.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
    }

    public record Property(String title, String location, double area, String numberOfRooms, String feature, String uploadImage, String propertyType, String sellingType, String price) {


    }

    @FXML
    public void handleAddProperty(ActionEvent event)  {
        String title = Title.getText();
        String location = Location.getText();
        String price = Price.getText();
        String area = Area.getText();
        String numberOfRooms = NumberOfRooms.getText();
        String feature = Feature.getText();
        String uploadImage = UploadImage.getText();
        String propertyType = PropertyType.getValue();
        String sellingType = SellingType.getValue();

        if (title.isEmpty() || location.isEmpty() || price.isEmpty() || area.isEmpty() ||
                numberOfRooms.isEmpty() || feature.isEmpty() || uploadImage.isEmpty() || propertyType == null || sellingType == null) {
            showError("Please fill in all fields!");
            return;
        }

        try {
            Property newProperty = new Property(title, location, Double.parseDouble(area), numberOfRooms, feature, uploadImage, propertyType, sellingType, price);

            propertyList.add(newProperty);
            clearFields();


        } catch (NumberFormatException e) {
            showError("Price and Area must be valid numbers!");
        }

    }
    @FXML
    public void handleUpdateProperty(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addproperetyinfo/UpdateProForm.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Update Property");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Unable to open Update Property form!");
            e.printStackTrace();
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        Title.clear();
        Location.clear();
        Price.clear();
        Area.clear();
        NumberOfRooms.clear();
        Feature.clear();
        UploadImage.clear();
        PropertyType.getSelectionModel().clearSelection();
        SellingType.getSelectionModel().clearSelection();
    }


    }

