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

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InvoiceViewController extends Controller {
    public TableView<Invoice> tvInvoiceTable;
    public TableColumn<Invoice, String> tcInvoiceId;
    public TableColumn<Invoice, String> tcInvoiceDate;
    public TableColumn<Invoice, String> tcInvoiceClient;
    public TableColumn<Invoice, Integer> tcInvoiceTotal;
    public VBox vBoxContainer;
    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();
    private ObservableList<InvoiceCard> invoiceCards = FXCollections.observableArrayList();


    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void initialize() throws SQLException {
        tcInvoiceId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tcInvoiceDate.setCellValueFactory(cellData -> cellData.getValue().datePropertyProperty());
        tcInvoiceClient.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        tcInvoiceTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        search();

    }

    @FXML
    private void search() throws SQLException {
        try {
            ObservableList<Invoice> resultSet = InvoiceDAO.searchAll();
            tvInvoiceTable.setItems(resultSet);
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }


    @Override
    public void updateView() {
        try {
            search();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
