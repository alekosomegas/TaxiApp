package com.ak.taxiapp.ss;

import com.ak.taxiapp.TaxiApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.HashMap;

public class InvoiceCard extends HBox {
    private Node view;
    private InvoiceCardController controller;
    private final String FXMLPATH = "fxml/invoice/InvoiceCard.fxml";

    public InvoiceCard() {
        super();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TaxiApplication.class.getResource(FXMLPATH));

        controller = new InvoiceCardController();
        loader.setController(controller);

        try {
            view = (Node) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getChildren().add(view);
    }

    public enum InvoiceCardFields {
        STATUS, ID, CLIENT, FROM, TO, DATE, TOTAL, NOTES
    }
    public void setValues(HashMap<InvoiceCardFields, String> values) {
        controller.setLblId(values.get(InvoiceCardFields.ID));
        controller.setLblClient(values.get(InvoiceCardFields.CLIENT));
        controller.setLblDate(values.get(InvoiceCardFields.DATE));
        controller.setLblTotal(values.get(InvoiceCardFields.TOTAL));

    }
}
