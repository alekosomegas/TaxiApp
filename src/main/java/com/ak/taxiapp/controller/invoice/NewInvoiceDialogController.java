package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.controller.Controller;
import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.invoice.InvoiceTable;
import com.ak.taxiapp.model.invoice.InvoiceTableRow;
import com.ak.taxiapp.util.GeneratePDF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.time.LocalDate;

public class NewInvoiceDialogController extends Controller {
    public Label lblInvoiceNo;
    public DatePicker dpDate;
    public Label lblClient;
    public Label lblFirstDate;
    public Label lblLastDate;
    public TableView<InvoiceTableRow> tvInvoiceTable;
    public TableColumn<InvoiceTableRow, String> tcInvoiceDate;
    public TableColumn<InvoiceTableRow, String> tcInvoicePassenger;
    public TableColumn<InvoiceTableRow, String> tcInvoiceFrom;
    public TableColumn<InvoiceTableRow, String> tcInvoiceStops;
    public TableColumn<InvoiceTableRow, String> tcInvoiceTo;
    public TableColumn<InvoiceTableRow, Integer> tcInvoicePrice;
    public TableColumn<InvoiceTableRow, String> tcInvoiceNotes;
    public Label lblTotal;

    private Invoice invoice;
    private InvoiceTable invoiceTable;

    @FXML
    private void initialize() throws SQLException {

    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
        invoiceTable = invoice.getInvoiceTable();
        setValues();
    }

    private void setValues() {
        dpDate.setValue(LocalDate.now());
        lblClient.setText(invoice.getClientName());

        lblFirstDate.setText(invoice.getInvoiceTable().getOldestDate());
        lblLastDate.setText(invoice.getInvoiceTable().getNewestDate());
        lblTotal.setText(String.valueOf(invoiceTable.getTotal()));

        tcInvoiceDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        tcInvoicePassenger.setCellValueFactory(cellData -> cellData.getValue().passengerProperty());
        tcInvoiceFrom.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        tcInvoiceStops.setCellValueFactory(cellData -> cellData.getValue().stopsProperty());
        tcInvoiceTo.setCellValueFactory(cellData -> cellData.getValue().toProperty());
        tcInvoicePrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tcInvoiceNotes.setCellValueFactory(cellData -> cellData.getValue().notesProperty());

        tvInvoiceTable.setItems(invoiceTable.getAllRows());
        tvInvoiceTable.sort();

    }

    @Override
    public void insert() throws Exception {
        onDateAction();
        new GeneratePDF(invoice);
    }


    public void onDateAction() {
        invoice.setDate(CalendarModel.convertDate(dpDate.getValue()));
    }
}
