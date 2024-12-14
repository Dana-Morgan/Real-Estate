package com.example.realestate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/realestate/views/ListingPage.fxml"));
=======

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/realestate/views/HomePage.fxml"));

>>>>>>> 13c847fcfce2a007ef8e187feac7f281f3257a37
        Scene scene = new Scene(fxmlLoader.load(), 1280, 832);
        stage.setTitle("Real Estate Application");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}