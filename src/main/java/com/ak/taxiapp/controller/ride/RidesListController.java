package com.ak.taxiapp.controller.ride;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.Formatter;
import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.client.ClientDAO;
import com.ak.taxiapp.model.driver.Driver;
import com.ak.taxiapp.model.driver.DriverDAO;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.model.ride.RideRow;
import com.ak.taxiapp.util.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RidesListController extends Controller {
    public VBox vBoxContainer;
    public ToggleButton tgToday;
    public ToggleButton tgWeek;
    public ToggleButton tgMonth;
    public ToggleGroup tgStatus;
    private ToggleButton selectedToggle;
    public SearchableComboBox<String> searchCbDrivers;
    public SearchableComboBox<String> searchCbClients;
    private String selectedClient;
    private String selectedDriver;
    public Label lblMonth;
    public Label lblTotal;
    public Button tabOverview;
    public Label lblOverviewYear;
    private ObservableList<Ride> rides = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedMonth = LocalDate.now().getMonthValue()-1;
        selectedYear = LocalDate.now().getYear();
        lblMonth.setText(months[selectedMonth].toString());
        selectedToggle = tgToday;
        try {
            initClientCb();
            initDriverCb();
//            search();
            filter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        createRidesRows();

        lblOverviewYear.setText("Rides Overview "+ selectedYear);
    }



    private void initClientCb() throws SQLException {
        ObservableList<String> clientIds = FXCollections.observableArrayList();
        ObservableList<Client> clientsList = ClientDAO.searchAllClients();
        clientIds.add("ALL");

        for (Client client : clientsList) {
            clientIds.add(client.getClient_id() + ". " + client.getClient_name());
        }
        searchCbClients.setItems(clientIds);
        searchCbClients.setValue("ALL");
        selectedClient = "ALL";

        searchCbClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                if (newValue != null && !newValue.equals(selectedClient)) {
                    selectedClient = newValue;
                    filter();
                }
            }
        });

    }

    private void initDriverCb() throws SQLException {
        ObservableList<String> driversIds = FXCollections.observableArrayList();
        ObservableList<Driver> driversList = DriverDAO.searchDrivers();
        driversIds.add("ALL");
        for (Driver driver : driversList) {
            driversIds.add(driver.getDriver_id() + ". " + driver.getDriver_name());
        }
        searchCbDrivers.setItems(driversIds);
        searchCbDrivers.setValue("ALL");
        selectedDriver = "ALL";

        searchCbDrivers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                if (newValue != null && !newValue.equals(selectedDriver)) {
                    selectedDriver = newValue;
                    filter();
                }
            }
        });
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

    public void onClear() {
        searchCbDrivers.setValue("ALL");
        searchCbClients.setValue("ALL");
        filter();
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

            values.put(RideRow.RideRowFields.CREDIT,
                    ride.getRidesCredit() == 0 ? "" : String.valueOf(ride.getRidesCredit()));
            values.put(RideRow.RideRowFields.CASH,
                    ride.getRidesCash() == 0 ? "" : String.valueOf(ride.getRidesCash()));

            values.put(RideRow.RideRowFields.INVOICE,
                    ride.getRidesInvoiceId().equals("0") ? "" : ride.getRidesInvoiceId());

            values.put(RideRow.RideRowFields.NOTES, ride.getRidesNotes());


            rideRow.setValues(values);

            vBoxContainer.getChildren().add(rideRow);
        }
        buildTotal();
    }

    @Override
    public void updateView() {
        try {
//            search();
            filter();
//            createRidesRows();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public enum Months {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER , DECEMBER
    }
    public Months[] months = Months.values();
    private int selectedMonth;
    private int selectedYear;
    public void onPrevMonth() {
        tgStatus.selectToggle(tgMonth);
//        tgMonth.setSelected(true);
        selectedMonth--;
        if (selectedMonth < 0) {
            selectedMonth = 11;
            selectedYear--;
            lblOverviewYear.setText("Rides Overview "+ selectedYear);
        }
        String month = months[selectedMonth].toString();
        lblMonth.setText(month);
        selectedToggle = tgMonth;

        filter();
    }

    public void onNextMonth() {
        tgStatus.selectToggle(tgMonth);
//        tgMonth.setSelected(true);

        selectedMonth++;
        if (selectedMonth > 11) {
            selectedMonth = 0;
            selectedYear++;
            lblOverviewYear.setText("Rides Overview "+ selectedYear);
        }
//        selectedMonth = selectedMonth % 12;
        String month = months[selectedMonth].toString();
        lblMonth.setText(month);
        selectedToggle = tgMonth;

        filter();
    }

    public void onStatusChanged(ActionEvent event) {
        if (event.getSource() == selectedToggle) {
            tgStatus.selectToggle(selectedToggle);
            selectedToggle.setSelected(true);
            return;
        }

        selectedToggle = (ToggleButton) tgStatus.getSelectedToggle();
        if (tgToday.isSelected() || tgWeek.isSelected()) {
            selectedMonth = LocalDate.now().getMonthValue() -1;
            lblMonth.setText(months[selectedMonth].toString());
        }

        filter();

    }

    private void filter() {
        String clientId = "";
        String driverId = "";
        String period = selectedToggle.getText();

        if (!selectedClient.equals("ALL")) {clientId = selectedClient.split("\\.")[0];}

        if (!selectedDriver.equals("ALL")) {driverId = selectedDriver.split("\\.")[0];}

        rides.clear();
        try {
            rides = RideDAO.searchByMonthClientDriver(selectedYear,
                    String.format("%02d", selectedMonth+1), clientId, driverId, period);
            createRidesRows();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildTotal() {
        double total = 0.00;
        for(Ride ride : rides) {
            total += ride.getRidesTotal();
        }
        lblTotal.setText(String.format("%,.2f", total));
    }
}
