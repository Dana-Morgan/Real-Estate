
package com.example.realestate.controllers;

import com.example.realestate.models.Property;
import com.example.realestate.services.PropertyDAO;
import com.example.realestate.services.PropertyDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


public class UpdateProController {

    @FXML
    private TextField Title, Location, Price, Area, NumberOfRooms, Feature, UploadImage;
    @FXML
    private ChoiceBox<String> PropertyType, SellingType;
    @FXML
    private Button Update_btn;

    private Property propertyToUpdate = null;
    private final PropertyDAO propertyDAO = new PropertyDAOImpl();

    public void setPropertyToUpdate(Property property) {
        this.propertyToUpdate = property;
        populateFormWithData(property);
    }

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
    public void handleUpdateProperty(ActionEvent event) {
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
            LocalDate currentDate = LocalDate.now();

            // Set the updated values on the property
            propertyToUpdate.setName(title);
            propertyToUpdate.setLocation(location);
            propertyToUpdate.setPrice(price);
            propertyToUpdate.setArea(area);
            propertyToUpdate.setNumberOfRooms(Integer.parseInt(numberOfRooms));  // Make sure this is a valid number
            propertyToUpdate.setPropertyFeatures(feature);
            propertyToUpdate.setImage(uploadImage);
            propertyToUpdate.setPropertyType(propertyType);
            propertyToUpdate.setStatus(sellingType);
            propertyToUpdate.setDate(currentDate);


            propertyDAO.updateProperty(propertyToUpdate);

            showInfo("Property updated successfully!");

        } catch (NumberFormatException e) {
            showError("Price and Area must be valid numbers!");
        } catch (Exception e) {
            showError("An error occurred while updating the property!");
        }
    }



    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void BackToProList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/realestate/views/PropertiesTable.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1280, 832);
        stage.setScene(scene);
        stage.show();
}
}