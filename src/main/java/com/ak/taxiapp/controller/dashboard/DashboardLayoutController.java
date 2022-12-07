package com.ak.taxiapp.controller.dashboard;

import com.ak.taxiapp.controller.invoice.InvoiceCard;
import com.ak.taxiapp.util.Controller;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardLayoutController extends Controller implements Initializable {

    public VBox vbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbox.getChildren().add(new Rectangle(110,110, Color.RED));

    }
}
