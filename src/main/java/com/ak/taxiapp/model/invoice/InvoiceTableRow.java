package com.ak.taxiapp.model.invoice;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.ride.Ride;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

// endregion
// ------------------------------------------------------------------ //

public class InvoiceTableRow {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private StringProperty date;
    private StringProperty passenger;
    private StringProperty from;
    private StringProperty stops;
    private StringProperty to;
    private IntegerProperty price;
    private StringProperty notes;

    //endregion
    // ------------------------------------------------------------------ //

    private ArrayList<String> allData = new ArrayList<>();

    public InvoiceTableRow(Ride ride) {
        this.date = ride.ridesDateProperty();
        this.passenger = ride.ridesPassengerProperty();
        this.from = ride.ridesFromProperty();
        this.stops = ride.ridesStopsProperty();
        this.to = ride.ridesToProperty();
        this.price = ride.ridesTotalProperty();
        this.notes = ride.ridesNotesProperty();

        allData.add(String.valueOf(date));
        allData.add(String.valueOf(passenger));
        allData.add(String.valueOf(from));
        allData.add(String.valueOf(stops));
        allData.add(String.valueOf(to));
        allData.add(String.valueOf(price));
        allData.add(String.valueOf(notes));
    }


    public ArrayList<String> getAllData() {
        return allData;
    }

    public String getDate() {
        return date.get();
    }
    public LocalDate getLocalDate() {
        return LocalDate.parse(date.get());
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getPassenger() {
        return passenger.get();
    }

    public StringProperty passengerProperty() {
        return passenger;
    }

    public String getFrom() {
        return from.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public String getStops() {
        return stops.get();
    }

    public StringProperty stopsProperty() {
        return stops;
    }

    public String getTo() {
        return to.get();
    }

    public StringProperty toProperty() {
        return to;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }
}
