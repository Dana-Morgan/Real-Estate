package com.example.realestate.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Random;
import com.example.realestate.services.AgentDOAImpl;
import com.example.realestate.models.Agent;
import com.example.realestate.utils.SendEmail;
public class ForgetPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Button backButton;

    @FXML
    private Button sendButton;

    private final AgentDOAImpl agentService = new AgentDOAImpl();

    private String verificationCode;
    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/Login.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSendButton() {
        String userEmail = emailField.getText().trim();

        if (userEmail.isEmpty()) {
            showAlert("Error", "Please enter your email!", Alert.AlertType.ERROR);
            return;
        }
        Agent agent = agentService.getByEmail(userEmail);
        if (agent == null) {
            showAlert("Error", "This email is not registered!", Alert.AlertType.ERROR);
            return;
        }
        verificationCode = generateRandomCode();
        String emailSubject = "Your Verification Code for Real Estate Application";
        String emailBody = "Dear User,\n\nYour verification code for resetting your password is:\n\n" + verificationCode;

        sendButton.setDisable(true);

        new Thread(() -> {
            SendEmail sendEmail = new SendEmail(userEmail, emailSubject, emailBody);
            sendEmail.send();

            Platform.runLater(() -> {
                showAlert("Success", "Verification code sent to your email!", Alert.AlertType.INFORMATION);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/VerificationCode.fxml"));
                    Stage stage = (Stage) sendButton.getScene().getWindow();
                    Scene scene = new Scene(loader.load());

                    VerificationCodeController controller = loader.getController();
                    controller.setCorrectCode(verificationCode);
                    controller.setUserEmail(userEmail);

                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendButton.setDisable(false);
            });
        }).start();
    }
    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
