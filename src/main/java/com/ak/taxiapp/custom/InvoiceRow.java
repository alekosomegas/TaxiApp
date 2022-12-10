package com.ak.taxiapp.custom;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.controller.invoice.InvoiceCard;
import com.ak.taxiapp.controller.invoice.InvoiceCardController;
import com.ak.taxiapp.controller.invoice.InvoiceRowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.HashMap;

public class InvoiceRow extends HBox {
    private Node view;
    private InvoiceRowController controller;
    private final String FXMLPATH = "fxml/invoice/InvoiceRow.fxml";


    public InvoiceRow() {
        super();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TaxiApplication.class.getResource(FXMLPATH));

        controller = new InvoiceRowController();
        loader.setController(controller);

        try {
            view = (Node) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getChildren().add(view);
    }


    public enum InvoiceRowFields {
        STATUS, ID, CLIENT, FROM, TO, DATE, TOTAL, NOTES
    }

    public void setValues(HashMap<InvoiceRowFields, String> values) {
        controller.setLblId(values.get(InvoiceRowFields.ID));
        controller.setLblClient(values.get(InvoiceRowFields.CLIENT));
        controller.setLblDate(values.get(InvoiceRowFields.DATE));
        controller.setLblTotal(values.get(InvoiceRowFields.TOTAL));

    }

}
