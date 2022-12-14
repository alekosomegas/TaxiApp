package com.ak.taxiapp.model.invoice;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.Formatter;
import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.ss.InvoiceTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

// endregion
// ------------------------------------------------------------------ //

public class Invoice {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    // As displayed in tableView in Database layout
    private StringProperty id;
    private StringProperty clientName;
    private StringProperty dateRange;
    private StringProperty dateProperty;
    private StringProperty notes;
    private IntegerProperty total;
    private StringProperty statusProperty;

    private String clientsId;

    // Invoice info
    private Client client;
    private Date date;
    private LocalDate startDate;
    private LocalDate endDate;
    private ObservableList<Ride> rides;



    /**
     * OPEN      - created and running account, can be altered, no date of issue
     * When a new ride that has a non-zero amount of credit AND
     * a named client from the clients table, is inserted into the database,
     * it will be either :
     * A -  Inserted into an invoice, if an OPEN invoice with the same Client exist
     * B -  Inserted into a NEW invoice
     *
     * CLOSED    - no alteration allowed, date fixed, pdf created
     * The invoice can become CLOSED in 3 ways:
     * 1.   By clicking the close button in the single invoice view
     * 2.   On the date of the optional pre-defined closing date
     * 3.   By printing a pdf and confirm the closing
     *
     * ISSUED   - invoice is marked as sent to client
     * The status changes to  ISSUED in two ways
     * 1.   Manually
     * 2.   Automatically by clicking the send email (in the feature)
     *
     * PAID     - balanced paid in full
     */
    public enum Status {
        // VOID? - to create an invoice manually
        VOID, OPEN, CLOSED, ISSUED, PAID
    }

    private HashMap<Status, Color> statusColors = new HashMap<>() {
        {
            put(Status.VOID, Color.WHITESMOKE);
            // blue
            put(Status.OPEN, Color.rgb(47,97,190));
            // violer
            put(Status.CLOSED, Color.rgb(154,47,190));
            // orange
            put(Status.ISSUED, Color.rgb(190,140,47));
            // green
            put(Status.PAID, Color.rgb(83,190,47));
        }
    };
    private Status status;

    // TODO: delete
    private InvoiceTable invoiceTable;
    //endregion
    // ------------------------------------------------------------------ //

    public Invoice(Client client) {
        // db properties
        // wil be set from InvoiceDao
        this.id = new SimpleStringProperty();
        this.dateProperty = new SimpleStringProperty();
        this.clientsId = "";
        this.notes = new SimpleStringProperty();
        this.statusProperty = new SimpleStringProperty();

        // derived properties
        this.clientName = new SimpleStringProperty();
        this.dateRange = new SimpleStringProperty();
        this.total = new SimpleIntegerProperty();

        // invoice properties
        this.client = client;

    }

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //

    public void setInvoiceData() {
        setClientName(String.format("%02d", Integer.parseInt(clientsId))
                + ". " + client.getClient_name());
        this.rides = findRides();
        setDate(findDate());
        setStatus(findStatus());
        setTotal(findTotal());
        setDateRange(findRange());
    }

    private Status findStatus() {
        return Invoice.Status.valueOf(getStatusProperty());
    }

    private Date findDate() {
        if (getDateProperty() != null && !getDateProperty().equals("null")) {
            return (CalendarModel.convertDate(LocalDate.parse(getDateProperty())));
        }
        return null;
    }

    private String findRange() {
        if(rides.isEmpty()) {
            return "";
        }
        LocalDate oldest = rides.get(0).getDate();
        LocalDate newest = oldest;
        for (Ride ride : rides) {
            if (ride.getDate().isBefore(oldest)) {
                oldest = ride.getDate();
            }
            if (ride.getDate().isAfter(newest)) {
                newest = ride.getDate();
            }
        }
        startDate = oldest;
        endDate = newest;
        return oldest.format(Formatter.DATE_DISPLAY) +" - "+ newest.format(Formatter.DATE_DISPLAY);
    }

    // Total Property
    public int getTotal() {
        return total.get();
    }

    private Integer findTotal() {
        int total = 0;
        for (Ride ride : rides) {
            total += ride.getRidesTotal();
        }
        return total;
    }
    public IntegerProperty totalProperty() {
        return total;
    }
    public void setTotal(int total) {
        this.total.set(total);
    }


    public void updateStatus() {
        InvoiceDAO.updateStatus(getId(), status);
    }
    // Rides

    /**
     * Searches the database for all rides that have the invoice id
     * @return List of the rides in the invoice
     */
    private ObservableList<Ride> findRides() {
        try {
            return RideDAO.searchRidesByInvoiceId(getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Ride> getRides() {
        return rides;
    }

    // Id
    public String getId() {
        return id.get();
    }
    public StringProperty idProperty() {
        return id;
    }
    public void setId(String id) {
        this.id.set(id);
    }

    // Client name
    public StringProperty clientNameProperty() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }


    // Date
    public String getDateProperty() {
        return dateProperty.get();
    }
    public StringProperty datePropertyProperty() {
        return dateProperty;
    }
    public void setDateProperty(String dateProperty) {
        this.dateProperty.set(dateProperty);
    }


    // Notes
    public String getNotes() {
        return notes.get();
    }
    public StringProperty notesProperty() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    // TODO: delete
    public InvoiceTable getInvoiceTable() {
        return invoiceTable;
    }

    // Client
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
    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public String getClientsId() {
        return clientsId;
    }

    public void setClientsId(String clientsId) {
        this.clientsId = clientsId;
    }

    // Date
    public String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    public String getDatePrint() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
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

    public String getDateRange() {
        return dateRange.get();
    }

    public StringProperty dateRangeProperty() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange.set(dateRange);
    }

    public Date getDate() {
        return date;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    // Status

    public Status getStatus() {
        return status;
    }

    public String getStatusProperty() {
        return statusProperty.get();
    }

    public StringProperty statusPropertyProperty() {
        return statusProperty;
    }

    public void setStatusProperty(String statusProperty) {
        this.statusProperty.set(statusProperty);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public HashMap<Status, Color> getStatusColors() {
        return statusColors;
    }

    //endregion
    // ------------------------------------------------------------------ //
}
