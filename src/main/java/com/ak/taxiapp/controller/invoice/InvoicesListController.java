package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.custom.InvoiceRow;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.invoice.InvoiceDAO;
import com.ak.taxiapp.util.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InvoicesListController extends Controller {

    public AnchorPane apContent;
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

    public void onOK(ActionEvent event) {
    }

    public void onCancel(ActionEvent event) {
    }

    public void onClear(ActionEvent event) {
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
            HashMap<InvoiceRow.InvoiceRowFields, String> values = new HashMap<>();
            InvoiceRow invoiceRow = new InvoiceRow();

            values.put(InvoiceRow.InvoiceRowFields.CLIENT,
                    invoice.clientNameProperty().getValue());
            values.put(InvoiceRow.InvoiceRowFields.DATE,
                    invoice.getDateProperty());
            values.put(InvoiceRow.InvoiceRowFields.TOTAL,
                    invoice.totalProperty().getValue().toString());
            values.put(InvoiceRow.InvoiceRowFields.ID,
                    invoice.idProperty().getValue());


            invoiceRow.setValues(values);

            vBoxContainer.getChildren().add(invoiceRow);
        }
    }

    @Override
    public void updateView() {
        try {
            search();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        createInvoiceCards();
    }

}
