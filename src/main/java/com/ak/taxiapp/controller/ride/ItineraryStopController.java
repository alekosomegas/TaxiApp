package com.ak.taxiapp.controller.ride;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ItineraryStopController implements Initializable {


    @FXML TextField tfStop;
    @FXML Label lblStop;
    SingleRideController singleRideController;
    IteneraryStop iteneraryStop ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ItineraryStopController() {
    }

    @FXML
    public void onRemoveStop(ActionEvent event) {
        iteneraryStop.onRemoveStop();

    }

    public String getStop() {
        return tfStop.getText();
    }


}
