package com.ak.taxiapp.controller;

import com.ak.taxiapp.model.DriverDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.HashMap;

public class NewDriverDialogControler extends Controller{

    public ColorPicker cpColorPicker;
    @FXML
    private TextField tfName;
    @FXML private TextField tfCarId;

    @FXML
    public void initialize() {

    }


    @FXML
    public void insertDriver() throws SQLException {
        DriverDAO.insertDriver(
                tfName.getText(),
                Integer.parseInt(tfCarId.getText()),
                cpColorPicker.getValue().toString());
    }

    private HashMap<String,String> selectedDriverValues;

    public void setValues(HashMap<String,String> values) {
        tfName.setText(values.get("driversNameCol"));
        tfCarId.setText(values.get("driversCarCol"));
        cpColorPicker.setValue(Color.valueOf(values.get("driversColorCol")));
        selectedDriverValues = values;
    }

    @FXML public void update() throws SQLException {
        selectedDriverValues.put("driversNameCol", tfName.getText());
        selectedDriverValues.put("driversCarCol", tfCarId.getText());
        selectedDriverValues.put("driversColorCol", cpColorPicker.getValue().toString());
        DriverDAO.updateDriver(selectedDriverValues);
    }
}
