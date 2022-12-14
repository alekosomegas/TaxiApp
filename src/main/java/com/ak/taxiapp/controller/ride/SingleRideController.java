package com.ak.taxiapp.controller.ride;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.car.Car;
import com.ak.taxiapp.model.car.CarDAO;
import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.client.ClientDAO;
import com.ak.taxiapp.model.driver.Driver;
import com.ak.taxiapp.model.driver.DriverDAO;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.invoice.InvoiceDAO;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.util.AutoCompleteTextField;
import com.ak.taxiapp.util.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.controlsfx.control.SearchableComboBox;
import org.w3c.dom.Text;


import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;


public class SingleRideController extends Controller implements Initializable {
    public DatePicker dtDate;
    public TextField tfTimeStartH;
    public TextField tfTimeStartM;
    public TextField tfTimeEndH;
    public TextField tfTimeEndM;
    public Button tabOverview;
    public ToggleButton tgRoundTrip;
    public TextField tfFrom;
    public TextField tfTo;
    public VBox vbItinerary;
    public VBox vbStops;
    public SearchableComboBox<String> searchCbClients;
    public SearchableComboBox<String> searchCbDrivers;
    public SearchableComboBox<String> searchCbCars;
    public VBox vbPassenger;
    public VBox vbClient;
    public TextField tfDistance;
    public TextArea taNotes;
    public TextField tfCash;
    public TextField tfCredit;
    public Label lblTotal;
    public TextField tfVAT;
    public Label lblVAT;
    public HBox hbContent;
    private LocalDate date;

    private HashMap<String, String> values = new HashMap<>();


    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date = LocalDate.now();
        dtDate.setValue(date);
        dtDate.setShowWeekNumbers(false);

        tfTimeStartH.addEventFilter(KeyEvent.ANY, eventHandlerHour);
        tfTimeEndH.addEventFilter(KeyEvent.ANY, eventHandlerHour);
        tfTimeStartM.addEventFilter(KeyEvent.ANY, eventHandlerMinutes);
        tfTimeEndM.addEventFilter(KeyEvent.ANY, eventHandlerMinutes);

        tfFrom.addEventFilter(KeyEvent.ANY, eventHandlerFrom);

        tfCash.addEventFilter(KeyEvent.ANY, eventHandlerPrice);
        tfCredit.addEventFilter(KeyEvent.ANY, eventHandlerPrice);

        addListeners();


        vbStops.getChildren().add(new IteneraryStop(this));

        try {
            initClientCb();
            initDriverCb();
            initCarCb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        AutoCompleteTextField autoTfPassenger = new AutoCompleteTextField();
        autoTfPassenger.setId("tfPassenger");
        autoTfPassenger.getEntries().addAll((Arrays.asList("Tychon", "Alex", "Bob")));
        vbPassenger.getChildren().add(autoTfPassenger);

    }

    private void initClientCb() throws SQLException {
        ObservableList<String> clientIds = FXCollections.observableArrayList();
        ObservableList<Client> clientsList = ClientDAO.searchAllClients();
        for (Client client : clientsList) {
            clientIds.add(client.getClient_id() + ". " + client.getClient_name());
        }
        searchCbClients.setItems(clientIds);
    }

    private void initDriverCb() throws SQLException {
        ObservableList<String> driversIds = FXCollections.observableArrayList();
        ObservableList<Driver> driversList = DriverDAO.searchDrivers();
        for (Driver driver : driversList) {
            driversIds.add(driver.getDriver_id() + ". " + driver.getDriver_name());
        }
        searchCbDrivers.setItems(driversIds);
    }

    private void initCarCb() throws SQLException {
        ObservableList<String> carsIds = FXCollections.observableArrayList();
        ObservableList<Car> carsList = CarDAO.searchCars();
        for (Car car : carsList) {
            carsIds.add(car.getCar_id() + ". " + car.getCar_reg());
        }
        searchCbCars.setItems(carsIds);
    }

    public void onPrevDay(ActionEvent event) {
        date = date.minusDays(1);
        dtDate.setValue(date);
    }

    public void onNextDay(ActionEvent event) {
        date = date.plusDays(1);
        dtDate.setValue(date);
    }

