package com.ak.taxiapp.controller.car;

import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.model.car.CarDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.HashMap;

public class NewCarDialogController extends Controller {
    @FXML private Label lblTitle;
    @FXML private TextField tfRegistration;
    @FXML private TextField tfMileage;
    @FXML private TextField tfMake;
    @FXML private TextField tfModel;
    private HashMap<String,String> selectedCarValues;

    @FXML @Override
    public void insert() throws SQLException {
        CarDAO.insert(tfRegistration.getText(),tfMake.getText(),tfModel.getText(),Integer.parseInt(tfMileage.getText()));
    }

    public void setValues(HashMap<String, String> values) {
        tfRegistration.setText(values.get("carsRegCol"));
        tfMileage.setText(values.get("carsMilCol"));
        tfMake.setText(values.get("carsMakeCol"));
        tfModel.setText(values.get("carsModelCol"));
        selectedCarValues = values;
    }

    @FXML public void update() throws SQLException {
        selectedCarValues.put("carsMakeCol", tfMake.getText());
        selectedCarValues.put("carsModelCol", tfModel.getText());
        selectedCarValues.put("carsMilCol", tfMileage.getText());
        selectedCarValues.put("carsRegCol", tfRegistration.getText());
        CarDAO.update(selectedCarValues);
    }

}
