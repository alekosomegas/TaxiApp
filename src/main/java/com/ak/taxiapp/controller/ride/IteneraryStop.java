package com.ak.taxiapp.controller.ride;

import com.ak.taxiapp.TaxiApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class IteneraryStop extends VBox {
    private Node view;
    private ItineraryStopController controller;
    private final String FXMLPATH = "fxml/ride/ItineraryStop.fxml";
    private SingleRideController singleRideController;

    public IteneraryStop(SingleRideController singleRideController) {
        super();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TaxiApplication.class.getResource(FXMLPATH));

        controller = new ItineraryStopController();
        loader.setController(controller);
        this.singleRideController = singleRideController;
        controller.iteneraryStop = this;

        try {
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getChildren().add(view);
    }

    public void onRemoveStop() {
        singleRideController.removeStop(this);
    }

    public String getStop() {
        return controller.getStop();
    }

}
