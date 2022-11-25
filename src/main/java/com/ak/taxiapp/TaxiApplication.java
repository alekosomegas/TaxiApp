package com.ak.taxiapp;

import com.ak.taxiapp.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
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
    private static BorderPane rootLayout;
    public static RootLayoutController rootLayoutController;


    @Override
    public void start(Stage primaryStage) throws IOException {
        //1) Declare a primary stage (Everything will be on this stage)
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Test Taxi App - Sample JavaFX App");
        primaryStage.setMaximized(true);
        //2) Initialize RootLayout
        initRootLayout();
        //3) Display the EmployeeOperations View
//        showClientDbView();

    }
    public void initRootLayout() {
        try {
            //First, load root layout from RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("RootLayout.fxml"));
            rootLayout = loader.load();
            rootLayoutController = loader.getController();
            //Second, show the scene containing the root layout.
            Scene scene = new Scene(rootLayout); //We are sending rootLayout to the Scene.
            scene.getStylesheets().add("stylesheet.css");
            primaryStage.setScene(scene); //Set the scene in primary stage.
            //Third, show the primary stage
            primaryStage.show(); //Display the primary stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Loads the client db view in the center of the boarder pane
        and returns its controller instance.
     */
    public static void showClientDbView() {
        try {
            //First, load ClientView from ClientView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("ClientDbView.fxml"));
            AnchorPane clientDbView = loader.load();
            ClientDbController cdbc = loader.getController();
            cdbc.setRootLayoutController(rootLayoutController);
            // Set Employee Operations view into the center of root layout.
            rootLayout.setCenter(clientDbView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRidesView() {
        try {
            //First, load ClientView from ClientView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("RidesView.fxml"));
            AnchorPane ridesView = loader.load();
            RidesViewController rvc = loader.getController();
            rvc.setRootLayoutController(rootLayoutController);
            // Set Employee Operations view into the center of root layout.
            rootLayout.setCenter(ridesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showDriversView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("DriversView.fxml"));
            AnchorPane driversView = loader.load();
            DriversViewController dvc = loader.getController();
            dvc.setRootLayoutController(rootLayoutController);
            rootLayout.setCenter(driversView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // refactor
    public static void showCarsView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("CarsView.fxml"));
            AnchorPane carsView = loader.load();
            CarsViewController cvc = loader.getController();
            cvc.setRootLayoutController(rootLayoutController);
            rootLayout.setCenter(carsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRidesByClientView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("RidesByClientView.fxml"));
            AnchorPane rbcView = loader.load();
            RidesByClientViewController rbcvc = loader.getController();
            rbcvc.setRootLayoutController(rootLayoutController);
            rootLayout.setCenter(rbcView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAndWaitNewRideDialog(Optional<ButtonType> result, FXMLLoader loader, Dialog<ButtonType> dialog) throws SQLException {
        if (result.get() == ButtonType.OK) {
            NewRideDialogController nrdc = loader.getController();
            if(nrdc.checkInputs()) {
                try {
                    nrdc.insertRide();
                } catch (Exception e) {
                    e.printStackTrace();
                    result = dialog.showAndWait();
                    showAndWaitNewRideDialog(result, loader, dialog);
                }
            } else {
                result = dialog.showAndWait();
                showAndWaitNewRideDialog(result, loader, dialog);
            }
        }
    }

    public static void showNewRideDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("NewRideDialog.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            dialogPane.getStylesheets().add("stylesheet.css");
            showAndWaitNewRideDialog(result, loader, dialog);
            showRidesView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int showNewClientDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getResource("NewClientDialog.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                NewClientDialogController ncdc = loader.getController();
                ncdc.insertClient();
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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("NewCarDialog.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                NewCarDialogController ncdc = loader.getController();
                ncdc.insertCar();
                showCarsView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // UPDATE CAR
    public static void showEditCarDialog(HashMap<String,String> values) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("NewCarDialog.fxml"));
            DialogPane dialogPane = loader.load();
            NewCarDialogController controller = loader.getController();
            controller.setValues(values);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                controller.updateCar();
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
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("NewDriverDialog.fxml"));
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
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("NewClientDialog.fxml"));
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
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("NewRideDialog.fxml"));
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
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource("NewDriverDialog.fxml"));
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

    public static void showCalendarView(String view) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaxiApplication.class.getClassLoader().getResource(view));
            AnchorPane cView = loader.load();
            Controller controller = loader.getController();
            controller.setRootLayoutController(rootLayoutController);
            rootLayout.setCenter(cView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}