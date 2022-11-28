package com.ak.taxiapp.controller.client;

import com.ak.taxiapp.model.client.ClientDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.HashMap;

public class NewClientDialogController {
    @FXML
    private TextField nameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField telText;
//    public RootLayoutController rlc;
    private HashMap<String, String> selectedClientValues;


    //Insert a client to the DB
    @FXML
    public void insert() throws SQLException, ClassNotFoundException {
        try {
            ClientDAO.insert(nameText.getText(),addressText.getText(),emailText.getText(),telText.getText());
        } catch (SQLException e) {
            throw e;
        }
    }

    public void setValues(HashMap<String, String> values) {
        nameText.setText(values.get("clientNameCol"));
        addressText.setText(values.get("clientAddressCol"));
        emailText.setText(values.get("clientEmailCol"));
        telText.setText(values.get("clientTelCol"));
        selectedClientValues = values;
    }

    @FXML public void update() throws SQLException {
        selectedClientValues.put("clientNameCol", nameText.getText());
        selectedClientValues.put("clientAddressCol", addressText.getText());
        selectedClientValues.put("clientEmailCol", emailText.getText());
        selectedClientValues.put("clientTelCol", telText.getText());
        ClientDAO.update(selectedClientValues);
    }
}
