package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.model.invoice.*;
import com.ak.taxiapp.util.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class InvoiceViewController extends Controller {
    public TableView<Invoice> tvInvoiceTable;
    public TableColumn<Invoice, String> tcInvoiceId;
    public TableColumn<Invoice, String> tcInvoiceDate;
    public TableColumn<Invoice, String> tcInvoiceClient;
    public TableColumn<Invoice, Integer> tcInvoiceTotal;


    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        tcInvoiceId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tcInvoiceDate.setCellValueFactory(cellData -> cellData.getValue().datePropertyProperty());
        tcInvoiceClient.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        tcInvoiceTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        search();

    }

    @FXML
    private void search() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Invoice> resultSet = InvoiceDAO.searchAll();
            tvInvoiceTable.setItems(resultSet);
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }


}
