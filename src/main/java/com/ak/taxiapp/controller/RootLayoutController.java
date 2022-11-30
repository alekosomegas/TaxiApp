package com.ak.taxiapp.controller;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.util.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class RootLayoutController extends Controller {
    @FXML
    public Label resultArea;
    public Button BtnRidesByClient;
    public BorderPane bpRoot;

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


    public void onBtnDriversView() {
        TaxiApplication.showDriversView();
        resultArea.setText("Drivers Database View");
    }

    public void onBtnCarsView() {
        TaxiApplication.showCarsView();
        resultArea.setText("Cars Database View");
    }

    public void onBtnRidesByClient() {
        TaxiApplication.showRidesByClientView();
        resultArea.setText("Rides by Client View");
    }

    public void onBtnCalendarView() {
        TaxiApplication.showCalendarView("fxml/calendar/CalendarDayView.fxml");
    }

    public void onBtnInvoicesView() {
        TaxiApplication.showInvoicesView();
    }
}
