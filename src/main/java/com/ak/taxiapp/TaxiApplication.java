package com.ak.taxiapp;

import com.ak.taxiapp.controller.*;
import com.ak.taxiapp.controller.car.NewCarDialogController;
import com.ak.taxiapp.controller.client.NewClientDialogController;
import com.ak.taxiapp.controller.driver.NewDriverDialogControler;
import com.ak.taxiapp.controller.invoice.InvoicesListController;
import com.ak.taxiapp.controller.invoice.NewInvoiceDialogController;
import com.ak.taxiapp.controller.invoice.SingleInvoiceController;
import com.ak.taxiapp.controller.ride.NewRideDialogController;
import com.ak.taxiapp.controller.ride.RidesViewController;
import com.ak.taxiapp.controller.ride.SingleRideController;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.util.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class TaxiApplication extends Application {
    //This is our PrimaryStage (It contains everything)
    private Stage primaryStage;
    //This is the BorderPane of RootLayout
    public static BorderPane rootLayout;
    public static RootLayoutController rootLayoutController;

    private static Layouts layouts;

    @Override
    public void init() throws Exception {
        DBUtil.dbConnect();
    }
    @Override
    public void stop() throws SQLException {
        System.out.println("Stage is closing");
        DBUtil.dbDisconnect();
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        //1) Declare a primary stage (Everything will be on this stage)
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Taxi App");
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        //2) Initialize RootLayout
        initRootLayout();
        //3) Display the EmployeeOperations View
//        showClientDbView();
        initLayouts();

    }

    private void initLayouts() {
        layouts = Layouts.getInstance();
        layouts.addRootLController(rootLayoutController);
    }

    public void initRootLayout() {
        try {
            //First, load root layout from RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/RootLayout.fxml"));
            rootLayout = loader.load();
            rootLayoutController = loader.getController();
            //Second, show the scene containing the root layout.
            Scene scene = new Scene(rootLayout); //We are sending rootLayout to the Scene.
            primaryStage.setScene(scene); //Set the scene in primary stage.
            //Third, show the primary stage
            primaryStage.show(); //Display the primary stage
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    private static void show(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource(fxml));

            Node node = loader.load();
            Controller controller = loader.getController();
            controller.setRootLayoutController(rootLayoutController);
            rootLayout.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void showDashboardView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.DASHBOARD));
    }
    /*
        Loads the client db view in the center of the boarder pane
        and returns its controller instance.
     */
    public static void showClientDbView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.CLIENTS));
    }

    public static void showRidesView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.RIDES));

    }

    public static void showRideView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.RIDE));

    }

    public static void showRideView(Ride ride) {
        SingleRideController controller = (SingleRideController) layouts.CONTROLLERS.get(Layouts.Pages.RIDE);
        controller.populateData(ride);
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.RIDE));

    }
    public static void showDriversView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.DRIVERS));
    }

    public static void showCarsView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.FLEET));
    }

    public static void showRidesByClientView() {
        show("fxml/ride/RidesByClientView.fxml");
    }

    public static void showCalendarView(String view) {
        layouts.CONTROLLERS.get(Layouts.Pages.CALENDAR).updateView();
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.CALENDAR));
//        show(view);
    }

    public static void showInvoicesView() {
        InvoicesListController controller = (InvoicesListController) layouts.CONTROLLERS.get(Layouts.Pages.INVOICES);
        controller.updateView();
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.INVOICES));
    }
    public static void showInvoiceView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.INVOICE));
    }

    public static void showInvoiceEditView(Invoice invoice) throws SQLException {
        SingleInvoiceController controller = (SingleInvoiceController) layouts.CONTROLLERS.get(Layouts.Pages.INVOICE);
        controller.populateData(invoice);
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.INVOICE));
    }

    public static void showDatabaseView() {
        rootLayout.setCenter(layouts.LAYOUTS.get(Layouts.Pages.DATABASE));
    }
    private static void showAndWaitNewDialog(Optional<ButtonType> result, FXMLLoader loader, Dialog<ButtonType> dialog) throws SQLException {
        if (result.get() == ButtonType.OK) {
            Controller controller = loader.getController();
            if(controller.checkInputs()) {
                try {
                    controller.insert();
                } catch (Exception e) {
                    e.printStackTrace();
                    result = dialog.showAndWait();
                    showAndWaitNewDialog(result, loader, dialog);
                }
            } else {
                result = dialog.showAndWait();
                showAndWaitNewDialog(result, loader, dialog);
            }
        }
    }

    private static void showDialog(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource(fxml));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            showAndWaitNewDialog(result, loader, dialog);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showNewRideDialog() {
        showDialog("fxml/ride/NewRideDialog.fxml");
        showRidesView();
    }

    public static int showNewClientDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/client/NewClientDialog.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                NewClientDialogController ncdc = loader.getController();
                ncdc.insert();
                showClientDbView();
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    public static void showNewCarDialog() {
        showDialog("fxml/car/NewCarDialog.fxml");
        showCarsView();
    }
    // UPDATE CAR
    public static void showEditCarDialog(HashMap<String,String> values) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/car/NewCarDialog.fxml"));
            DialogPane dialogPane = loader.load();
            NewCarDialogController controller = loader.getController();
            controller.setValues(values);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                controller.update();
                showCarsView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showEditDriverDialog(HashMap<String,String> values) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/driver/NewDriverDialog.fxml"));
            DialogPane dialogPane = loader.load();
            NewDriverDialogControler controller = loader.getController();
            controller.setValues(values);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                controller.update();
                showDriversView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showEditClientDialog(HashMap<String,String> values) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/client/NewClientDialog.fxml"));
            DialogPane dialogPane = loader.load();
            NewClientDialogController controller = loader.getController();
            controller.setValues(values);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                controller.update();
                showClientDbView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showEditRideDialog(HashMap<String,String> values) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/ride/NewRideDialog.fxml"));
            DialogPane dialogPane = loader.load();
            NewRideDialogController controller = loader.getController();
            controller.setValues(values);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                controller.update();
                showRidesView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showNewDriverDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/driver/NewDriverDialog.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                NewDriverDialogControler controller = loader.getController();
                controller.insertDriver();
                showDriversView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showNewInvoice(Invoice invoice) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("fxml/invoice/NewInvoiceDialog.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);

            NewInvoiceDialogController controller = loader.getController();
            controller.setInvoice(invoice);

            Optional<ButtonType> result = dialog.showAndWait();


            if (result.get() == ButtonType.OK) {

                controller.insert();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}