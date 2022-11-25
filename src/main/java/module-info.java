module com.ak.taxiapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    requires org.jetbrains.annotations;


    opens com.ak.taxiapp to javafx.fxml;
    exports com.ak.taxiapp;
    exports com.ak.taxiapp.model;
    opens com.ak.taxiapp.model to javafx.fxml;
    exports com.ak.taxiapp.util;
    opens com.ak.taxiapp.util to javafx.fxml;
    exports com.ak.taxiapp.controller;
    opens com.ak.taxiapp.controller to javafx.fxml;
}