package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.Formatter;
import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.invoice.InvoiceDAO;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.util.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SingleInvoiceController extends Controller {
    public TableView<Ride> tvInvoiceRidesTable;
    public TableColumn<Ride, String> tcInvoiceRideDate;
    public TableColumn<Ride, String> tcInvoiceRidePassenger;
    public TableColumn<Ride, String> tcInvoiceRideItinerary;
    public TableColumn<Ride, Integer> tcInvoiceRidePrice;
    public TableColumn<Ride, String> tcInvoiceRideNotes;
    public TableColumn<Ride, String> tcInvoiceRideEdit;
    public TableColumn<Ride, String> tcInvoiceRideRemove;
    public TextField tfInvoiceNumber;
    public TextField tfClient;
    public TextField tfFromDate;
    public TextField tfTillDate;
    public TextArea taNotes;
    public Label lblTotal;

    public ToggleGroup tgStatus;
    public ToggleButton tgOpen;
    public ToggleButton tgClosed;
    public ToggleButton tgIssued;
    public ToggleButton tgPaid;
    public ToggleButton selectedToggleButton;



    public DatePicker dtDate;


    private HashMap<InvoiceDAO.DBFields, String> values = new HashMap<>();

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

    // TODO: no need of constructor
    /**
     * Creates a Controller for the single invoice view without a given Invoice number.
     * To be used at programme lunch and when creating a new invoice
     */
    public SingleInvoiceController() {
    }

    /**
     * Creates a Controller for the single invoice view without a given Invoice number.
     * To be used at programme lunch and when creating a new invoice
     */
    public SingleInvoiceController(Invoice invoiceNumber) throws SQLException {
//        populateData(invoiceNumber);
    }

    // TODO: is the factory needed at this point?
    private void initialize() {
        tcInvoiceRideDate.setCellValueFactory(ride -> ride.getValue().ridesDateProperty());
        tcInvoiceRidePassenger.setCellValueFactory(ride -> ride.getValue().ridesPassengerProperty());
        tcInvoiceRideItinerary.setCellValueFactory(ride -> ride.getValue().ridesFromProperty());
        tcInvoiceRidePrice.setCellValueFactory(ride -> ride.getValue().ridesTotalProperty().asObject());
        tcInvoiceRideNotes.setCellValueFactory(ride -> ride.getValue().ridesNotesProperty());
    }

    /**
     * Called from the InvoiceRow -> TaxiApplication showInvoiceEditView
     * Gets the invoice id from the row object and uses it to search for all the relevant
     * data and populate the fields in the view
     */
    public void populateData(Invoice invoice) {
        tfInvoiceNumber.setText(invoice.getId());
        tfClient.setText(invoice.getClientName());
        taNotes.setText(invoice.getNotes());
        lblTotal.setText(String.valueOf(invoice.getTotal()));
        if(invoice.getDate() != null) {
            dtDate.setValue(CalendarModel.convertDate(invoice.getDate()));
        }
        tfFromDate.setText(Formatter.format("dd MMM yyyy", invoice.getStartDate()));
        tfTillDate.setText(Formatter.format("dd MMM yyyy", invoice.getEndDate()));

        switch (invoice.getStatus()) {
            case OPEN -> {
                selectedToggleButton = tgOpen;
                tgStatus.selectToggle(tgOpen);
            }
            case PAID -> {
                selectedToggleButton = tgPaid;
                tgStatus.selectToggle(tgPaid);
            }
            case CLOSED -> {
                selectedToggleButton = tgClosed;
                tgStatus.selectToggle(tgClosed);
            }
            case ISSUED -> {
                selectedToggleButton = tgIssued;
                tgStatus.selectToggle(tgIssued);
            }
        }
        tvInvoiceRidesTable.setItems(invoice.getRides());
    }

    public void onNextDay(ActionEvent event) {
    }

    public void onPrevDay(ActionEvent event) {
    }

    public void onOK(ActionEvent event) {
        update();
        TaxiApplication.showInvoicesView();
    }

    public void onCancel(ActionEvent event) {
    }

    public void onClear(ActionEvent event) {
    }

    public void onTabOverview(ActionEvent event) {
        TaxiApplication.showInvoicesView();
    }

    public void onStatusChanged(ActionEvent event) {
        // prevents unselecting
        if (event.getSource() == selectedToggleButton)  {
            tgStatus.selectToggle(selectedToggleButton);
            selectedToggleButton.setSelected(true);
        }

        selectedToggleButton = (ToggleButton) tgStatus.getSelectedToggle();
        ToggleButton selected = selectedToggleButton;

        if (selected == tgOpen) {
            tgIssued.setDisable(true);
            tgIssued.setOpacity(0.5);
            tgPaid.setDisable(true);
            tgPaid.setOpacity(0.5);
            tgClosed.setOpacity(0.5);
        }
        if (selected == tgClosed) {
            tgClosed.setOpacity(1);
            tgIssued.setDisable(false);
            tgIssued.setOpacity(0.5);
            tgPaid.setDisable(true);
            tgPaid.setOpacity(0.5);
        }

        // allow to reopen after issue
        if (selected == tgIssued) {
            tgIssued.setOpacity(1);
            tgPaid.setDisable(false);
            tgPaid.setOpacity(0.5);
        }

        if (selected == tgPaid) {
            tgPaid.setOpacity(1);
        }
    }

    public void update() {
        values.put(InvoiceDAO.DBFields.INV_NO, tfInvoiceNumber.getText());
        values.put(InvoiceDAO.DBFields.DATE, String.valueOf(dtDate.getValue()));
        values.put(InvoiceDAO.DBFields.NOTES, taNotes.getText());
        ToggleButton selected = (ToggleButton) tgStatus.getSelectedToggle();
        values.put(InvoiceDAO.DBFields.STATUS, selected.getText().toUpperCase());

        InvoiceDAO.update(values);
    }
}
