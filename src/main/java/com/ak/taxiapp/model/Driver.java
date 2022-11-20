package com.ak.taxiapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Driver extends Table {
    private IntegerProperty driver_id;
    private StringProperty driver_name;
    private IntegerProperty driver_car;
    private StringProperty driver_color;

    public String getDriver_color() {
        return driver_color.get();
    }

    public StringProperty driver_colorProperty() {
        return driver_color;
    }

    public void setDriver_color(String driver_color) {
        this.driver_color.set(driver_color);
    }




    public Driver() {
        this.driver_id = new SimpleIntegerProperty();
        this.driver_name = new SimpleStringProperty();
        this.driver_car = new SimpleIntegerProperty();
        this.driver_color= new SimpleStringProperty();
    }

    public int getDriver_id() {
        return driver_id.get();
    }

    public IntegerProperty driver_idProperty() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id.set(driver_id);
    }

    public String getDriver_name() {
        return driver_name.get();
    }

    public StringProperty driver_nameProperty() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name.set(driver_name);
    }

    public int getDriver_car() {
        return driver_car.get();
    }

    public IntegerProperty driver_carProperty() {
        return driver_car;
    }

    public void setDriver_car(int driver_car) {
        this.driver_car.set(driver_car);
    }
}
