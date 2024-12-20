module com.example.realestate {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.logging;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.realestate.models to javafx.base, org.hibernate.orm.core;
    opens com.example.realestate to javafx.fxml;
    opens com.example.realestate.utils to javafx.fxml;
    opens com.example.realestate.controllers to javafx.fxml;

    exports com.example.realestate;
    exports com.example.realestate.utils;
    exports com.example.realestate.models;
    exports com.example.realestate.controllers;
}
