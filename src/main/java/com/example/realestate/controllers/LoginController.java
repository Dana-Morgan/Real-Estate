package com.example.realestate.controllers;

import com.example.realestate.models.User;
import com.example.realestate.services.AdminDAOImpl;
import com.example.realestate.services.AgentDAOImpl;
import com.example.realestate.services.UserDOAImpl;
import com.example.realestate.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField EmailIDLogin;

    @FXML
    private Text forgetbut;

    @FXML
    private Button loginbut;

    @FXML
    private PasswordField passordLogin;
    private AgentDAOImpl agentDAO = new AgentDAOImpl(); // Instantiate AgentDOAImpl
    private AdminDAOImpl adminDOA = new AdminDAOImpl(); // Instantiate AgentDOAImpl
    private UserDOAImpl userDAO = new UserDOAImpl();
    private MouseEvent event;

    @FXML
    void handleResetPassword(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/ForgetPassword.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) ((Text) event.getSource()).getScene().getWindow();

            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*

    @FXML
    void loginbutton(ActionEvent event) {
        String email = EmailIDLogin.getText();
        String password = passordLogin.getText();

        // Call login method to check credentials
        Agent agent = agentDAO.login(email, password);
        Admin admin = adminDOA.login(email, password);

        if (agent != null || admin != null ) {
            // If login is successful, navigate to the HomePage
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/realestate/views/HomePageForAdmin.fxml"));
                Parent newPageRoot = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(newPageRoot);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If login fails, show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password. Please try again.");
            alert.showAndWait();
        }
    }

     */

    @FXML
    private void loginbutton(ActionEvent event) throws IOException {
        String email = EmailIDLogin.getText();
        String password = passordLogin.getText();

        // تحقق من صحة بيانات المستخدم
        User user = userDAO.login(email, password);

        if (user != null) {
            // تخزين المستخدم في الجلسة
            SessionManager.setLoggedInUser(user);
            System.out.println("User role after login: " + user.getRole());

            String role = user.getRole(); // الحصول على دور المستخدم

            // تحديد الصفحة بناءً على الدور
            String fxmlFile;
            if ("Agent".equalsIgnoreCase(role)) {
                fxmlFile = "/com/example/realestate/views/HomePageForAgent.fxml";
            } else if ("Admin".equalsIgnoreCase(role)) {
                fxmlFile = "/com/example/realestate/views/HomePageForAdmin.fxml";
            } else {
                // إذا كان الدور غير معرّف
                System.out.println("Unknown role: " + role);
                return;
            }

            // تحميل الصفحة المناسبة
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // عرض الصفحة
            Stage stage = (Stage) loginbut.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            // في حالة فشل تسجيل الدخول
            System.out.println("Invalid email or password");
        }
    }





}
