package com.ak.taxiapp.controller.ride;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.client.ClientDAO;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.util.GeneratePDF;
import com.ak.taxiapp.model.invoice.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class RidesByClientViewController extends RidesViewController{

    public ChoiceBox clientsChoiceBox;
    public ChoiceBox clientsChoiceBoxMonth;
    private final ObservableList<String> MONTHS = FXCollections.observableArrayList(
            "Any", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    public ChoiceBox clientsChoiceBoxYear;
    private final ObservableList<String> YEARS = FXCollections.observableArrayList(
            "Any");
    private List<Integer> yearsList = IntStream.rangeClosed(2000, LocalDate.now().getYear()).boxed().toList();
    private int selectedMonthIndex;
    private Object selectedYear;
    private Object selectedClient;
    private Client client;

    private ObservableList<Ride> allRides;


    @FXML
    private void initialize() throws SQLException {

        setColumnValueFactory();
        initComboBoxes();

        searchAllRides();
    }

    /*
     * Sets the FXML Values to the values taken from equivalent variable of the ride object
     * for each column in the table in the rides view.
     */
    private void setColumnValueFactory() {
        ridesIdCol.setCellValueFactory(cellData -> cellData.getValue().rides_idProperty().asObject());
        ridesDateCol.setCellValueFactory(cellData -> cellData.getValue().ridesDateProperty());
        ridesStartCol.setCellValueFactory(cellData -> cellData.getValue().ridesTimeStartProperty());
        ridesEndCol.setCellValueFactory(cellData -> cellData.getValue().ridesTimeEndProperty());
        ridesClientIdCol.setCellValueFactory(cellData -> cellData.getValue().ridesClientIdProperty().asObject());
        ridesClientCol.setCellValueFactory(cellData -> cellData.getValue().ridesClientProperty());
        ridesFromCol.setCellValueFactory(cellData -> cellData.getValue().ridesFromProperty());
        ridesToCol.setCellValueFactory(cellData -> cellData.getValue().ridesToProperty());
        ridesStopsCol.setCellValueFactory(cellData -> cellData.getValue().ridesStopsProperty());
        ridesCashCol.setCellValueFactory(cellData -> cellData.getValue().ridesCashProperty().asObject());
        ridesCreditCol.setCellValueFactory(cellData -> cellData.getValue().ridesCreditProperty().asObject());
        ridesDriverIdCol.setCellValueFactory(cellData -> cellData.getValue().ridesDriverIdProperty().asObject());
        ridesCarIdCol.setCellValueFactory(cellData -> cellData.getValue().ridesCarIdProperty().asObject());
        ridesDriverCol.setCellValueFactory(cellData -> cellData.getValue().ridesDriverProperty());
        ridesCarCol.setCellValueFactory(cellData -> cellData.getValue().ridesCarProperty());
        ridesTotalCol.setCellValueFactory(cellData -> cellData.getValue().ridesTotalProperty().asObject());
        ridesPassengerCol.setCellValueFactory(cellData -> cellData.getValue().ridesPassengerProperty());
        ridesNotesCol.setCellValueFactory(cellData -> cellData.getValue().ridesNotesProperty());
    }

    private void initComboBoxes() throws SQLException {
        clientsChoiceBox.setItems(ClientDAO.getClientsNamesList());
        clientsChoiceBox.setValue("None");
        clientsChoiceBoxMonth.setItems(MONTHS);
        clientsChoiceBoxMonth.setValue("Any");

        for (Integer year :
                yearsList) {
            YEARS.add(year.toString());
        }
        clientsChoiceBoxYear.setItems(YEARS);
        clientsChoiceBoxYear.setValue("Any");
        selectedYear = "Any";

        clientsChoiceBoxMonth.setOnAction(event -> {
            selectedMonthIndex = clientsChoiceBoxMonth.getSelectionModel().getSelectedIndex();
            searchAllRides();
            });

        clientsChoiceBox.setOnAction((event) -> {
            selectedClient = clientsChoiceBox.getSelectionModel().getSelectedItem();
            searchAllRides();
        });

        clientsChoiceBoxYear.setOnAction((event) -> {
            selectedYear = clientsChoiceBoxYear.getSelectionModel().getSelectedItem();
            searchAllRides();
        });


    }

    @FXML
    public void searchAllRides() {
        if (selectedClient == null) {return;}
        ObservableList<Ride> rideData;
            try {
                Client client = ClientDAO.searchClientByName(selectedClient.toString());
                this.client = client;
                rideData = RideDAO.searchRidesByClientId(client.getClient_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            populateRides(filterByRange(rideData));
            allRides = filterByRange(rideData);
        }

    private ObservableList<Ride> filterByRange(ObservableList<Ride> list) {
        if(selectedMonthIndex == 0 && selectedYear == "Any") {
            return list;
        }
        //TODO: null exception
        ObservableList<Ride> res = FXCollections.observableArrayList();
        for (Ride ride : list) {

            if((selectedMonthIndex == 0 ||
                ride.getDate().getMonth().getValue() == selectedMonthIndex) &&

                    (selectedYear == "Any" ||
                    ride.getDate().getYear() == Integer.parseInt(selectedYear.toString()))) {

                res.add(ride);
            }
        }


        return res;
    }

    public void generateInvoice() {
        Invoice invoice = new Invoice(client, allRides, new Date());
        TaxiApplication.showNewInvoice(invoice);
    }
}
