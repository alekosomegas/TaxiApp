package com.ak.taxiapp.model.invoice;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.ride.Ride;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

// endregion
// ------------------------------------------------------------------ //

public class InvoiceTable {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private final ArrayList<String> HEADERS = new ArrayList<>(Arrays.asList(
            "Date", "Passenger", "From", "Stops", "To", "Price", "Notes"));

//    private int numOfRows;
    private ObservableList<InvoiceTableRow> allRows = FXCollections.observableArrayList();

    public ArrayList<String> getHEADERS() {
        return HEADERS;
    }

    public ObservableList<InvoiceTableRow> getAllRows() {
        return allRows;
    }

    private LocalDate oldestDate;
    private LocalDate newestDate;
    private Integer total;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy");
    //endregion
    // ------------------------------------------------------------------ //

    public InvoiceTable(ObservableList<Ride> allRides) {
        for (Ride ride : allRides) {
            allRows.add(new InvoiceTableRow(ride));
        }
        setOldestRide();
        setNewestRide();
        setTotal();
    }

    public int getNumOfColumns() {
        return HEADERS.size();
    }

    public String getOldestDate() {
        return formatter.format(CalendarModel.convertDate(oldestDate));
    }

    public String getNewestDate() {
        return formatter.format(CalendarModel.convertDate(newestDate));
    }

    //TODO: COMBINE ? call allRows only once
    private void setOldestRide() {
        LocalDate oldest = allRows.get(0).getLocalDate();
        for (InvoiceTableRow row : allRows) {
            if (row.getLocalDate().isBefore(oldest)) {
                oldest = row.getLocalDate();
            }
        }
        this.oldestDate = oldest;
    }
    private void setNewestRide() {
        LocalDate newest = allRows.get(0).getLocalDate();
        for (InvoiceTableRow row : allRows) {
            if (row.getLocalDate().isAfter(newest)) {
                newest = row.getLocalDate();
            }
        }
        this.newestDate = newest;
    }

    private void setTotal() {
        Integer total = 0;
        for (InvoiceTableRow row : allRows) {
            total += row.getPrice();
        }
        this.total = total;
    }

    public int getTotal() {
        return total;
    }



}
