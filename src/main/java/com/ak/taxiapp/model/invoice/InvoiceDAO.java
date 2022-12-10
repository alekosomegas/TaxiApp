package com.ak.taxiapp.model.invoice;

import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceDAO {

    private static ObservableList<Invoice> getList(ResultSet rs) throws SQLException {
        ObservableList<Invoice> ls = FXCollections.observableArrayList();
        while (rs.next()) {
            Ride ride;
            Invoice invoice = new Invoice();
            invoice.setId(rs.getString("INVOICES_ID"));

//            String listOfRides = rs.getString("INVOICES_RIDES_IDS");
//            ride = RideDAO.searchById(findRidesId(listOfRides)).get(0);
//            invoice.setClientName(ride.getRidesClient());

            invoice.setDateProperty(rs.getString("INVOICES_DATE"));

//            int total = InvoiceTable.findTotal(findAllRides(listOfRides));
//            invoice.setTotal(total);

            ls.add(invoice);
        }
        return ls;
    }

    public static ObservableList<Invoice> searchAll() throws SQLException {
        String selectStatement = "SELECT * FROM invoices";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            return getList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    private static String findRidesId(String listOfRides) {
        return listOfRides.split(" ")[0];
    }
    private static ObservableList<Ride> findAllRides(String listOfRides) throws SQLException {
        listOfRides = listOfRides.stripTrailing();
        String[] ridesList = listOfRides.split(" ");
        ObservableList<Ride> allRides = FXCollections.observableArrayList();

        for (String ride : ridesList) {
            allRides.add(RideDAO.searchById(ride).get(0));
        }

        return allRides;
    }

}
