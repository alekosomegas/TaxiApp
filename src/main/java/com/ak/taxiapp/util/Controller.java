package com.ak.taxiapp.util;

import com.ak.taxiapp.controller.RootLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    public RootLayoutController rlc;

    public void setRootLayoutController(RootLayoutController rlc) {
        this.rlc = rlc;
    }

    public boolean checkInputs() {
        return true;
    }

    public void insert() throws Exception {

    }

    public void updateView() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
