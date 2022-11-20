package com.ak.taxiapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client extends Table {
    private IntegerProperty client_id;
    private StringProperty client_name;
    private StringProperty client_address;
    private StringProperty client_email;
    private StringProperty client_tel;

    public Client() {
        this.client_id       = new SimpleIntegerProperty();
        this.client_name     = new SimpleStringProperty();
        this.client_address  = new SimpleStringProperty();
        this.client_email    = new SimpleStringProperty();
        this.client_tel      = new SimpleStringProperty();
    }

    public int getClient_id() {
        return client_id.get();
    }

    public IntegerProperty client_idProperty() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id.set(client_id);
    }

    public String getClient_name() {
        return client_name.get();
    }

    public StringProperty client_nameProperty() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name.set(client_name);
    }

    public String getClient_address() {
        return client_address.get();
    }

    public StringProperty client_addressProperty() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address.set(client_address);
    }

    public String getClient_email() {
        return client_email.get();
    }

    public StringProperty client_emailProperty() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email.set(client_email);
    }

    public String getClient_tel() {
        return client_tel.get();
    }

    public StringProperty client_telProperty() {
        return client_tel;
    }

    public void setClient_tel(String client_tel) {
        this.client_tel.set(client_tel);
    }
}
