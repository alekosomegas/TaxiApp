package com.ak.taxiapp.controller;

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
import java.util.HashMap;
import java.util.Objects;

public class NewRideDialogController {

    public TextField tfDuration;
    public ComboBox<String> cbClientId;
    public TextField tfFrom;
    public TextField tfTo;
    public TextField tfStops;
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
    public ComboBox cbStatus;
    public GridPane gpGridPane;
    public static int addStopCount;
    public static int totaladdStopCount;
    public Pane dpane;
    public TextField tfCash;
    public TextArea taNotes;
    public CheckBox cbClientPaid;
    public TextField tfPassenger;
    public Label lblTotal;
    public CheckBox cbDriverPaid;


    private HashMap<String,String> selectedRideValues;

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

    private void initStatusComboBox() throws SQLException {
        ObservableList<String> status = FXCollections.observableArrayList();
        status.add("Completed");
        status.add("Ongoing");
        status.add("Planned");

        cbStatus.setItems(status);
    }
    private int getStatus() {
        if (cbStatus.getValue() == "Completed") {return 1;}
        if (cbStatus.getValue() == "Ongoing") {return 2;}
        if (cbStatus.getValue() == "Planned") {return 3;}
        return 0;
    }

    private String getIdfromCB(ComboBox cb) {
        String re =  cb.getValue().toString().split("\\.")[0];
        System.out.println(re);
        return re;
    }

    @FXML public void initialize() throws SQLException {

        initTimePicker();
        initClintIdComboBox();
        initDriverIdComboBox();
        initCarIdComboBox();
        initStatusComboBox();

        dtDate.setValue(LocalDate.now());
        cbStatus.setValue("Planned");

        addStopCount = 0;
        totaladdStopCount = 0;
        ScrollBar s = new ScrollBar();

        s.setOrientation(Orientation.VERTICAL);
//        dpane.getChildren().add(s);

    }
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
                    getCash(),getStatus(),
                    getCredit(),1,taNotes.getText(), 1);
//            resultArea.setText("Client inserted! \n");

        } catch (SQLException e) {
//            resultArea.setText("Problem occurred while inserting client " + e);
            throw e;
        }
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
        return "";
    }

    public void setValues(HashMap<String, String> values) {
//        tfDate.setText(values.get("ridesDateCol"));
//        dtDate.setValue(LocalDate.parse(values.get("ridesDateCol")));
//        tfStart.setText(values.get("ridesStartCol"));
//        tfEnd.setText(values.get("ridesEndCol"));
//        tfDuration.setText(values.get("ridesDurationCol"));
        cbClientId.setValue(values.get("ridesClientIdCol") + ". " + values.get("ridesClientCol"));
        tfFrom.setText(values.get("ridesFromCol"));
        tfCash.setText(values.get("ridesCashCol"));
        cbDriverId.setValue(values.get("ridesDriverIdCol") + ". " + values.get("ridesDriverCol"));
        cbStatus.setValue(values.get("ridesStatusCol"));
        cbCarId.setValue(values.get("ridesCarIdCol") + ". " + values.get("ridesCarCol"));
        tfTo.setText(values.get("ridesToCol"));
//        tfStops.setText(values.get("ridesStopsCol"));
        tfCredit.setText(values.get("ridesCreditCol"));
        selectedRideValues = values;
    }

    @FXML public void update() throws SQLException {
        selectedRideValues.put("ridesDateCol",      dtDate.getValue().toString());
        selectedRideValues.put("ridesStartCol",     spStartH.getValue().toString());
        selectedRideValues.put("ridesEndCol",       spEndH.getValue().toString());
        selectedRideValues.put("ridesDurationCol",  tfDuration.getText());
        selectedRideValues.put("ridesClientIdCol",  cbClientId.getValue().split("\\.")[0]);
        selectedRideValues.put("ridesFromCol",      tfFrom.getText());
        selectedRideValues.put("ridesCashCol",      tfCash.getText());
        selectedRideValues.put("ridesDriverIdCol",  cbDriverId.getValue().toString().split("\\.")[0]);
        selectedRideValues.put("ridesStatusCol",    cbStatus.getValue().toString());
        selectedRideValues.put("ridesCarIdCol",     cbCarId.getValue().toString().split("\\.")[0]);
        selectedRideValues.put("ridesToCol",        tfTo.getText());
//        selectedRideValues.put("ridesStopsCol",     tfStops.getText());
        selectedRideValues.put("ridesCreditCol",    tfCredit.getText());
//        selectedRideValues.put("ridesPaidCol",      cbClientPaid.getText());
        RideDAO.update(selectedRideValues);
    }


    public void onAddStopClicked() {
        addStopCount++;
        totaladdStopCount++;
        TextField tf = new TextField();
        tf.setId(String.valueOf(totaladdStopCount));
        tf.setText(String.valueOf(addStopCount));
        VBox v = (VBox) hbItenerary.getChildren().get(0);

        dpane.getScene().getWindow().sizeToScene();

        v.getChildren().add(addStopCount, tf);

        Button rvbtn = new Button();
        rvbtn.setText("-");
        rvbtn.setId(String.valueOf(totaladdStopCount));
        rvbtn.setOnAction(event -> onRemoveStopClicked(rvbtn, tf));
        VBox v2 = (VBox) hbItenerary.getChildren().get(1);
        v2.getChildren().add(rvbtn);

    }

    public void onRemoveStopClicked(Button btn, TextField tf) {
        addStopCount--;
        VBox v = (VBox) hbItenerary.getChildren().get(0);
        VBox v2 = (VBox) hbItenerary.getChildren().get(1);
        v2.getChildren().remove(btn);
        v.getChildren().remove(tf);
    }

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
        if (!checkClient()) {
            setStyleClassToCeck(cbClientId.getParent());
            res = false;
        }
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
}
