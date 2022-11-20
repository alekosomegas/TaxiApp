package com.ak.taxiapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car extends Table {
    private IntegerProperty car_id;
    private StringProperty car_reg;
    private StringProperty car_make;
    private StringProperty car_model;
    private IntegerProperty car_mileage;

    public Car() {
        this.car_id = new SimpleIntegerProperty();
        this.car_reg = new SimpleStringProperty();
        this.car_make = new SimpleStringProperty();
        this.car_model = new SimpleStringProperty();
        this.car_mileage = new SimpleIntegerProperty();
    }

    public int getCar_id() {
        return car_id.get();
    }

    public IntegerProperty car_idProperty() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id.set(car_id);
    }

    public String getCar_reg() {
        return car_reg.get();
    }

    public StringProperty car_regProperty() {
        return car_reg;
    }

    public void setCar_reg(String car_reg) {
        this.car_reg.set(car_reg);
    }

    public String getCar_make() {
        return car_make.get();
    }

    public StringProperty car_makeProperty() {
        return car_make;
    }

    public void setCar_make(String car_make) {
        this.car_make.set(car_make);
    }

    public String getCar_model() {
        return car_model.get();
    }

    public StringProperty car_modelProperty() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model.set(car_model);
    }

    public int getCar_mileage() {
        return car_mileage.get();
    }

    public IntegerProperty car_mileageProperty() {
        return car_mileage;
    }

    public void setCar_mileage(int car_mileage) {
        this.car_mileage.set(car_mileage);
    }
}
