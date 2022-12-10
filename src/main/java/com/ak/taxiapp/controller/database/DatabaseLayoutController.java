package com.ak.taxiapp.controller.database;

import com.ak.taxiapp.Layouts;
import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.util.Controller;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DatabaseLayoutController extends Controller {

    public TabPane tabPane;
    public Tab tabRides;
    public Tab tabClients;
    public Tab tabInvoices;
    public Tab tabCars;
    public Tab tabDrivers;

    @FXML @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Tabs tab : Tabs.values()) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TaxiApplication.class.getResource(paths.get(tab)));
                Node layout = loader.load();
                Controller controller = loader.getController();

                LAYOUTS.put(tab, layout);
                CONTROLLERS.put(tab, controller);
            } catch (Exception e) {
                System.out.println(tab);
                System.out.println(paths.get(tab));
                e.printStackTrace();
            }
        }
        onSelectionChanged();
    }

    private enum Tabs {
        RIDES, CLIENTS, INVOICES, CARS, DRIVERS
    }

    private final HashMap<Tabs, String> paths= new HashMap<>() {
        {
            put(Tabs.CLIENTS,   "fxml/client/"  + "ClientsDbView"   + ".fxml");
            put(Tabs.CARS,      "fxml/car/"     + "CarsDbView"      + ".fxml");
            put(Tabs.RIDES,     "fxml/ride/"    + "RidesDbView"     + ".fxml");
            put(Tabs.DRIVERS,   "fxml/driver/"  + "DriversDbView"   + ".fxml");
            put(Tabs.INVOICES,  "fxml/invoice/" + "InvoicesDbView"  + ".fxml");
        }
    };

    public final HashMap<Tabs, Node> LAYOUTS = new HashMap<>(5);
    public final HashMap<Tabs, Controller> CONTROLLERS = new HashMap<>(5);
    public final HashMap<String, Tabs> TABS = new HashMap<>() {
        {
            put("tabClients",   Tabs.CLIENTS);
            put("tabCars",      Tabs.CARS);
            put("tabRides",     Tabs.RIDES);
            put("tabDrivers",   Tabs.DRIVERS);
            put("tabInvoices",  Tabs.INVOICES);
        }
    };




    public void onSelectionChanged() {
        Tab selected = tabPane.getSelectionModel().getSelectedItem();
        String id = selected.getId();
        AnchorPane anchorPane = (AnchorPane) selected.getContent();

        selected.setContent(LAYOUTS.get(TABS.get(id)));

//        anchorPane.getChildren().add());
    }
}
