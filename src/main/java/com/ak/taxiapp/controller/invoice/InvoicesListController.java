package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.invoice.InvoiceRow;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.invoice.InvoiceDAO;
import com.ak.taxiapp.ss.InvoiceCard;
import com.ak.taxiapp.util.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InvoicesListController extends Controller {

    // the Vbox containing all the invoices
    public VBox vBoxContainer;
    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            search();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        createInvoiceCards();
        createInvoiceRows();
    }

    private void search() throws SQLException {
        try {
            ObservableList<Invoice> resultSet = InvoiceDAO.searchAll();
            invoices = resultSet;
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    public void onTabInvoice(ActionEvent event) {
        TaxiApplication.showInvoiceView();
    }

    public void createInvoiceCards() {
        vBoxContainer.getChildren().clear();

        for (Invoice invoice : invoices) {
            HashMap<InvoiceCard.InvoiceCardFields, String> values = new HashMap<>();
            InvoiceCard invoiceCard = new InvoiceCard();

            values.put(InvoiceCard.InvoiceCardFields.CLIENT,
                    invoice.clientNameProperty().getValue());
            values.put(InvoiceCard.InvoiceCardFields.DATE,
                    invoice.getDateProperty());
            values.put(InvoiceCard.InvoiceCardFields.TOTAL,
                    invoice.totalProperty().getValue().toString());
            values.put(InvoiceCard.InvoiceCardFields.ID,
                    invoice.idProperty().getValue());


            invoiceCard.setValues(values);
//            invoiceCards.add(invoiceCard);

            vBoxContainer.getChildren().add(invoiceCard);
        }
    }

    public void createInvoiceRows() {
        vBoxContainer.getChildren().clear();

        for (Invoice invoice : invoices) {
            // values to pass to InvoiceRow
            HashMap<InvoiceRow.InvoiceRowFields, String> values = new HashMap<>();
            InvoiceRow invoiceRow = new InvoiceRow(invoice);

            values.put(InvoiceRow.InvoiceRowFields.CLIENT,
                    invoice.getClientName());
            values.put(InvoiceRow.InvoiceRowFields.EMAIL,
                    invoice.getClientEmail());
            values.put(InvoiceRow.InvoiceRowFields.TEL,
                    invoice.getClientTel());
            values.put(InvoiceRow.InvoiceRowFields.DATE,
                    invoice.getDateProperty());
            values.put(InvoiceRow.InvoiceRowFields.RANGE,
                    invoice.getDateRange());
            values.put(InvoiceRow.InvoiceRowFields.TOTAL,
                    String.valueOf(invoice.getTotal()));
            values.put(InvoiceRow.InvoiceRowFields.ID,
                    invoice.idProperty().getValue());
            values.put(InvoiceRow.InvoiceRowFields.STATUS,
                    invoice.getStatusProperty());
            values.put(InvoiceRow.InvoiceRowFields.NOTES,
                    invoice.getNotes());


            invoiceRow.setValues(values);

            vBoxContainer.getChildren().add(invoiceRow);
        }
    }

    @Override
    public void updateView() {
        try {
            search();
            createInvoiceRows();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