    public void btnStartIncH(ActionEvent event) {
        if (!tfTimeStartH.isFocused()) {
            tfTimeStartH.requestFocus();
        }
        String input = tfTimeStartH.getText();
        if (Objects.equals(input, "")) {
            input = "01";
        } else {
            input = String.valueOf((Integer.parseInt(input) +1) % 24);
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeStartH.setText(input);

        if(Objects.equals(tfTimeStartM.getText(), "")) {
            tfTimeStartM.setText("00");
        }
    }

    public void btnStartDecH(ActionEvent event) {
        if (!tfTimeStartH.isFocused()) {
            tfTimeStartH.requestFocus();
        }
        String input = tfTimeStartH.getText();
        if (Objects.equals(input, "") || Objects.equals(input, "00")) {
            input = "23";
        } else {
            input = String.valueOf((Math.abs(Integer.parseInt(input) -1) % 24));
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeStartH.setText(input);
        if(Objects.equals(tfTimeStartM.getText(), "")) {
            tfTimeStartM.setText("00");
        }
    }

    public void btnStartIncM(ActionEvent event) {
        if (!tfTimeStartM.isFocused()) {
            tfTimeStartM.requestFocus();
        }
        String input = tfTimeStartM.getText();
        if (Objects.equals(input, "")) {
            input = "05";
        } else {
            int min = Integer.parseInt(input);
            if (min % 5 == 0) {
                input = String.valueOf((min+5)%60);
            } else {
                input = String.valueOf((int) (5.0 * (Math.ceil(min / 5.0))));
            }
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeStartM.setText(input);
        if(Objects.equals(tfTimeStartH.getText(), "")) {
            tfTimeStartH.setText("00");
        }
    }

    public void btnStartDecM(ActionEvent event) {
        if (!tfTimeStartM.isFocused()) {
            tfTimeStartM.requestFocus();
        }
        String input = tfTimeStartM.getText();
        if (Objects.equals(input, "")|| Objects.equals(input, "00")) {
            input = "55";
        } else {
            int min = Integer.parseInt(input);
            if (min % 5 == 0) {
                input = String.valueOf(Math.abs(min-5)%60);
            } else {
                input = String.valueOf((int) (5.0 * (Math.floor(min / 5.0))));
            }
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeStartM.setText(input);
        if(Objects.equals(tfTimeStartH.getText(), "")) {
            tfTimeStartH.setText("00");
        }
    }

    public void btnEndIncH(ActionEvent event) {
        if (!tfTimeEndH.isFocused()) {
            tfTimeEndH.requestFocus();
        }
        String input = tfTimeEndH.getText();
        if (Objects.equals(input, "")) {
            input = "01";
        } else {
            input = String.valueOf((Integer.parseInt(input) +1) % 24);
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeEndH.setText(input);
        if(Objects.equals(tfTimeEndM.getText(), "")) {
            tfTimeEndM.setText("00");
        }
    }

    public void btnEndDecH(ActionEvent event) {
        if (!tfTimeEndH.isFocused()) {
            tfTimeEndH.requestFocus();
        }
        String input = tfTimeEndH.getText();
        if (Objects.equals(input, "") || Objects.equals(input, "00")) {
            input = "23";
        } else {
            input = String.valueOf((Math.abs(Integer.parseInt(input) -1) % 24));
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeEndH.setText(input);
        if(Objects.equals(tfTimeEndM.getText(), "")) {
            tfTimeEndM.setText("00");
        }
    }

    public void btnEndIncM(ActionEvent event) {
        if (!tfTimeEndM.isFocused()) {
            tfTimeEndM.requestFocus();
        }
        String input = tfTimeEndM.getText();
        if (Objects.equals(input, "")) {
            input = "05";
        } else {
            int min = Integer.parseInt(input);
            if (min % 5 == 0) {
                input = String.valueOf((min+5)%60);
            } else {
                input = String.valueOf((int) (5.0 * (Math.ceil(min / 5.0))));
            }
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeEndM.setText(input);
        if(Objects.equals(tfTimeEndH.getText(), "")) {
            tfTimeEndH.setText("00");
        }
    }

    public void btnEndDecM(ActionEvent event) {
        if (!tfTimeEndM.isFocused()) {
            tfTimeEndM.requestFocus();
        }
        String input = tfTimeEndM.getText();
        if (Objects.equals(input, "") || Objects.equals(input, "00")) {
            input = "55";
        } else {
            int min = Integer.parseInt(input);
            if (min % 5 == 0) {
                input = String.valueOf(Math.abs(min-5)%60);
            } else {
                input = String.valueOf((int) (5.0 * (Math.floor(min / 5.0))));
            }
            if(input.length()==1) {
                input = "0" + input;
            }
        }
        tfTimeEndM.setText(input);
        if(Objects.equals(tfTimeEndH.getText(), "")) {
            tfTimeEndH.setText("00");
        }
    }

    //TODO: take the focused property listener out
    EventHandler<KeyEvent> eventHandlerHour = new EventHandler<>() {
        String input = "";
        @Override
        public void handle(KeyEvent event) {
            TextField textField = (TextField) event.getSource();

            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {

                    if (aBoolean) {
                        if(textField.getText().length() == 1){
                            input = "0"+textField.getText();
                            textField.setText(input);
                        }
                        if(textField.getText().equals("")) {
                            textField.getStyleClass().add("text-field--error");
                        } else {
                            textField.getStyleClass().remove("text-field--error");
                        }
                    }
                }
            });

            if(event.getEventType().toString().equals("KEY_TYPED")) {
                event.consume();
                char    charTyped = event.getCharacter().charAt(0);

                if(tfTimeStartH.isFocused()) {
                    if(Objects.equals(tfTimeStartM.getText(), "")) {
                        tfTimeStartM.setText("00");
                    }
                }else {
                    if(Objects.equals(tfTimeEndM.getText(), "")) {
                        tfTimeEndM.setText("00");
                    }
                }

                if (input.length() == 1) {
                    if (Character.isDigit(charTyped)) {
                        if (Integer.parseInt(input) != 2) {
                            input = input + charTyped;
                            textField.setText(input);
                        } else {
                            if (Arrays.asList("0", "1", "2", "3").contains(String.valueOf(charTyped))) {
                                input = input + charTyped;
                                textField.setText(input);
                            }
                        }
                    }
                } else {
                    input = "";
                    if (Arrays.asList("0", "1", "2").contains(String.valueOf(charTyped))) {
                        input = (String.valueOf(charTyped));
                        textField.setText(input);
                    } else {
                        if(Character.isDigit(charTyped)) {
                            input = "0" + String.valueOf(charTyped);
                            textField.setText(input);
                        }
                    }
                }
            }
        }
    };
    EventHandler<KeyEvent> eventHandlerMinutes = new EventHandler<>() {
        String input = "";
        @Override
        public void handle(KeyEvent event) {
            TextField textField = (TextField) event.getSource();

            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (aBoolean) {
                        if(textField.getText().length() == 1){
                            input = "0"+textField.getText();
                            textField.setText(input);
                        }
                        if(textField.getText().equals("")) {
                            textField.getStyleClass().add("text-field--error");
                        } else {
                            textField.getStyleClass().remove("text-field--error");
                        }
                    }
                }
            });

            if(event.getEventType().toString().equals("KEY_TYPED")) {
                event.consume();
                char    charTyped = event.getCharacter().charAt(0);

                if (input.length() == 1 && Character.isDigit(charTyped)) {
                    input = input + charTyped;
                    textField.setText(input);
                } else {
                    input = "";
                    if (Arrays.asList("0", "1", "2", "3", "4", "5").contains(String.valueOf(charTyped))) {
                        input = (String.valueOf(charTyped));
                        textField.setText(input);
                    } else {
                        if(Character.isDigit(charTyped)) {
                            input = "0" + String.valueOf(charTyped);
                            textField.setText(input);
                        }
                    }
                }
            }
        }
    };
    EventHandler<KeyEvent> eventHandlerFrom = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (tgRoundTrip.isSelected()) {
                tfTo.setText(tfFrom.getText());
            }
        }
    };

