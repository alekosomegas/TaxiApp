package com.ak.taxiapp.model;

import javafx.beans.property.*;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ride extends Table {
    private IntegerProperty rides_id;
    private StringProperty ridesDate;
    private StringProperty ridesTimeStart;
    private StringProperty ridesTimeEnd;
    private StringProperty ridesDuration;
    private IntegerProperty ridesClientId;
    private StringProperty ridesClient;
    private IntegerProperty ridesDriverId;
    private StringProperty ridesDriver;
    private IntegerProperty ridesCarId;
    private StringProperty ridesCar;
    private StringProperty ridesFrom;
    private StringProperty ridesStops;
    private StringProperty ridesTo;
    private IntegerProperty ridesCash;
    private IntegerProperty ridesStatus;
    private IntegerProperty ridesCredit;
    private IntegerProperty ridesPaid;
    private IntegerProperty ridesTotal;

    private Driver driver;
    private LocalDate date;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration duration;


    public Ride() throws SQLException {
        this.rides_id = new SimpleIntegerProperty();
        this.ridesDate = new SimpleStringProperty();
        this.ridesTimeStart = new SimpleStringProperty();
        this.ridesTimeEnd = new SimpleStringProperty();
        this.ridesDuration = new SimpleStringProperty();
        this.ridesClientId = new SimpleIntegerProperty();
        this.ridesClient = new SimpleStringProperty();
        this.ridesDriverId = new SimpleIntegerProperty();
        this.ridesDriver = new SimpleStringProperty();
        this.ridesCarId = new SimpleIntegerProperty();
        this.ridesCar = new SimpleStringProperty();
        this.ridesFrom = new SimpleStringProperty();
        this.ridesTo = new SimpleStringProperty();
        this.ridesStops = new SimpleStringProperty();
        this.ridesCash = new SimpleIntegerProperty();
        this.ridesStatus = new SimpleIntegerProperty();
        this.ridesCredit = new SimpleIntegerProperty();
        this.ridesPaid = new SimpleIntegerProperty();
        this.ridesTotal = new SimpleIntegerProperty();

    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRides_id() {
        return rides_id.get();
    }

    public IntegerProperty rides_idProperty() {
        return rides_id;
    }

    public void setRides_id(int rides_id) {
        this.rides_id.set(rides_id);
    }

    public String getRidesDate() {
        return ridesDate.get();
    }

    public StringProperty ridesDateProperty() {
        return ridesDate;
    }

    public void setRidesDate(String ridesDate) {
        this.ridesDate.set(ridesDate);
    }

    public String getRidesTimeStart() {
        return ridesTimeStart.get();
    }

    public StringProperty ridesTimeStartProperty() {
        return ridesTimeStart;
    }

    public void setRidesTimeStart(String ridesTimeStart) {
        this.ridesTimeStart.set(ridesTimeStart);
    }

    public String getRidesTimeEnd() {
        return ridesTimeEnd.get();
    }

    public StringProperty ridesTimeEndProperty() {
        return ridesTimeEnd;
    }

    public void setRidesTimeEnd(String ridesTimeEnd) {
        this.ridesTimeEnd.set(ridesTimeEnd);
    }

    public String getRidesDuration() {
        return ridesDuration.get();
    }

    public StringProperty ridesDurationProperty() {
        return ridesDuration;
    }

    public void setRidesDuration(String ridesDuration) {
        this.ridesDuration.set(ridesDuration);
    }

    public int getRidesClientId() {
        return ridesClientId.get();
    }

    public IntegerProperty ridesClientIdProperty() {
        return ridesClientId;
    }

    public void setRidesClientId(int ridesClientId) {
        this.ridesClientId.set(ridesClientId);
    }

    public String getRidesClient() {
        return ridesClient.get();
    }

    public StringProperty ridesClientProperty() {
        return ridesClient;
    }

    public void setRidesClient(String ridesClient) {
        this.ridesClient.set(ridesClient);
    }

    public int getRidesDriverId() {
        return ridesDriverId.get();
    }

    public IntegerProperty ridesDriverIdProperty() {
        return ridesDriverId;
    }

    public void setRidesDriverId(int ridesDriverId) {
        this.ridesDriverId.set(ridesDriverId);
    }

    public String getRidesDriver() {
        return ridesDriver.get();
    }

    public StringProperty ridesDriverProperty() {
        return ridesDriver;
    }

    public void setRidesDriver(String ridesDriver) {
        this.ridesDriver.set(ridesDriver);
    }

    public int getRidesCarId() {
        return ridesCarId.get();
    }

    public IntegerProperty ridesCarIdProperty() {
        return ridesCarId;
    }

    public void setRidesCarId(int ridesCarId) {
        this.ridesCarId.set(ridesCarId);
    }

    public String getRidesCar() {
        return ridesCar.get();
    }

    public StringProperty ridesCarProperty() {
        return ridesCar;
    }

    public void setRidesCar(String ridesCar) {
        this.ridesCar.set(ridesCar);
    }

    public String getRidesFrom() {
        return ridesFrom.get();
    }

    public StringProperty ridesFromProperty() {
        return ridesFrom;
    }

    public void setRidesFrom(String ridesFrom) {
        this.ridesFrom.set(ridesFrom);
    }

    public String getRidesStops() {
        return ridesStops.get();
    }

    public StringProperty ridesStopsProperty() {
        return ridesStops;
    }

    public void setRidesStops(String ridesStops) {
        this.ridesStops.set(ridesStops);
    }

    public String getRidesTo() {
        return ridesTo.get();
    }

    public StringProperty ridesToProperty() {
        return ridesTo;
    }

    public void setRidesTo(String ridesTo) {
        this.ridesTo.set(ridesTo);
    }

    public int getRidesCash() {
        return ridesCash.get();
    }

    public IntegerProperty ridesCashProperty() {
        return ridesCash;
    }

    public void setRidesCash(int ridesCash) {
        this.ridesCash.set(ridesCash);
    }

    public int getRidesStatus() {
        return ridesStatus.get();
    }

    public IntegerProperty ridesStatusProperty() {
        return ridesStatus;
    }

    public void setRidesStatus(int ridesStatus) {
        this.ridesStatus.set(ridesStatus);
    }

    public int getRidesCredit() {
        return ridesCredit.get();
    }

    public IntegerProperty ridesCreditProperty() {
        return ridesCredit;
    }

    public void setRidesCredit(int ridesCredit) {
        this.ridesCredit.set(ridesCredit);
    }

    public int getRidesPaid() {
        return ridesPaid.get();
    }

    public IntegerProperty ridesPaidProperty() {
        return ridesPaid;
    }

    public void setRidesPaid(int ridesPaid) {
        this.ridesPaid.set(ridesPaid);
    }

    public int getRidesTotal() {
        return ridesTotal.get();
    }

    public IntegerProperty ridesTotalProperty() {
        return ridesTotal;
    }

    public void setRidesTotal(int ridesTotal) {
        this.ridesTotal.set(ridesTotal);
    }

}







