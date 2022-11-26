package com.ak.taxiapp.controller;

// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

//endregion
// ------------------------------------------------------------------ //

public class NewRideDialogController {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    public TextField tfDuration;
    public ComboBox<String> cbClientId;
    public TextField tfFrom;
    public TextField tfTo;
    public String tfStops;
    private ArrayList<TextField> stopsTfList;
    public TextField tfCredit;
    public DatePicker dtDate;
    public Spinner spStartH;
    public Spinner spStartM;
    public Spinner spEndH;
    public Spinner spEndM;
    public ComboBox cbDriverId;
    public HBox hbItenerary;
    public Button btnAddStop;
    public ComboBox cbCarId;
    public GridPane gpGridPane;
    public static int addStopCount;
    public static int totalAddStopCount;
    public Pane dPane;
    public TextField tfCash;
    public TextArea taNotes;
    public TextField tfPassenger;
    public Label lblTotal;
    private HashMap<String,String> selectedRideValues;

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ---------------------------- INITIALIZE --------------------------- //

    @FXML public void initialize() throws SQLException {
        initTimePicker();
        initClintIdComboBox();
        initDriverIdComboBox();
        initCarIdComboBox();


        dtDate.setValue(LocalDate.now());

        addStopCount = 0;
        totalAddStopCount = 0;
        ScrollBar s = new ScrollBar();

        s.setOrientation(Orientation.VERTICAL);
        stopsTfList = new ArrayList<>();
    }

