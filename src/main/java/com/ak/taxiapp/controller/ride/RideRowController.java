package com.ak.taxiapp.controller.ride;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class RideRowController extends Controller {
    public Ride ride;

    @FXML public Label lblDate;
    @FXML public Label lblTime;
    @FXML public Label lblDriver;
    @FXML public Label lblCar;
    @FXML public Label lblClient;
    @FXML public Label lblPassenger;
    @FXML public Label lblStart;
    @FXML public Label lblFinish;
    @FXML public Label lblCredit;
    @FXML public Label lblCash;
    @FXML public Label lblInvoiceNo;
    @FXML public HBox hbRowContainer;
    @FXML
    public Label lblNotes;


    @FXML
    public void onEdit() throws SQLException {
        TaxiApplication.showRideView(ride);
    }

    public void traverse(Pane pane, Integer mode) {
        for (Node node : pane.getChildren()) {
            if (node instanceof Pane) {
                Pane child = (Pane) node;
                traverse(child, mode);
            } else if (node instanceof Label) {
                Label label = (Label) node;
                if (label.getId() != null) {
                    if(mode == 1) {
                        label.getStyleClass().add("text--table--white");
                    } else {
                        // TODO: issue with css? not able to add style check css error
                        label.getStyleClass().remove("text--table--white");
                    }
                }
            }
        }

    }
    public void highlight() {
        traverse(hbRowContainer, 1);

    }
    public void unhighlight() {
        traverse(hbRowContainer, 2);
    }

    public void setLblDate(String lblDate) {
        this.lblDate.setText(lblDate);
    }

    public void setLblTime(String lblTime) {
        this.lblTime.setText(lblTime);
    }

    public void setLblDriver(String lblDriver) {
        this.lblDriver.setText(lblDriver);
    }

    public void setLblCar(String lblCar) {
        this.lblCar.setText(lblCar);
    }

    public void setLblClient(String lblClient) {
        this.lblClient.setText(lblClient);
    }

    public void setLblPassenger(String lblPassenger) {
        this.lblPassenger.setText(lblPassenger);
    }

    public void setLblStart(String lblStart) {
        this.lblStart.setText(lblStart);
    }

    public void setLblFinish(String lblFinish) {
        this.lblFinish.setText(lblFinish);
    }

    public void setLblCredit(String lblCredit) {
        this.lblCredit.setText(lblCredit);
    }

    public void setLblCash(String lblCash) {
        this.lblCash.setText(lblCash);
    }

    public void setLblInvoiceNo(String lblInvoiceNo) {
        this.lblInvoiceNo.setText(lblInvoiceNo);
    }

    public void setLblNotes(String lblNotes) {
        this.lblNotes.setText(lblNotes);
    }
}
