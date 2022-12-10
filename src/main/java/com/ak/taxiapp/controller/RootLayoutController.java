package com.ak.taxiapp.controller;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.util.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RootLayoutController extends Controller {
    @FXML
    public Label resultArea;
    public Button BtnRidesByClient;
    public BorderPane bpRoot;
    public Button calendarViewBtn;
    public Button dashboardViewBtn;
    public Button expensesViewBtn;
    public Button invoicesViewBtn;
    public Button fleetViewBtn;
    public Button reportsViewBtn;
    public Button databaseViewBtn;
    public Button settingsViewBtn;

    @FXML
    private Button ridesViewBtn;
    @FXML
    private Button clientsViewBtn;
    @FXML private Button CarsViewBtn;
    @FXML private Button driversViewBtn;

    private Button selectedBtn;
    private HashMap<String, ImageView> icons = new HashMap<>(22) {
    };

    @FXML @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.selectedBtn = dashboardViewBtn;
        getIcons();


    }

    private void getIcons() {
        try {

            icons.put("Dashboard",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-home-24px.png"
                    ))));
            icons.put("Dashboard-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-home-24px.png"
                    ))));

            icons.put("Calendar",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-event-24px.png"
                    ))));
            icons.put("Calendar-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-event-24px.png"
                    ))));

            icons.put("Rides",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-swap_calls-24px.png"
                    ))));
            icons.put("Rides-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-swap_calls-24px.png"
                    ))));

            icons.put("Expenses",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-account_balance_wallet-24px.png"
                    ))));
            icons.put("Expenses-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-account_balance_wallet-24px.png"
                    ))));

            icons.put("Invoices",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-description-24px.png"
                    ))));
            icons.put("Invoices-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-description-24px.png"
                    ))));

            icons.put("Fleet",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-build-24px.png"
                    ))));
            icons.put("Fleet-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-build-24px.png"
                    ))));

            icons.put("Clients",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-supervisor_account-24px.png"
                    ))));
            icons.put("Clients-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-supervisor_account-24px.png"
                    ))));

            icons.put("Drivers",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-local_taxi-24px.png"
                    ))));
            icons.put("Drivers-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-local_taxi-24px.png"
                    ))));

            icons.put("Reports",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-assessment-24px.png"
                    ))));
            icons.put("Reports-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-assessment-24px.png"
                    ))));

            icons.put("Database",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\baseline-dns-24px.png"
                    ))));
            icons.put("Database-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\baseline-dns-24px.png"
                    ))));

            icons.put("Settings",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar\\icon-action-settings_24px.png"
                    ))));
            icons.put("Settings-selected",
                    stylizeIv(new ImageView(new Image(
                            "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\sidebar_selected\\icon-action-settings_24px.png"
                    ))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageView stylizeIv(ImageView iv) {
        iv.setFitHeight(20);
        iv.setFitWidth(20);
        iv.getStyleClass().add("side_bar__button__icon");

        return iv;
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
        clientsViewBtn.setGraphic(icons.get("Clients-selected"));
        clientsViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(clientsViewBtn);
        TaxiApplication.showClientDbView();
        setResultText("Clients Database View");
    }
    public void onBtnRidesView() {
        ridesViewBtn.setGraphic(icons.get("Rides-selected"));
        ridesViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(ridesViewBtn);
        TaxiApplication.showRidesView();
        setResultText("Rides Database View");
    }

    public void setResultText(String text) {
        if (resultArea != null) {
            resultArea.setText(text);
        }
    }


    public void onBtnDriversView() {
        driversViewBtn.setGraphic(icons.get("Drivers-selected"));
        driversViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(driversViewBtn);
        TaxiApplication.showDriversView();
        setResultText("Drivers Database View");
    }

    public void onBtnCarsView() {
        fleetViewBtn.setGraphic(icons.get("Fleet-selected"));
        fleetViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(fleetViewBtn);
        TaxiApplication.showCarsView();
        setResultText("Cars Database View");
    }

    public void onBtnRidesByClient() {
        TaxiApplication.showRidesByClientView();
        setResultText("Rides by Client View");
    }

    public void onBtnCalendarView() {
        calendarViewBtn.setGraphic(icons.get("Calendar-selected"));
        calendarViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(calendarViewBtn);
        TaxiApplication.showCalendarView("fxml/calendar/CalendarDayView.fxml");
    }

    public void onBtnInvoicesView() {
        invoicesViewBtn.setGraphic(icons.get("Invoices-selected"));
        invoicesViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(invoicesViewBtn);
        TaxiApplication.showInvoiceView();
    }

    private void highlight(Button button) {
        selectedBtn.getStyleClass().remove("side_bar__button--selected");
        selectedBtn.getStyleClass().add("side_bar__button");
        selectedBtn.setGraphic(icons.get(selectedBtn.getText()));

        selectedBtn = button;

        selectedBtn.getStyleClass().remove("side_bar__button");
        selectedBtn.getStyleClass().add("side_bar__button--selected");
    }

    public void onBtnDashboardView(ActionEvent event) {
        dashboardViewBtn.setGraphic(icons.get("Dashboard-selected"));
        dashboardViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(dashboardViewBtn);
        TaxiApplication.showDashboardView();
    }

    public void onBtnSettingsView(ActionEvent event) {
        settingsViewBtn.setGraphic(icons.get("Settings-selected"));
        settingsViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(settingsViewBtn);
    }

    public void onBtnDatabaseView(ActionEvent event) {
        databaseViewBtn.setGraphic(icons.get("Database-selected"));
        databaseViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(databaseViewBtn);
        TaxiApplication.showDatabaseView();
    }

    public void onBtnReportsView(ActionEvent event) {
        reportsViewBtn.setGraphic(icons.get("Reports-selected"));
        reportsViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(reportsViewBtn);
    }

    public void onBtnExpensesView(ActionEvent event) {
        expensesViewBtn.setGraphic(icons.get("Expenses-selected"));
        expensesViewBtn.getGraphic().getStyleClass().add("side_bar__button__icon--selected");
        highlight(expensesViewBtn);
    }
}
