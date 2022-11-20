package com.ak.taxiapp.controller;

import com.ak.taxiapp.TaxiApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RootLayoutController extends Controller{
    @FXML
    public Label resultArea;
    public Button BtnRidesByClient;

    @FXML
    private Button ridesViewBtn;
    @FXML
    private Button clientsViewBtn;
    @FXML private Button CarsViewBtn;
    @FXML private Button driversViewBtn;


    @FXML
    public void initialize() {

    }

    //Exit the program
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    //Help Menu button behavior
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("This is a test taxi app");
        alert.setContentText("WIP");
        alert.show();
    }
    /*
        passes the rootlayout controller instance to the clientdbcontroler
     */
    public void onBtnClientsView() {
        TaxiApplication.showClientDbView();
        resultArea.setText("Clients Database View");
    }
    public void onBtnRidesView() {
        TaxiApplication.showRidesView();
        resultArea.setText("Rides Database View");

    }

    public void setResultText(String text) {
        resultArea.setText(text);
    }


    public void onBtnDriversView(ActionEvent actionEvent) {
        TaxiApplication.showDriversView();
        resultArea.setText("Drivers Database View");
    }

    public void onBtnCarsView(ActionEvent actionEvent) {
        TaxiApplication.showCarsView();
        resultArea.setText("Cars Database View");
    }

    public void onBtnRidesByClient(ActionEvent actionEvent) {
        TaxiApplication.showRidesByClientView();
        resultArea.setText("Rides by Client View");
    }

    public void onBtnCalendarView() {
        TaxiApplication.showCalendarView("CalendarDayView.fxml");
    }

}
