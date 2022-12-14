package com.ak.taxiapp.ss;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.ride.Ride;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    private IntegerProperty fkRideId = new SimpleIntegerProperty();
    private Ride ride;

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
        this.fkRideId.setValue(ride.getRides_id());

        allData.add(getDate());
        allData.add(getPassenger());
        allData.add(getFrom());
        allData.add(getStops());
        allData.add(getTo());
        allData.add(getNotes());
        allData.add(String.valueOf(getPrice()));
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

    public Ride getRide() {
        return ride;
    }

    public int getFkRideId() {
        return fkRideId.get();
    }

    public IntegerProperty fkRideIdProperty() {
        return fkRideId;
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setPassenger(String passenger) {
        this.passenger.set(passenger);
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public void setStops(String stops) {
        this.stops.set(stops);
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public void setFkRideId(int fkRideId) {
        this.fkRideId.set(fkRideId);
    }
}
