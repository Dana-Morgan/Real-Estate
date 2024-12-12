module com.example.realestate {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.realestate to javafx.fxml;
    exports com.example.realestate;
    exports com.example.realestate.controllers;
    opens com.example.realestate.controllers to javafx.fxml;
}