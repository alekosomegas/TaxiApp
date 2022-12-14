package com.ak.taxiapp.model.invoice;

import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.client.ClientDAO;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class InvoiceDAO {

    public enum DBFields {
        INV_NO, DATE, CLIENT_ID, NOTES, STATUS
    }

    public static HashMap<DBFields, String> dbColumns = new HashMap<>() {
        {
            put(DBFields.INV_NO, "INVOICES_ID");
            put(DBFields.DATE, "INVOICES_DATE");
            put(DBFields.CLIENT_ID, "INVOICES_CLIENTS_ID");
            put(DBFields.NOTES, "INVOICES_NOTES");
            put(DBFields.STATUS, "INVOICES_STATUS");
        }
    };

    private static ObservableList<Invoice> getList(ResultSet rs) throws SQLException {
        ObservableList<Invoice> ls = FXCollections.observableArrayList();
        while (rs.next()) {
            Invoice invoice = null;
            try {
                // get the client fot the invoice
                Client client = ClientDAO.searchClientById(String.valueOf(rs.getInt("INVOICES_CLIENTS_ID")));
                invoice = new Invoice(client);
                invoice.setId(rs.getString("INVOICES_ID"));
                invoice.setDateProperty(rs.getString("INVOICES_DATE"));
                invoice.setClientsId(rs.getString("INVOICES_CLIENTS_ID"));
                invoice.setNotes(rs.getString("INVOICES_NOTES"));
                invoice.setStatusProperty(rs.getString("INVOICES_STATUS"));

                invoice.setInvoiceData();

            } catch (Exception e) {
                System.out.println("ERROR - reading invoice database");
                e.printStackTrace();
            }

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

    public static ObservableList<Invoice> searchById(String invoiceId) throws SQLException {
        String selectStatement = "SELECT * FROM invoices WHERE INVOICES_ID='" +invoiceId+ "';";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            return getList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    public static ObservableList<Invoice> searchByClientId(String clientId) throws SQLException {
        String selectStatement = "SELECT * FROM invoices WHERE INVOICES_CLIENTS_ID='" +clientId+ "';";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            return getList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    public static ObservableList<Invoice> searchByClientIdAndStatus(String clientId, Invoice.Status status) throws SQLException {
        String selectStatement = "SELECT * FROM invoices WHERE INVOICES_CLIENTS_ID='" +clientId+ "'" +
                "AND INVOICES_STATUS='" +status + "';";
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

    public static void insert(Invoice invoice) {
        String values = "'" +invoice.getId()+ "', '"
                            +invoice.getClient().getClient_id()+ "', '"
                            +invoice.getNotes()+ "', '"
                            +String.valueOf(invoice.getStatus())+ "'";

        String updateStatement =
                "INSERT INTO invoices (INVOICES_ID,  INVOICES_CLIENTS_ID, INVOICES_NOTES, INVOICES_STATUS)" +
                        " VALUES (" +values+ ");";

        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(HashMap<DBFields, String> values) {
        String updateStatement =
                "UPDATE invoices SET " +
                dbColumns.get(DBFields.DATE) + "='" +values.get(DBFields.DATE) +
                "'," + dbColumns.get(DBFields.NOTES) + "='" +values.get(DBFields.NOTES) +
                "', " + dbColumns.get(DBFields.STATUS) + "='" +values.get(DBFields.STATUS) +
                "' WHERE " + dbColumns.get(DBFields.INV_NO) + "='" + values.get(DBFields.INV_NO) + "';";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateStatus(String id, Invoice.Status status) {
        String updateStatement =
                "UPDATE invoices SET " +
                        dbColumns.get(DBFields.STATUS) + "='" +status +
                        "' WHERE " + dbColumns.get(DBFields.INV_NO) + "='" + id + "';";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
