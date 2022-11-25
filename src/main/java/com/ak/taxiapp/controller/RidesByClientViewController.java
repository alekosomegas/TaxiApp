package com.ak.taxiapp.controller;

import com.ak.taxiapp.model.Client;
import com.ak.taxiapp.model.ClientDAO;
import com.ak.taxiapp.model.Ride;
import com.ak.taxiapp.model.RideDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.sql.SQLException;

public class RidesByClientViewController extends RidesViewController{

    public ChoiceBox clientsChoiceBox;

    @FXML @Override
    public void searchAllRides() throws SQLException {
        try {
            clientsChoiceBox.setItems(ClientDAO.getClientsNamesList());

            clientsChoiceBox.setOnAction((event) -> {
                int selectedIndex = clientsChoiceBox.getSelectionModel().getSelectedIndex();
                Object selectedItem = clientsChoiceBox.getSelectionModel().getSelectedItem();

                System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
                System.out.println("   ChoiceBox.getValue(): " + clientsChoiceBox.getValue());

                ObservableList<Ride> rideData;
                try {
                    Client client = ClientDAO.searchClientByName(selectedItem.toString());
                    rideData = RideDAO.searchRidesByClientId(client.getClient_id());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                populateRides(rideData);
            });
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }
}
