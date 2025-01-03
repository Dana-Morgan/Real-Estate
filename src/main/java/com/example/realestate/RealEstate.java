package com.example.realestate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class RealEstate extends Application {

    private static final Logger LOGGER = Logger.getLogger(RealEstate.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEstate.class.getResource("/com/example/realestate/views/HomePage.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1400, 780);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setMinWidth(root.minWidth(-1));
        stage.setMinHeight(root.minHeight(-1));
        stage.setTitle("Real Estate Application");
        stage.show();
    }
}