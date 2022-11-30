package com.ak.taxiapp.model.invoice;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.ride.Ride;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.Date;

// endregion
// ------------------------------------------------------------------ //

public class Invoice {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private StringProperty id;
    private Client client;
    private StringProperty clientName;
    private InvoiceTable invoiceTable;
    private Date date;
    private StringProperty dateProperty;
    private StringProperty notes;
    private IntegerProperty total;


    //endregion
    // ------------------------------------------------------------------ //

    public Invoice(Client client, ObservableList<Ride> allRides, Date date) {
        this.id = new SimpleStringProperty();
        this.clientName = new SimpleStringProperty();
        this.dateProperty = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.total = new SimpleIntegerProperty();

        this.client = client;
        this.invoiceTable = new InvoiceTable(allRides);
        this.date = date;
        setId("0" + client.getClient_id() + "/" + "1");
        setClientName(getClientName());
        // TODO:create format in settings for all dates
        setDateProperty(date.toString());
        setTotal(invoiceTable.getTotal());
    }

    public Invoice() {
        this.id = new SimpleStringProperty();
        this.clientName = new SimpleStringProperty();
        this.dateProperty = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.total = new SimpleIntegerProperty();
    }

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //


    public int getTotal() {
        return total.get();
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    public String getDateProperty() {
        return dateProperty.get();
    }

    public StringProperty datePropertyProperty() {
        return dateProperty;
    }

    public void setDateProperty(String dateProperty) {
        this.dateProperty.set(dateProperty);
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

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
