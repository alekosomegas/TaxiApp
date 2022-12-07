package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.model.invoice.*;
import com.ak.taxiapp.util.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.HashMap;

public class InvoiceViewController extends Controller {
    public TableView<Invoice> tvInvoiceTable;
    public TableColumn<Invoice, String> tcInvoiceId;
    public TableColumn<Invoice, String> tcInvoiceDate;
    public TableColumn<Invoice, String> tcInvoiceClient;
    public TableColumn<Invoice, Integer> tcInvoiceTotal;
    public VBox vBoxContainer;
    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws SQLException {
        tcInvoiceId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tcInvoiceDate.setCellValueFactory(cellData -> cellData.getValue().datePropertyProperty());
        tcInvoiceClient.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        tcInvoiceTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        search();
        createInvoiceCards();
    }

    @FXML
    private void search() throws SQLException {
        try {
            ObservableList<Invoice> resultSet = InvoiceDAO.searchAll();
            tvInvoiceTable.setItems(resultSet);
            invoices = resultSet;
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    public void createInvoiceCards() {
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
            vBoxContainer.getChildren().add(invoiceCard);
        }

    }


}
