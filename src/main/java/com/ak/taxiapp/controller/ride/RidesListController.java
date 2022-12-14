package com.ak.taxiapp.controller.ride;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.Formatter;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.model.ride.RideRow;
import com.ak.taxiapp.util.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RidesListController extends Controller {
    public VBox vBoxContainer;
    private ObservableList<Ride> rides = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            search();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        createRidesRows();
    }

    private void search() throws SQLException {
        try {
            ObservableList<Ride> resultSet = RideDAO.searchAllRides();
            rides = resultSet;
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    public void onTabRide(ActionEvent event) {
        TaxiApplication.showRideView();
    }

    public void onOK(ActionEvent event) {
    }

    public void onCancel(ActionEvent event) {
    }

    public void onClear(ActionEvent event) {
    }

    public void createRidesRows() {
        vBoxContainer.getChildren().clear();

        for (Ride ride : rides) {
            // values to pass to RideRow
            HashMap<RideRow.RideRowFields, String> values = new HashMap<>();
            RideRow rideRow = new RideRow(ride);

            values.put(RideRow.RideRowFields.DATE, Formatter.DATE_DISPLAY.format(ride.getDate()));
            values.put(RideRow.RideRowFields.TIME, ride.getRidesTimeStart() +" - "+
                    ride.getRidesTimeEnd());
            values.put(RideRow.RideRowFields.DRIVER,
                    ride.getRidesDriverId() +". "+ ride.getRidesDriver());
            values.put(RideRow.RideRowFields.CAR,
                    ride.getRidesCarId() +". "+ ride.getRidesCar());
            values.put(RideRow.RideRowFields.CLIENT,
                    ride.getRidesClientId() +". "+ ride.getRidesClient());
            values.put(RideRow.RideRowFields.PASSENGER, ride.getRidesPassenger());
            values.put(RideRow.RideRowFields.START, ride.getRidesFrom());
            values.put(RideRow.RideRowFields.FINISH, ride.getRidesTo());
            values.put(RideRow.RideRowFields.CREDIT, String.valueOf(ride.getRidesCredit()));
            values.put(RideRow.RideRowFields.CASH, String.valueOf(ride.getRidesCash()));
            values.put(RideRow.RideRowFields.INVOICE, ride.getRidesInvoiceId());
            values.put(RideRow.RideRowFields.NOTES, ride.getRidesNotes());


            rideRow.setValues(values);

            vBoxContainer.getChildren().add(rideRow);
        }
    }

    @Override
    public void updateView() {
        try {
            search();
            createRidesRows();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
