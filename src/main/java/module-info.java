module com.ak.taxiapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    requires org.jetbrains.annotations;
    requires layout;
    requires kernel;
    requires io;
    requires java.desktop;
    requires org.controlsfx.controls;


    opens com.ak.taxiapp to javafx.fxml;
    exports com.ak.taxiapp;
    exports com.ak.taxiapp.util;
    opens com.ak.taxiapp.util to javafx.fxml;
    exports com.ak.taxiapp.controller;
    opens com.ak.taxiapp.controller to javafx.fxml;
    exports com.ak.taxiapp.model.invoice;
    opens com.ak.taxiapp.model.invoice to javafx.fxml;
    exports com.ak.taxiapp.model.car;
    opens com.ak.taxiapp.model.car to javafx.fxml;
    exports com.ak.taxiapp.model.client;
    opens com.ak.taxiapp.model.client to javafx.fxml;
    exports com.ak.taxiapp.model.driver;
    opens com.ak.taxiapp.model.driver to javafx.fxml;
    exports com.ak.taxiapp.model.ride;
    opens com.ak.taxiapp.model.ride to javafx.fxml;
    exports com.ak.taxiapp.controller.calendar;
    opens com.ak.taxiapp.controller.calendar to javafx.fxml;
    exports com.ak.taxiapp.controller.car;
    opens com.ak.taxiapp.controller.car to javafx.fxml;
    exports com.ak.taxiapp.controller.driver;
    opens com.ak.taxiapp.controller.driver to javafx.fxml;
    exports com.ak.taxiapp.controller.client;
    opens com.ak.taxiapp.controller.client to javafx.fxml;
    exports com.ak.taxiapp.controller.ride;
    opens com.ak.taxiapp.controller.ride to javafx.fxml;
    exports com.ak.taxiapp.controller.invoice;
    opens com.ak.taxiapp.controller.invoice to javafx.fxml;

    exports com.ak.taxiapp.controller.dashboard;
    opens com.ak.taxiapp.controller.dashboard to javafx.fxml;
    exports com.ak.taxiapp.controller.database;
    opens com.ak.taxiapp.controller.database to javafx.fxml;
    exports com.ak.taxiapp.controller.expenses;
    opens com.ak.taxiapp.controller.expenses to javafx.fxml;
    exports com.ak.taxiapp.controller.reports;
    opens com.ak.taxiapp.controller.reports to javafx.fxml;
    exports com.ak.taxiapp.controller.settings;
    opens com.ak.taxiapp.controller.settings to javafx.fxml;

}