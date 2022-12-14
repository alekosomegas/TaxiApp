package com.ak.taxiapp.model.ride;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.controller.invoice.InvoiceRowController;
import com.ak.taxiapp.controller.ride.RideRowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import javax.swing.event.ChangeListener;
import java.beans.EventHandler;
import java.io.IOException;
import java.util.HashMap;

public class RideRow extends HBox {
    private Node view;
    private RideRowController controller;
    private final String FXMLPATH = "fxml/ride/RideRow.fxml";
    private Ride ride;

    public RideRow(Ride ride) {
        super();

        this.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                controller.highlight();
            } else {
                controller.unhighlight();
            }
        });

        this.ride = ride;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TaxiApplication.class.getResource(FXMLPATH));

        controller = new RideRowController();
        controller.ride = ride;
        loader.setController(controller);

        try {
            view = (Node) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getChildren().add(view);
    }

    public enum RideRowFields {
        DATE, TIME, DRIVER, CAR, CLIENT, PASSENGER, START, FINISH, CREDIT, CASH, INVOICE, NOTES
    }

    public void setValues(HashMap<RideRowFields, String> values) {
        controller.setLblCar(values.get(RideRowFields.CAR));
        controller.setLblCash(values.get(RideRowFields.CASH));
        controller.setLblClient(values.get(RideRowFields.CLIENT));
        controller.setLblCredit(values.get(RideRowFields.CREDIT));
        controller.setLblDate(values.get(RideRowFields.DATE));
        controller.setLblDriver(values.get(RideRowFields.DRIVER));
        controller.setLblFinish(values.get(RideRowFields.FINISH));
        controller.setLblNotes(values.get(RideRowFields.NOTES));
        controller.setLblInvoiceNo(values.get(RideRowFields.INVOICE));
        controller.setLblTime(values.get(RideRowFields.TIME));
        controller.setLblPassenger(values.get(RideRowFields.PASSENGER));
        controller.setLblStart(values.get(RideRowFields.START));
    }

}
