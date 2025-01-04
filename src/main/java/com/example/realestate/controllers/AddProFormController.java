package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import com.example.realestate.services.PropertyDAO;
import com.example.realestate.services.PropertyDAOImpl;
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
import java.time.LocalDate;
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

    @FXML
    public LocalDate date;

    private Scene scene;
    private Property propertyToUpdate = null;

    private final PropertyDAO propertyDAO = new PropertyDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PropertyType.getItems().addAll("Residential", "Commercial", "Land","Vacation");
        SellingType.getItems().addAll("Available", "Rented", "Pending", "Sold");

        if (propertyToUpdate != null) {
            // If a property is set for update, populate the fields with its data
            populateFormWithData(propertyToUpdate);
        }
    }

    // Method to set the property that will be updated
    public void setPropertyToUpdate(Property property) {
        this.propertyToUpdate = property;
    }

    // Method to populate the form with existing property data
    private void populateFormWithData(Property property) {
        Title.setText(property.getName());
        Location.setText(property.getLocation());
        Price.setText(property.getPrice());
        Area.setText(property.getArea());
        NumberOfRooms.setText(String.valueOf(property.getNumberOfRooms()));
        Feature.setText(property.getPropertyFeatures());
        UploadImage.setText(property.getImage());
        PropertyType.setValue(property.getPropertyType());
        SellingType.setValue(property.getStatus());
    }

    @FXML
    public void handleAddProperty(ActionEvent event) {
        String title = Title.getText();
        String location = Location.getText();
        String price = Price.getText();
        String area = Area.getText();
        String numberOfRooms = NumberOfRooms.getText();
        String feature = Feature.getText();
        String uploadImage = UploadImage.getText();
        String propertyType = PropertyType.getValue();
        String sellingType = SellingType.getValue();

        // Validate input fields
        if (title.isEmpty() || location.isEmpty() || price.isEmpty() || area.isEmpty() ||
                numberOfRooms.isEmpty() || feature.isEmpty() || uploadImage.isEmpty() || propertyType == null || sellingType == null) {
            showError("Please fill in all fields!");
            return;
        }

        try {
            // Get current date for the property
            LocalDate currentDate = LocalDate.now();

            if (propertyToUpdate != null) {
                // If updating an existing property
                propertyToUpdate.setName(title);
                propertyToUpdate.setLocation(location);
                propertyToUpdate.setPrice(price);
                propertyToUpdate.setArea(area);
                propertyToUpdate.setNumberOfRooms(Integer.parseInt(numberOfRooms));
                propertyToUpdate.setPropertyFeatures(feature);
                propertyToUpdate.setImage(uploadImage);
                propertyToUpdate.setPropertyType(propertyType);
                propertyToUpdate.setStatus(sellingType);
                propertyToUpdate.setDate(currentDate);

                propertyDAO.updateProperty(propertyToUpdate); // Update the property in the database
            } else {
                // Create a new Property object if not updating
                Property newProperty = new Property(title, location, price, area, Integer.parseInt(numberOfRooms),
                        feature, uploadImage, propertyType, sellingType, currentDate);

                propertyDAO.addProperty(newProperty); // Add the property to the database
            }

            // Update the properties table
            updatePropertiesTable();

            // Clear fields after adding/updating the property
            clearFields();

        } catch (NumberFormatException e) {
            showError("Price and Area must be valid numbers!");
        } catch (Exception e) {
            showError("An error occurred while adding or updating the property!");
        }
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

    // Method to reload properties table after adding/updating the property
    private void updatePropertiesTable() {
        try {
            // Get the current scene's stage
            Stage stage = (Stage) AddPro_btn.getScene().getWindow();

            // Load the PropertiesTable.fxml to update the table view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/PropertiesTable.fxml"));
            Parent root = loader.load();
            PropertiesTableController controller = loader.getController();
            controller.loadData(); // Reload the data

            // Set the scene back to the stage
            Scene scene = new Scene(root, 1280, 832);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Error updating the properties table!");
        }
    }

    // Go back to the properties list page
    public void BackToProList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/realestate/views/PropertiesTable.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
    }
}