    public void addListeners() {
        tfCredit.setOnMouseClicked(e -> tfCredit.selectAll());
        tfCash.setOnMouseClicked(e -> tfCash.selectAll());

        tfCredit.focusedProperty().addListener(new ChangeListener<Boolean>() {
            HBox hBox = (HBox) tfCredit.getParent();
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                // If out of focus and empty text hide euro sign
                if (!t1 && tfCredit.getText().equals("")) {
                    hBox.getChildren().get(0).setVisible(false);
                    if(tfCash.getText().equals("")) {
                        lblVAT.setText("VAT");
                        tfVAT.setText("");
                    }
                }
            }
        });

        tfCash.focusedProperty().addListener(new ChangeListener<Boolean>() {
            HBox hBox = (HBox) tfCash.getParent();
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                // If out of focus and empty text hide euro sign
                if (!t1 && tfCash.getText().equals("")) {
                    hBox.getChildren().get(0).setVisible(false);

                    if(tfCredit.getText().equals("")) {
                        lblVAT.setText("VAT");
                        tfVAT.setText("");
                    }
                }
            }
        });
    }



    EventHandler<KeyEvent> eventHandlerPrice = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            TextField textField = (TextField) event.getSource();
            HBox hBox = (HBox) textField.getParent();

            if(event.getEventType().equals(KeyEvent.KEY_TYPED)) {
                char    charTyped = event.getCharacter().charAt(0);
                if(!Character.isDigit(charTyped)) {
                    event.consume();
                } else {
                    hBox.getChildren().get(0).setVisible(true);
                }
            }
            calculateTotal();
            calculateVAT();
        }

    };

