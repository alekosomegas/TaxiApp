package com.ak.taxiapp.util;


import com.ak.taxiapp.model.Ride;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

public class InvoiceTable {

    private final ArrayList<String> HEADERS = new ArrayList<>(Arrays.asList(
            "Date", "Passenger", "From", "Stops", "To", "Price", "Notes"));

//    private int numOfRows;
    private ArrayList<InvoiceTableRow> allRows = new ArrayList<>();

    public InvoiceTable(ObservableList<Ride> allRides) {
        for (Ride ride : allRides) {
            allRows.add(new InvoiceTableRow(ride));
        }
    }

    public ArrayList<String> getHEADERS() {
        return HEADERS;
    }

    public ArrayList<InvoiceTableRow> getAllRows() {
        return allRows;
    }

    public int getNumOfColumns() {
        return HEADERS.size();
    }
}
