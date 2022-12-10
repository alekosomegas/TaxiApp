package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.util.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SingleInvoiceController extends Controller {
    public TableView<Ride> tvInvoiceRidesTable;
    public TableColumn<Ride, String> tcInvoiceRideDate;
    public TableColumn<Ride, String> tcInvoiceRidePassenger;
    public TableColumn<Ride, String> tcInvoiceRideItinerary;
    public TableColumn<Ride, Integer> tcInvoiceRidePrice;
    public TableColumn<Ride, String> tcInvoiceRideNotes;
    public TableColumn<Ride, String> tcInvoiceRideEdit;
    public TableColumn<Ride, String> tcInvoiceRideRemove;

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() throws SQLException {
        tcInvoiceRideDate.setCellValueFactory(ride -> ride.getValue().ridesDateProperty());
        tcInvoiceRidePassenger.setCellValueFactory(ride -> ride.getValue().ridesPassengerProperty());
        tcInvoiceRideItinerary.setCellValueFactory(ride -> ride.getValue().ridesFromProperty());
        tcInvoiceRidePrice.setCellValueFactory(ride -> ride.getValue().ridesTotalProperty().asObject());
        tcInvoiceRideNotes.setCellValueFactory(ride -> ride.getValue().ridesNotesProperty());

        tvInvoiceRidesTable.setItems(RideDAO.searchRidesByInvoiceId("01/1"));
    }

    public void onNextDay(ActionEvent event) {
    }

    public void onPrevDay(ActionEvent event) {
    }

    public void onOK(ActionEvent event) {
    }

    public void onCancel(ActionEvent event) {
    }

    public void onClear(ActionEvent event) {
    }

    public void onTabOverview(ActionEvent event) {
        TaxiApplication.showInvoicesView();
    }

}
