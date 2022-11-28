package com.ak.taxiapp.controller.driver;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.controller.Controller;
import com.ak.taxiapp.model.driver.Driver;
import com.ak.taxiapp.model.driver.DriverDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.HashMap;

public class DriversViewController extends Controller {

    public TableView<Driver> driversTable;
    public TableColumn<Driver, Integer> driverIdCol;
    public TableColumn<Driver, String> driversNameCol;
    public TableColumn<Driver, Integer> driversCarCol;
    public TableColumn<Driver, String> driversColorCol;
    public VBox vbColors;
    private Driver selectedDriver;

    @FXML
    private void initialize() throws SQLException {
        driverIdCol.setCellValueFactory(cellData -> cellData.getValue().driver_idProperty().asObject());
        driversNameCol.setCellValueFactory(cellData -> cellData.getValue().driver_nameProperty());
        driversCarCol.setCellValueFactory(cellData -> cellData.getValue().driver_carProperty().asObject());
        driversColorCol.setCellValueFactory(cellData -> cellData.getValue().driver_colorProperty());
        search();

        int rows = driversTable.getItems().size();
        for (int i = 0; i < rows; i++) {
            Rectangle rec = new Rectangle(15,15,Color.valueOf(driversColorCol.getCellData(i)));
            vbColors.getChildren().add(rec);
            rec.setTranslateY(25);

        }

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