//    EventHandler<KeyEvent>

    public void calculateTotal() {
        int cash = 0;
        int credit = 0;
        if(!tfCash.getText().equals("")) {
            cash = Integer.parseInt(tfCash.getText());
        }
        if(!tfCredit.getText().equals("")) {
            credit = Integer.parseInt(tfCredit.getText());
        }
        lblTotal.setText(String.valueOf(cash+ credit));
    }

    public void calculateVAT() {
        boolean cashOnly = false;
        DecimalFormat f = new DecimalFormat("0.00");
        int cash = 0;
        int credit = 0;
        if(!tfCash.getText().equals("")) {
            cash = Integer.parseInt(tfCash.getText());
        }
        if(!tfCredit.getText().equals("")) {
            credit = Integer.parseInt(tfCredit.getText());
        } else {cashOnly = true;}
        if(cashOnly) {
            lblVAT.setText("VAT inc.");
            tfVAT.setText(f.format(((double) cash * 9) / 109));
        } else {
            tfVAT.setText(f.format(((double) credit * 9) / 100));
            lblVAT.setText("VAT add.");
        }
    }
    public void onRoundTrip(ActionEvent event) {
        if(tgRoundTrip.isSelected()) {
            tgRoundTrip.setText("Round Trip");

            if(!Objects.equals(tfFrom.getText(), "")) {
                tfTo.setText(tfFrom.getText());
            }

        } else {
            tgRoundTrip.setText("One Way");
        }
    }

    public void onAddStop(ActionEvent event) {
        vbStops.getChildren().add(new IteneraryStop(this));
    }

    public void removeStop(IteneraryStop stop) {
        vbStops.getChildren().remove(stop);
        stop = null;
    }

    /**
     * Goes through all the children of the stops' container
     * calls their controller and separates
     * the stop values with a ' ; '
     */
    public String getStops() {
        StringBuilder stops = new StringBuilder();
        for (int i = 0; i < vbStops.getChildren().size(); i++) {
            IteneraryStop iteneraryStop = (IteneraryStop) vbStops.getChildren().get(i);
            stops.append(iteneraryStop.getStop());
            if(i != vbStops.getChildren().size()-1) {
                stops.append(" ; ");
            }
        }
        return String.valueOf(stops);
    }

    private boolean isPane(Node node) {
        // if it is not pane class
        return node instanceof Pane;
    }

    private boolean isTextField(Node node) {
        return node instanceof TextInputControl;
    }
    private boolean isCombo(Node node) {
        return node instanceof SearchableComboBox;
    }

    public void traverse(Pane pane, int mode) {
        for (Node node : pane.getChildren()) {

            if(isPane(node)) {
                Pane child = (Pane) node;
                traverse(child, mode);
            } else if (isTextField(node)) {
                TextInputControl textField = (TextInputControl) node;
                if (mode == 1) {
                    textField.setText("");
                } else {
                    values.put(textField.getId(), textField.getText());
                }
            } else if (isCombo(node)) {
                SearchableComboBox searchableComboBox = (SearchableComboBox) node;
                if (mode == 1) {
                    searchableComboBox.setValue(null);
                } else {
                    if (searchableComboBox.getValue() != null) {
                        values.put(searchableComboBox.getId(),
                                searchableComboBox.getValue().toString().split("\\.")[0]);
                    } else {
                        values.put(searchableComboBox.getId(),
                                "");
                    }

                }
            }
        }
    }


    public void onClear(ActionEvent event) {
        for (Node node : hbContent.getChildren()) {
            Pane pane = (Pane) node;
            traverse(pane, 1);
        }

        tfFrom.setText("");
        tfTo.setText("");
        vbStops.getChildren().clear();
        lblTotal.setText("");
        dtDate.setValue(LocalDate.now());

        if (tgRoundTrip.isSelected()) {
            vbStops.getChildren().add(new IteneraryStop(this));
        }
    }

    public void onOK(ActionEvent event) throws SQLException {
        for (Node node : hbContent.getChildren()) {
            Pane pane = (Pane) node;
            traverse(pane, 2);
        }

        values.put("tfFrom", tfFrom.getText());
        values.put("tfTo", tfTo.getText());
        values.put("tfStops", getStops());
        values.put("dtDate", dtDate.getValue().toString());

        // 0 id is unknown
        if(values.get("searchCbClients").equals("")) {
            values.put("searchCbClients", "0");
        }
        if(values.get("searchCbDrivers").equals("")) {
            values.put("searchCbDrivers", "0");
        }
        if(values.get("searchCbCars").equals("")) {
            values.put("searchCbCars", "0");
        }
        if(values.get("tfCash").equals("")) {
            values.put("tfCash", "0");
        }
        if(values.get("tfCredit").equals("")) {
            values.put("tfCredit", "0");
        }



        if(belongsToInvoice()) {
            // find invoice id
            // search for all open invoices of client, no need to add since it is already tagged
            // TODO: handle multiple open invoices
            // TODO: generate id
            // TODO: stat number for id
            ObservableList<Invoice> openInvoices = InvoiceDAO.searchByClientIdAndStatus(getClientId(), Invoice.Status.OPEN);
            ObservableList<Invoice> totalInvoices = InvoiceDAO.searchByClientId(getClientId());

            String rest;
            String id = getClientId() + "/";
            if(openInvoices.size() == 0) {
                // create new invoice
                Invoice newInvoice = new Invoice(ClientDAO.searchClientById(getClientId()));
                newInvoice.setStatus(Invoice.Status.OPEN);
                // last + one
                rest = String.format("%03d", totalInvoices.size()+1);
                id = id + rest;
                newInvoice.setId(id);

                InvoiceDAO.insert(newInvoice);


            } else {
                // last one
                rest = String.format("%03d", totalInvoices.size());
                id = id + rest;
            }

            values.put("id", id);

            // add if exist
            // create new with id from Invoice.generateId()
        }

        RideDAO.insert(values);

    }

    public void onCancel(ActionEvent event) {
    }

    //TODO: disable credit field, enable only if client is selected
    public boolean belongsToInvoice() {
        // If credit and client exist
        if (!tfCredit.getText().equals("")  &&
            !searchCbClients.getValue().equals("")) {
            return true;
        }
        return false;
    }

    private void validateInputs() {

    }

    private String getClientId() {
        return searchCbClients.getValue().split("\\.")[0];
    }


    public void onTabList(ActionEvent event) {
        TaxiApplication.showRidesView();
    }

    public void populateData(Ride ride) {
        dtDate.setValue(ride.getDate());
        tfTimeStartH.setText(ride.getRidesTimeStart().split(":")[0]);
        tfTimeStartM.setText(ride.getRidesTimeStart().split(":")[1]);
        tfTimeEndH.setText(ride.getRidesTimeEnd().split(":")[0]);
        tfTimeEndM.setText(ride.getRidesTimeEnd().split(":")[1]);
        tfFrom.setText(ride.getRidesFrom());
        tfTo.setText(ride.getRidesTo());
        taNotes.setText(ride.getRidesNotes());
        tfCash.setText(String.valueOf(ride.getRidesCash()));
        tfCredit.setText(String.valueOf(ride.getRidesCredit()));
        searchCbClients.setValue(ride.getRidesClientId() +". "+ ride.getRidesClient());
        searchCbDrivers.setValue(ride.getRidesDriverId() +". "+ ride.getRidesDriver());
        searchCbCars.setValue(ride.getRidesCarId() +". "+ ride.getRidesCar());

        calculateTotal();
        calculateVAT();

    }
}