    private void initTimePicker() {
        SpinnerValueFactory<Integer> valueFactorySH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);
        SpinnerValueFactory<Integer> valueFactoryEH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);
        SpinnerValueFactory<Integer> valueFactorySM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,55,0,5);
        SpinnerValueFactory<Integer> valueFactoryEM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,55,0,5);
        spStartH.setValueFactory(valueFactorySH);
        spStartM.setValueFactory(valueFactorySM);
        spEndH.setValueFactory(valueFactoryEH);
        spEndM.setValueFactory(valueFactoryEM);
    }

    private void initClintIdComboBox() throws SQLException {
        ObservableList<String> clientIds = FXCollections.observableArrayList();
        ObservableList<Client> clientsList = ClientDAO.searchAllClients();
        for (Client client : clientsList) {
            clientIds.add(client.getClient_id() + ". " + client.getClient_name());
        }
        cbClientId.setItems(clientIds);
    }
    private void initDriverIdComboBox() throws SQLException {
        ObservableList<String> driversIds = FXCollections.observableArrayList();
        ObservableList<Driver> driversList = DriverDAO.searchDrivers();
        for (Driver driver : driversList) {
            driversIds.add(driver.getDriver_id() + ". " + driver.getDriver_name());
        }
        cbDriverId.setItems(driversIds);
    }

    private void initCarIdComboBox() throws SQLException {
        ObservableList<String> carsIds = FXCollections.observableArrayList();
        ObservableList<Car> carsList = CarDAO.searchCars();
        for (Car car : carsList) {
            carsIds.add(car.getCar_id() + ". " + car.getCar_reg());
        }
        cbCarId.setItems(carsIds);
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- SQL METHODS --------------------------- //

    @FXML
    public void insertRide() throws SQLException {

        try {
            RideDAO.insertRide(dtDate.getValue().toString(),
                    spStartH.getValue().toString() +":"+ spStartM.getValue().toString(),
                    spEndH.getValue().toString() +":"+ spEndM.getValue().toString(),
                    Integer.parseInt(getIdfromCB(cbClientId)),
                    Integer.parseInt(getIdfromCB(cbDriverId)),
                    Integer.parseInt(getIdfromCB(cbCarId)),
                    tfFrom.getText(),getStops(),tfTo.getText(),
                    getCash(), getCredit(),
                    taNotes.getText(), tfPassenger.getText());
//            resultArea.setText("Client inserted! \n");

        } catch (SQLException e) {
//            resultArea.setText("Problem occurred while inserting client " + e);
            throw e;
        }
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------ UPDATE METHODS -------------------------- //

    public void setValues(HashMap<String, String> values) {
        dtDate.setValue(LocalDate.parse(values.get("ridesDateCol")));
        spStartH.getValueFactory().setValue(Integer.parseInt(values.get("ridesStartCol").split(":")[0]));
        spStartM.getValueFactory().setValue(Integer.parseInt(values.get("ridesStartCol").split(":")[1]));
        spEndH.getValueFactory().setValue(Integer.parseInt(values.get("ridesEndCol").split(":")[0]));
        spEndM.getValueFactory().setValue(Integer.parseInt(values.get("ridesEndCol").split(":")[1]));
        tfDuration.setText(values.get("ridesDurationCol"));
        cbClientId.setValue(values.get("ridesClientIdCol") + ". " + values.get("ridesClientCol"));
        tfFrom.setText(values.get("ridesFromCol"));
        tfCash.setText(values.get("ridesCashCol"));
        cbDriverId.setValue(values.get("ridesDriverIdCol") + ". " + values.get("ridesDriverCol"));
        cbCarId.setValue(values.get("ridesCarIdCol") + ". " + values.get("ridesCarCol"));
        tfTo.setText(values.get("ridesToCol"));
        setStops(values);
        tfCredit.setText(values.get("ridesCreditCol"));
        tfPassenger.setText(values.get("ridesPassengerCol"));
        selectedRideValues = values;

    }

    @FXML public void update() throws SQLException {
        selectedRideValues.put("ridesDateCol",      dtDate.getValue().toString());
        putTimesValues();
        selectedRideValues.put("ridesDurationCol",  tfDuration.getText());
        selectedRideValues.put("ridesClientIdCol",  cbClientId.getValue().split("\\.")[0]);
        selectedRideValues.put("ridesFromCol",      tfFrom.getText());
        selectedRideValues.put("ridesCashCol",      String.valueOf(getCash()));
        selectedRideValues.put("ridesDriverIdCol",  cbDriverId.getValue().toString().split("\\.")[0]);
        selectedRideValues.put("ridesCarIdCol",     cbCarId.getValue().toString().split("\\.")[0]);
        selectedRideValues.put("ridesToCol",        tfTo.getText());
        getStops();
        selectedRideValues.put("ridesStopsCol",     tfStops);
        System.out.println(tfStops);
        selectedRideValues.put("ridesCreditCol",    String.valueOf(getCredit()));
        selectedRideValues.put("ridesPassengerCol", tfPassenger.getText());

        RideDAO.update(selectedRideValues);
    }

    // ------------------------------------------------------------------ //
    //TODO: add redundancy for time parsing and ":"
    /**
     * Gets the values from the 2 spinners for each time and creates a string with
     * a ':' separator.
     */
    private void putTimesValues() {
        selectedRideValues.put("ridesStartCol",
                spStartH.getValue().toString() +":"+
                spStartM.getValue().toString());
        selectedRideValues.put("ridesEndCol",
                spEndH.getValue().toString() +":"+
                spEndM.getValue().toString());
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- FXML METHODS -------------------------- //


    public TextField onAddStopClicked() {
        addStopCount++;
        totalAddStopCount++;
        TextField tf = new TextField();
        tf.setId(String.valueOf(totalAddStopCount));
        tf.setText(String.valueOf(addStopCount));
        VBox v = (VBox) hbItenerary.getChildren().get(0);

//        dPane.getScene().getWindow().sizeToScene();

        v.getChildren().add(addStopCount, tf);

        Button rvbtn = new Button();
        rvbtn.setText("-");
        rvbtn.setId(String.valueOf(totalAddStopCount));
        rvbtn.setOnAction(event -> onRemoveStopClicked(rvbtn, tf));
        VBox v2 = (VBox) hbItenerary.getChildren().get(1);
        v2.getChildren().add(rvbtn);

        stopsTfList.add(tf);
        return tf;
    }

    public void onRemoveStopClicked(Button btn, TextField tf) {
        addStopCount--;
        VBox v = (VBox) hbItenerary.getChildren().get(0);
        VBox v2 = (VBox) hbItenerary.getChildren().get(1);
        v2.getChildren().remove(btn);
        v.getChildren().remove(tf);

        stopsTfList.remove(tf);
    }

    public void onPriceChanged() {
        int cash = 0;
        int credit = 0;
        if(!isNumeric(tfCredit.getText()) || !isNumeric(tfCash.getText())) {
            lblTotal.setText(" ");
            return;
        }
        try {
            credit = Integer.parseInt(tfCredit.getText());
        } catch (NumberFormatException e) {
            tfCredit.setText("");
            lblTotal.setText(" ");
//            setStyleClassToCeck(tfCredit);
        }
        try {
            cash = Integer.parseInt(tfCash.getText());
        } catch (NumberFormatException e) {
            tfCash.clear();
            lblTotal.setText(" ");
//            setStyleClassToCeck(tfCash);
        }

        lblTotal.setText(String.valueOf(cash + credit));
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- INPUT VALIDATION ----------------------- //

    private void setStyleClassToCeck(Node n) {
        n.getStyleClass().clear();
        n.getStyleClass().add("check");
    }
    private boolean checkIten() {
        if (Objects.equals(tfFrom.getText(), "From") ||
            Objects.equals(tfFrom.getText(), "") ||
            Objects.equals(tfTo.getText(), "To") ||
            Objects.equals(tfTo.getText(), "")) {
            return false;
        } return true;
    }
    private boolean checkClient() {
//        return cbClientId.getValue() != null;
        return true;
    }
    private boolean checkDriver() {
        return cbDriverId.getValue() != null;
    }
    private boolean checkCar() {
        return cbCarId.getValue() != null;
    }


    public boolean checkInputs() {
        boolean res = true;
        if(!checkIten()) {
            setStyleClassToCeck(hbItenerary);
            res = false;
        }
//        if (!checkClient()) {
//            setStyleClassToCeck(cbClientId.getParent());
//            res = false;
//        }
        if (!checkDriver()) {
            setStyleClassToCeck(cbDriverId.getParent());
            res = false;
        }
        if (!checkCar()) {
            setStyleClassToCeck(cbCarId.getParent());
            res = false;
        }
        onPriceChanged();

        return res;
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    private boolean isNumeric(String st) {
        if(st.isBlank()) {
            return true;
        }
        try {
            int i = Integer.parseInt(st);
        } catch (NumberFormatException e) {
            return false;
        } return true;
    }


    private int getCash() {
        try {
            return Integer.parseInt(tfCash.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private int getCredit() {
        try {
            return Integer.parseInt(tfCredit.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getStops() {
        tfStops = "";
        for (TextField tf :
                stopsTfList) {
            tfStops = tfStops + tf.getText() + " ; ";
        }
        System.out.println(tfStops);
        return tfStops;
    }

    private void setStops(HashMap<String, String> values) {
        tfStops = values.get("ridesStopsCol");
        String[] stops = tfStops.split(" ; ");
        for (String stop :
                stops) {
            onAddStopClicked().setText(stop);
        }

    }

    private String getIdfromCB(ComboBox cb) {
        return cb.getValue().toString().split("\\.")[0];
    }

    //endregion
    // ------------------------------------------------------------------ //

}
