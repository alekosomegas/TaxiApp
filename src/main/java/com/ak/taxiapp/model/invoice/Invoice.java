package com.ak.taxiapp.model.invoice;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.ride.Ride;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

// endregion
// ------------------------------------------------------------------ //

public class Invoice {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private Client client;
    private InvoiceTable invoiceTable;
    private Date date;


    //endregion
    // ------------------------------------------------------------------ //

    public Invoice(Client client, ObservableList<Ride> allRides, Date date) {
        this.client = client;
        this.invoiceTable = new InvoiceTable(allRides);
        this.date = date;

    }

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //

    public InvoiceTable getInvoiceTable() {
        return invoiceTable;
    }

    public String getClientName() {
        return client.getClient_name();
    }

    public String getClientAddress() {
        return client.getClient_address();
    }

    public String getClientEmail() {
        return client.getClient_email();
    }

    public String getClientTel() {
        return client.getClient_tel();
    }

    public String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

    public String getYearString() {
        return String.valueOf(date.getYear() + 1900);
    }

    public String getMonthString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
        return formatter.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //endregion
    // ------------------------------------------------------------------ //
}
