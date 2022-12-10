package com.ak.taxiapp.controller.driver;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.model.driver.Driver;
import com.ak.taxiapp.model.driver.DriverDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DriversViewController extends Controller {

    public TableView<Driver> driversTable;
    public TableColumn<Driver, Integer> driverIdCol;
    public TableColumn<Driver, String> driversNameCol;
    public TableColumn<Driver, Integer> driversCarCol;
    public TableColumn<Driver, String> driversColorCol;
    public VBox vbColors;
    private Driver selectedDriver;

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void initialize() throws SQLException {
        driversTable.setEditable(true);
        driverIdCol.setCellValueFactory(cellData -> cellData.getValue().driver_idProperty().asObject());
        driversNameCol.setCellValueFactory(cellData -> cellData.getValue().driver_nameProperty());
        driversCarCol.setCellValueFactory(cellData -> cellData.getValue().driver_carProperty().asObject());
        driversColorCol.setCellValueFactory(cellData -> cellData.getValue().driver_colorProperty());

        driversColorCol.setCellFactory(new Callback<TableColumn<Driver, String>, TableCell<Driver, String>>() {
            @Override
            public TableCell<Driver, String> call(TableColumn<Driver, String> driverStringTableColumn) {
                return new TableCell<Driver, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (!empty) {
                            int index = indexProperty().getValue() < 0 ? 0 : indexProperty().getValue();
                            String col = driverStringTableColumn.getTableView().getItems().get(index).getDriver_color();
                            setStyle("-fx-background-color: " + col.replace("0x", "#"));
                        }
                    }
                };
            }
        });
        search();

    }

   @FXML
    private void search() throws SQLException {
        try {
            ObservableList<Driver> driverData = DriverDAO.searchDrivers();
            populateDrivers(driverData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    private void populateDrivers(ObservableList<Driver> driverData) {
        driversTable.setItems(driverData);
    }

    @FXML
    public void selectDriver() {
        selectedDriver = driversTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void deleteSelectedDriver() {
        if(selectedDriver != null) {
            Integer id = selectedDriver.getDriver_id();
            try {
                DriverDAO.deleteDriverWithId(id);
                rlc.setResultText("Client deleted! Driver id: " + id + "\n");
                search();
            } catch (SQLException e) {
                rlc.setResultText("Problem occurred while deleting driver " + e);
            }
        }
    }
    @FXML
    private void onNewDriverClicked() {
        TaxiApplication.showNewDriverDialog();
    }

    @FXML private void onEditClicked() {
        HashMap<String, String> values = new HashMap<>();
        if(selectedDriver != null) {
            int id = selectedDriver.getDriver_id();
            ObservableList<TableColumn<Driver, ?>> columns = driversTable.getColumns();
            for (TableColumn<Driver, ?> column : columns) {
                values.put(column.getId(), column.getCellData(selectedDriver).toString());
            }
        }
        TaxiApplication.showEditDriverDialog(values);
    }
}