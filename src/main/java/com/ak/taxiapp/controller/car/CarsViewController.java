package com.ak.taxiapp.controller.car;

import com.ak.taxiapp.Layouts;
import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.model.car.Car;
import com.ak.taxiapp.model.car.CarDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.HashMap;

public class CarsViewController extends Controller {
    public TableView<Car> carsTable;
    public TableColumn<Car, Integer> carsIdCol;
    public TableColumn<Car, String> carsRegCol;
    public TableColumn<Car, String> carsMakeCol;
    public TableColumn<Car, String> carsModelCol;
    public TableColumn<Car, Integer> carsMilCol;

    private Car selectedCar;

    @FXML
    private void initialize() throws SQLException {
        carsIdCol.setCellValueFactory(cellData -> cellData.getValue().car_idProperty().asObject());
        carsRegCol.setCellValueFactory(cellData -> cellData.getValue().car_regProperty());
        carsMakeCol.setCellValueFactory(cellData -> cellData.getValue().car_makeProperty());
        carsModelCol.setCellValueFactory(cellData -> cellData.getValue().car_modelProperty());
        carsMilCol.setCellValueFactory(cellData -> cellData.getValue().car_mileageProperty().asObject());
        search();
    }

    @FXML
    private void search() throws SQLException {
        try {
            ObservableList<Car> carData = CarDAO.searchCars();
            populateDrivers(carData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    private void populateDrivers(ObservableList<Car> carData) {
        carsTable.setItems(carData);
    }


    @FXML
    public void selectCar() {
        selectedCar = carsTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void deleteSelectedCar() {
        if(selectedCar != null) {
            Integer id = selectedCar.getCar_id();
            try {
                CarDAO.delete(id);
                rlc.setResultText("Car deleted! Car id: " + id + "\n");
                search();
            } catch (SQLException e) {
                rlc.setResultText("Problem occurred while deleting car " + e);
            }
        }
    }

    @FXML
    private void onNewCarClicked() {
        TaxiApplication.showNewCarDialog();
    }

    @FXML
    private void onEditCarClicked() {
        HashMap<String, String> values = new HashMap<>();
        if(selectedCar != null) {
            int id = selectedCar.getCar_id();
            ObservableList<TableColumn<Car,?>> columns = carsTable.getColumns();
            for (TableColumn<Car, ?> column : columns) {
                values.put(column.getId(), column.getCellData(selectedCar).toString());
            }
        }
        TaxiApplication.showEditCarDialog(values);
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
