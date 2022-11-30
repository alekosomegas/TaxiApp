package com.ak.taxiapp.model.invoice;

import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class InvoiceTableRowDAO {

    private static ObservableList<InvoiceTableRow> getList(ResultSet resultSet, Ride ride) throws SQLException {
        ObservableList<InvoiceTableRow> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            InvoiceTableRow invoiceTableRow = new InvoiceTableRow(ride);
            setValuesFromRsData(resultSet, invoiceTableRow);
        }
        return list;
    }

    public static ObservableList<InvoiceTableRow> searchAll(Ride ride) throws SQLException {
        String selectStatement = "SELECT * FROM invoices";
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectStatement);
            return getList(resultSet, ride);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Gets all the invoices associated with a client that fall in the date range.
     * @param ride
     * @param clientId
     * @param startDate
     * @param endDate
     * @return
     */
    public static ObservableList<InvoiceTableRow> searchByClientAndDateRange(Ride ride, Integer clientId, LocalDate startDate, LocalDate endDate) {
        String selectStatement = "SELECT * FROM invoices " +
                "INNER JOIN rides   ON invoices.RIDES_ID=rides.RIDES_ID" +
                "INNER JOIN clients ON rides.RIDES_CLIENT_ID=" + clientId;
        ObservableList<InvoiceTableRow> list = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectStatement);
            while (resultSet.next()) {
                LocalDate ridesDate = LocalDate.parse(resultSet.getString("RIDES_DATE"));
                if(ridesDate.isAfter(startDate.minusDays(1)) && ridesDate.isBefore(endDate.plusDays(1))) {
                    list.add(new InvoiceTableRow(ride));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void insert(String invoiceId, String invoiceDate, String invoiceRideIds) {
        String values = "'" +invoiceId+ "', '"
                            +invoiceDate+ "', '"
                            +invoiceRideIds+ "'";

        String updateStatement =
                "INSERT INTO invoices (INVOICES_ID, INVOICES_DATE, INVOICES_RIDES_IDS)" +
                        " VALUES (" +values+ ");";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // TODO: change name of table column, ride id?
    public static void update(HashMap<String, String> values) {
        String updateStatement = "UPDATE invoice SET " +
                " INVOICE_DATE = " + "'" +values.get("invoiceDateCol")+ "'" +
                " INVOICE_RIDES_ID" + "'" +values.get("invoiceRideCol") + "';";

        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setValuesFromRsData(ResultSet resultSet, InvoiceTableRow invoiceTableRow) throws SQLException {
//        invoiceTableRow.setId(resultSet.getInt("INVOICE_ID"));
        invoiceTableRow.setDate(resultSet.getString("INVOICE_DATE"));
        invoiceTableRow.setFkRideId(resultSet.getInt("INVOICE_RIDES_IDS"));
    }

    }
