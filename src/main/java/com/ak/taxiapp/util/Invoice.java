package com.ak.taxiapp.util;

import com.ak.taxiapp.model.Client;
import com.ak.taxiapp.model.Ride;
import javafx.collections.ObservableList;

public class Invoice {
    private Client client;
    private InvoiceTable invoiceTable;

    public Invoice(Client client, ObservableList<Ride> allRides) {
        this.client = client;
        this.invoiceTable = new InvoiceTable(allRides);

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
}
