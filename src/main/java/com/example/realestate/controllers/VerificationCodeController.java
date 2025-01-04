package com.example.realestate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VerificationCodeController {

    @FXML
    private PasswordField verificationCodeField;

    @FXML
    private Button nextButton;

    @FXML
    private Button cancelButton;

    private String correctCode;
    private String userEmail;

    public void setCorrectCode(String code) {
        this.correctCode = code;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    @FXML
    private void handleNextButton() {
        String enteredCode = verificationCodeField.getText().trim();

        if (enteredCode.isEmpty()) {
            showAlert("Error", "Please enter the verification code!", Alert.AlertType.ERROR);
            return;
        }

        if (enteredCode.equals(correctCode)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/ResetYourPassword.fxml"));
                Stage stage = (Stage) nextButton.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);


                ResetPasswordController controller = loader.getController();
                controller.setUserEmail(userEmail);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "The verification code is incorrect!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancelButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/ForgetPassword.fxml"));
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
