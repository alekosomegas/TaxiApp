package com.ak.taxiapp.model.invoice;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.controller.invoice.InvoiceRowController;
import com.ak.taxiapp.model.Formatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.HashMap;

public class InvoiceRow extends HBox {
    private Node view;
    private InvoiceRowController controller;
    private final String FXMLPATH = "fxml/invoice/InvoiceRow.fxml";
    private Invoice invoice;


    public InvoiceRow(Invoice invoice) {
        super();

        this.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                controller.highlight();
            } else {
                controller.unhighlight();
            }
        });

        this.invoice = invoice;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TaxiApplication.class.getResource(FXMLPATH));

        controller = new InvoiceRowController();
        controller.invoice = invoice;
        loader.setController(controller);

        try {
            view = (Node) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getChildren().add(view);
    }


    public enum InvoiceRowFields {
        STATUS, ID, CLIENT, EMAIL, TEL, RANGE, DATE, TOTAL, NOTES
    }

    public void setValues(HashMap<InvoiceRowFields, String> values) {
        controller.setLblId(values.get(InvoiceRowFields.ID));
        controller.setLblClient(values.get(InvoiceRowFields.CLIENT));
        controller.setLblEmail(values.get(InvoiceRowFields.EMAIL));
        controller.setLblTel(values.get(InvoiceRowFields.TEL));
        controller.setLblDate(values.get(InvoiceRowFields.DATE));
        controller.setLblRange(values.get(InvoiceRowFields.RANGE));
        controller.setLblTotal(values.get(InvoiceRowFields.TOTAL));

        String vat = Formatter.TWO_DECIMALS.format(
                Double.parseDouble(values.get(InvoiceRowFields.TOTAL)) *9/100);
        controller.setLblVAT(vat);

        controller.setLblStatus(values.get(InvoiceRowFields.STATUS));
        controller.setRecStatus(invoice.getStatusColors().get(
                Invoice.Status.valueOf(values.get(InvoiceRowFields.STATUS))));
        controller.setLblNotes(values.get(InvoiceRowFields.NOTES));

    }

}
