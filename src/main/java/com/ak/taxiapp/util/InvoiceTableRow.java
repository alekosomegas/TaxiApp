package com.ak.taxiapp.util;

import com.ak.taxiapp.model.Ride;

import java.util.ArrayList;

public class InvoiceTableRow {

    private String date;
    private String passenger;
    private String from;
    private String stops;
    private String to;
    private String price;
    private String notes;

    private ArrayList<String> allData = new ArrayList<>();

    public InvoiceTableRow(Ride ride) {
        this.date = ride.getRidesDate();
        this.passenger = ride.getRidesPassenger();
        this.from = ride.getRidesFrom();
        this.stops = ride.getRidesStops();
        this.to = ride.getRidesTo();
        this.price = String.valueOf(ride.getRidesCredit());
        this.notes = ride.getRidesNotes();

        allData.add(date);
        allData.add(passenger);
        allData.add(from);
        allData.add(stops);
        allData.add(to);
        allData.add(price);
        allData.add(notes);
    }


    public ArrayList<String> getAllData() {
        return allData;
    }
}
