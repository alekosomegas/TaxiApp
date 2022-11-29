package com.ak.taxiapp.controller.ride;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;

//endregion
// ------------------------------------------------------------------ //

public class RidesViewController extends Controller {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    @FXML
     TableView<Ride> ridesTable;
    @FXML
    TableColumn<Ride, Integer> ridesIdCol;
    @FXML
    TableColumn<Ride, String> ridesDateCol;
    @FXML
     TableColumn<Ride, String> ridesStartCol;
    @FXML
     TableColumn<Ride, String> ridesEndCol;
    @FXML
    TableColumn<Ride, Integer> ridesClientIdCol;
    @FXML
     TableColumn<Ride, String> ridesClientCol;
    @FXML
    TableColumn<Ride, String> ridesFromCol;
    @FXML
    TableColumn<Ride, String> ridesToCol;
    @FXML
    TableColumn<Ride, String> ridesStopsCol;
    @FXML
    TableColumn<Ride, Integer> ridesCashCol;
    @FXML TableColumn<Ride, Integer> ridesCreditCol;
    @FXML TableColumn<Ride, Integer> ridesDriverIdCol;
    @FXML TableColumn<Ride, Integer> ridesCarIdCol;
    @FXML
    TableColumn<Ride, String> ridesDriverCol;
    @FXML
    TableColumn<Ride, String> ridesCarCol;
    @FXML
    TableColumn<Ride, Integer> ridesTotalCol;
    @FXML
    TableColumn<Ride, String> ridesPassengerCol;
    @FXML
    TableColumn<Ride, String> ridesNotesCol;

    private Ride selectedRide;

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ---------------------------- INITIALIZE --------------------------- //

    @FXML
    private void initialize() throws SQLException {
        /*
         * Sets the FXML Values to the values taken from equivalent variable of the ride object
         * for each column in the table in the rides view.
         */
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

        // Populates the table with all the rides
        searchAllRides();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- FXML METHODS -------------------------- //

    /**
     * Triggers the new ride dialogue
     */
    @FXML
    private void onNewRideClicked() {
        TaxiApplication.showNewRideDialog();
    }

    // ------------------------------------------------------------------ //
    /**
     * Triggers the new ride dialogue
     */
    @FXML public void onEditClicked() {
        HashMap<String, String> values = builtValues();
        TaxiApplication.showEditRideDialog(values);
    }

    // ------------------------------------------------------------------ //
    @FXML
    public void selectRide() {
        selectedRide = ridesTable.getSelectionModel().getSelectedItem();
    }

    // ------------------------------------------------------------------ //
    @FXML
    public void deleteSelectedRide() {
        if(selectedRide != null) {
            Integer id = selectedRide.getRides_id();
            try {
                RideDAO.deleteRideWithId(id);
                rlc.setResultText("Ride deleted! Ride id: " + id + "\n");
                searchAllRides();
            } catch (SQLException e) {
                rlc.setResultText("Problem occurred while deleting ride " + e);
            }
        }
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    /**
     * Creates a Hashmap of the values in the fields of the new ride dialogue
     * @return Hashmap of the values in the fields of the new ride dialogue
     */
    @NotNull
    private HashMap<String, String> builtValues() {
        HashMap<String, String> values = new HashMap<>();
        if(selectedRide != null) {
            ObservableList<TableColumn<Ride, ?>> columns = ridesTable.getColumns();
            for (TableColumn<Ride, ?> column : columns) {
                if (column.getCellData(selectedRide) != null) {
                    values.put(column.getId(), column.getCellData(selectedRide).toString());
                }
            }
        }
        return values;
    }

    // ------------------------------------------------------------------ //
    /**
     * Searches the database for all the ride and calls the populateRides
     * method which sets all the items in the rides' table in the rides view
     */
    @FXML
    public void searchAllRides() throws SQLException {
        try {
            // data of al the rides in the database
            ObservableList<Ride> rideData = RideDAO.searchAllRides();
            // populate the columns of the rides table
            populateRides(rideData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    // ------------------------------------------------------------------ //

    /**
     * Sets all the rides from the list of the rides as items in the table of the rides view
     */
    @FXML
    void populateRides(ObservableList<Ride> rdData) {
        ridesTable.setItems(rdData);
    }

    //endregion
    // ------------------------------------------------------------------ //

}

