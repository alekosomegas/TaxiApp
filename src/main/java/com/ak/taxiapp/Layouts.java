package com.ak.taxiapp;

import com.ak.taxiapp.controller.RootLayoutController;
import com.ak.taxiapp.controller.calendar.CalendarDayViewController;
import com.ak.taxiapp.util.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.util.HashMap;

public class Layouts {
    // number of layouts/controllers
    private final int CAPACITY = 13;

    private static Layouts singleInstance = null;

    public void addRootLController(RootLayoutController rootLayoutController) {
        for (Controller controller : CONTROLLERS.values()) {
            controller.setRootLayoutController(rootLayoutController);
        }
    }

    public enum Pages {
        DASHBOARD, CALENDAR, RIDES, RIDE, EXPENSES, INVOICES, INVOICE, FLEET, CLIENTS, DRIVERS, REPORTS, DATABASE, SETTINGS
    }

    private final HashMap<Pages, String> paths= new HashMap<>() {
        {
            put(Pages.DASHBOARD,"fxml/dashboard/"+ "DashboardLayout"    + ".fxml");
            put(Pages.CALENDAR, "fxml/calendar/" + "CalendarDayView"    + ".fxml");

            put(Pages.RIDES,    "fxml/ride/"     + "RidesListView"      + ".fxml");
            put(Pages.RIDE,     "fxml/ride/"     + "SingleRideView"     + ".fxml");

            put(Pages.EXPENSES, "fxml/expenses/" + "ExpensesLayout"     + ".fxml");

            put(Pages.INVOICES, "fxml/invoice/"  + "InvoicesListView"   + ".fxml");
            put(Pages.INVOICE,  "fxml/invoice/"  + "SingleInvoiceView"  + ".fxml");

            put(Pages.FLEET,    "fxml/car/"      + "CarsDbView"         + ".fxml");
            put(Pages.CLIENTS,  "fxml/client/"   + "ClientsDbView"      + ".fxml");
            put(Pages.DRIVERS,  "fxml/driver/"   + "DriversDbView"      + ".fxml");
            put(Pages.REPORTS,  "fxml/reports/"  + "ReportsLayout"      + ".fxml");
            put(Pages.DATABASE, "fxml/database/" + "DatabaseLayout"     + ".fxml");
            put(Pages.SETTINGS, "fxml/settings/" + "SettingsLayout"     + ".fxml");
        }
    };

    public final HashMap<Pages, Controller> CONTROLLERS = new HashMap<>(CAPACITY);
    public final HashMap<Pages, Node> LAYOUTS = new HashMap<>(CAPACITY);


    /**
     * A Singleton object holding all controllers and layouts
     */
    private Layouts() {
        for (Pages page : Pages.values() ) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TaxiApplication.class.getResource(paths.get(page)));
                Node layout = loader.load();
                Controller controller = loader.getController();

                LAYOUTS.put(page, layout);
                CONTROLLERS.put(page, controller);
            } catch (Exception e) {
                System.out.println(page.toString());
                System.out.println(paths.get(page));
                e.printStackTrace();
            }
        }
        singleInstance = this;
    }

    public static Layouts getInstance() {
        if(singleInstance == null) {
            singleInstance = new Layouts();
        }
        return singleInstance;
    }

    public static void updateLayoutView(Pages page) {
        if (singleInstance == null || page == null) {return;}
        singleInstance.CONTROLLERS.get(page).updateView();
    }
}
