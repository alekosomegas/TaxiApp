package com.ak.taxiapp.controller;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.Ride;
import com.ak.taxiapp.model.RideDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.HashMap;

public class RidesViewController extends Controller{

    public TableColumn<Ride, Integer> ridesTotalCol;
    @FXML
    private TableView<Ride> ridesTable;
    @FXML
    private TableColumn<Ride, Integer> ridesIdCol;
    public TableColumn<Ride, String> ridesDateCol;
    @FXML
    private TableColumn<Ride, String> ridesStartCol;
    @FXML
    private TableColumn<Ride, String> ridesEndCol;
    @FXML private TableColumn<Ride, String> ridesDurationCol;
    @FXML
    private TableColumn<Ride, Integer> ridesClientIdCol;
    @FXML
    private TableColumn<Ride, String> ridesClientCol;
    @FXML
    private TableColumn<Ride, String> ridesFromCol;
    @FXML
    private TableColumn<Ride, String> ridesToCol;
    @FXML
    private TableColumn<Ride, String> ridesStopsCol;
    @FXML
    private TableColumn<Ride, Integer> ridesCashCol;
    @FXML
    private TableColumn<Ride, Integer> ridesStatusCol;
    @FXML
    private TableColumn<Ride, Integer> ridesPaidCol;

    @FXML private TableColumn<Ride, Integer> ridesCreditCol;
    @FXML private TableColumn<Ride, Integer> ridesDriverIdCol;
    @FXML private TableColumn<Ride, Integer> ridesCarIdCol;
    @FXML
    private TableColumn<Ride, String> ridesDriverCol;
    @FXML
    private TableColumn<Ride, String> ridesCarCol;

    private Ride selectedRide;


    @FXML
    private void initialize() throws SQLException {
        ridesIdCol.setCellValueFactory(cellData -> cellData.getValue().rides_idProperty().asObject());
        ridesDateCol.setCellValueFactory(cellData -> cellData.getValue().ridesDateProperty());
        ridesStartCol.setCellValueFactory(cellData -> cellData.getValue().ridesTimeStartProperty());
        ridesEndCol.setCellValueFactory(cellData -> cellData.getValue().ridesTimeEndProperty());
        ridesDurationCol.setCellValueFactory(cellData -> cellData.getValue().ridesDurationProperty());
        ridesClientIdCol.setCellValueFactory(cellData -> cellData.getValue().ridesClientIdProperty().asObject());
        ridesClientCol.setCellValueFactory(cellData -> cellData.getValue().ridesClientProperty());
        ridesFromCol.setCellValueFactory(cellData -> cellData.getValue().ridesFromProperty());
        ridesToCol.setCellValueFactory(cellData -> cellData.getValue().ridesToProperty());
        ridesStopsCol.setCellValueFactory(cellData -> cellData.getValue().ridesStopsProperty());
        ridesCashCol.setCellValueFactory(cellData -> cellData.getValue().ridesCashProperty().asObject());
        ridesStatusCol.setCellValueFactory(cellData -> cellData.getValue().ridesStatusProperty().asObject());
        ridesPaidCol.setCellValueFactory(cellData -> cellData.getValue().ridesPaidProperty().asObject());
        ridesCreditCol.setCellValueFactory(cellData -> cellData.getValue().ridesCreditProperty().asObject());
        ridesDriverIdCol.setCellValueFactory(cellData -> cellData.getValue().ridesDriverIdProperty().asObject());
        ridesCarIdCol.setCellValueFactory(cellData -> cellData.getValue().ridesCarIdProperty().asObject());
        ridesDriverCol.setCellValueFactory(cellData -> cellData.getValue().ridesDriverProperty());
        ridesCarCol.setCellValueFactory(cellData -> cellData.getValue().ridesCarProperty());
        ridesTotalCol.setCellValueFactory(cellData -> cellData.getValue().ridesTotalProperty().asObject());
        searchRides();
    }

    @FXML
    public void searchRides() throws SQLException {
        try {
            ObservableList<Ride> rideData = RideDAO.searchRides();
            populateRides(rideData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    @FXML
    void populateRides(ObservableList<Ride> rdData) {
        ridesTable.setItems(rdData);
    }

    @FXML
    private void onNewRideClicked() {
        TaxiApplication.showNewRideDialog();
    }

    @FXML
    public void selectRide() {
        selectedRide = ridesTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void deleteSelectedRide() {
        if(selectedRide != null) {
            Integer id = selectedRide.getRides_id();
            try {
                RideDAO.deleteRideWithId(id);
                rlc.setResultText("Ride deleted! Ride id: " + id + "\n");
                searchRides();
            } catch (SQLException e) {
                rlc.setResultText("Problem occurred while deleting ride " + e);
            }
        }
    }

    @FXML public void onEditClicked() {
        HashMap<String, String> values = new HashMap<>();
        if(selectedRide != null) {
            int id = selectedRide.getRides_id();
            ObservableList<TableColumn<Ride, ?>> columns = ridesTable.getColumns();
            for (TableColumn<Ride, ?> column : columns) {
                if (column.getCellData(selectedRide) != null) {
                    values.put(column.getId(), column.getCellData(selectedRide).toString());
                }
            }
        }
        TaxiApplication.showEditRideDialog(values);
    }
}
