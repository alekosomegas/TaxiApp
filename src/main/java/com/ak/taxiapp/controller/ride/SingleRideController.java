package com.ak.taxiapp.controller.ride;

import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.client.ClientDAO;
import com.ak.taxiapp.util.AutoCompleteTextField;
import com.ak.taxiapp.util.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.textfield.TextFields;


import java.net.URL;
import java.sql.SQLException;
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
    public VBox vbPassenger;
    public VBox vbClient;
    private LocalDate date;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date = LocalDate.now();
        dtDate.setValue(date);
        dtDate.setShowWeekNumbers(false);

        tfTimeStartH.addEventFilter(KeyEvent.ANY, eventHandlerHour);
        tfTimeEndH.addEventFilter(KeyEvent.ANY, eventHandlerHour);
        tfTimeStartM.addEventFilter(KeyEvent.ANY, eventHandlerMinutes);
        tfTimeEndM.addEventFilter(KeyEvent.ANY, eventHandlerMinutes);

        tfFrom.addEventFilter(KeyEvent.ANY, eventHandlerFrom);

        vbStops.getChildren().add(new IteneraryStop(this));


        ObservableList<String> clientIds = FXCollections.observableArrayList();
        ObservableList<Client> clientsList = null;


        try {
            clientsList = ClientDAO.searchAllClients();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Client client : clientsList) {
            clientIds.add(client.getClient_id() + ". " + client.getClient_name());
        }
        searchCbClients.setItems(clientIds);

        AutoCompleteTextField autoTfPassenger = new AutoCompleteTextField();

        autoTfPassenger.getEntries().addAll((Arrays.asList("Tychon", "Alex", "Bob")));
        vbPassenger.getChildren().add(autoTfPassenger);

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
     * the stop values with a ' /'
     */
    public String getStops() {
        StringBuilder stops = new StringBuilder();
        for (int i = 0; i < vbStops.getChildren().size(); i++) {
            IteneraryStop iteneraryStop = (IteneraryStop) vbStops.getChildren().get(i);
            stops.append(iteneraryStop.getStop());
            stops.append(" /");
        }
        return String.valueOf(stops);
    }
}
