package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.ss.InvoiceTableRowDAO;
import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.ss.InvoiceTable;
import com.ak.taxiapp.ss.InvoiceTableRow;
import com.ak.taxiapp.util.GeneratePDF;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        try {
            InvoiceTableRowDAO.insert(invoice.getId(), invoice.getDateString(), invoiceTable.getRidesIds());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new GeneratePDF(invoice);
    }


    public void onDateAction() {
        invoice.setDate(CalendarModel.convertDate(dpDate.getValue()));
    }
}
