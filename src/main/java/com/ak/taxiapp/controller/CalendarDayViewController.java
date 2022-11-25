package com.ak.taxiapp.controller;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.Ride;
import com.ak.taxiapp.model.RideDAO;
import com.ak.taxiapp.model.calendar.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

// endregion
// ------------------------------------------------------------------ //

//TODO:TIDY
public class CalendarDayViewController extends Controller {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    public Button btnDayView;
    public Button btnWeekView;
    public Button btnMonthView;
    public Label lblSelectedDay;
    public TreeView<String> tvRidesByDriver;
    public HBox hbBoxArea;
    public GridPane gpMonth;
    public Label lblMonth;
    public Label lblYear;
    public HBox hbDayBtns;
    private final Calendar calendar = new GregorianCalendar();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
    public StackPane spDayView;
    public HBox hbDayView;
    public Pane pnLines;
    public Pane pnTimes;
    private CalendarModel calendarModel;
    private DayView dayView;
    private MiniMonthCalendar miniMonthCalendar;
    private WeekDisplay weekDisplay;

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //



    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// --------------------------- INITIALISE --------------------------- //

    /**
     * Creates new instances of all the Calendar Components, calls their built method
     * and adds them to the corresponding FXML Nodes
     */
    @FXML
    public void initialize() throws SQLException {
        // Start by creating instances of components
        loadComponents();
        // Then build them by calling their build function
        buildComponents();
        // Use the FXML variables to display components
        displayComponents();
        // set the date label to today
        updateSelectedDateLabel(calendar.getTime());

        //TODO: refactor
        initTreeView();
//        initBoxArea();

    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- INIT METHODS --------------------------- //

    /**
     * Creates the instances of the components assign them to global variables.
     */
    private void loadComponents() {
        this.calendarModel = new CalendarModel();
        // Create new instances;
        this.dayView = new DayView(calendarModel, this);
        this.miniMonthCalendar = new MiniMonthCalendar(calendarModel, this);
        this.weekDisplay = new WeekDisplay(calendarModel, this);
    }

    // ------------------------------------------------------------------ //
    /**
     * Uses the components built function
     */
    private void buildComponents() {
        dayView.built();
        miniMonthCalendar.built();
        weekDisplay.built();
    }

    // ------------------------------------------------------------------ //
    /**
     * Uses the values from each component
     * and FXML to display them
     */
    private void displayComponents() {
        for (MonthLabel monthLabel : miniMonthCalendar.getMonthLabelAList()) {
            gpMonth.add(monthLabel.getLabel(), monthLabel.getColumn(), monthLabel.getRow());
        }
        for (WeekButton toggleButton : weekDisplay.getWeekButtonsAList()) {
            hbDayBtns.getChildren().add(toggleButton.getToggleButton());
        }
    }

    // ------------------------------------------------------------------ //
    private void initTreeView() {
        TreeItem<String> rootNode =
                new TreeItem<>("Rides By Driver");
        rootNode.setExpanded(true);
        tvRidesByDriver.setRoot(rootNode);

        for (Ride ride : Objects.requireNonNull(getRidesByDate())) {
            boolean found = false;
            TreeItem<String> leaf =
                    new TreeItem<>(ride.getRidesFrom() +" - "+ ride.getRidesTo());

            for (TreeItem<String> driverNode : rootNode.getChildren()) {
                if (driverNode.getValue().contentEquals(ride.getRidesDriver())){
                    driverNode.getChildren().add(leaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> driverNode = new TreeItem<>(
                        ride.getRidesDriver());
                rootNode.getChildren().add(driverNode);
                driverNode.getChildren().add(leaf);
            }
        }
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- UPDATE METHODS ------------------------- //

    /**
     *
     */
    public void updateSelectedDateLabel(Date date) {
        lblSelectedDay.setText(dateFormat.format(date));

        //TODO: find a better way
        updateDayDisplay();
    }

    // ------------------------------------------------------------------ //
    /**
     * Sets the displayedCalendar date to selected date form the calendar and
     * updates the mini calendar display
     */
    public void updateMiniCalendar() {
        miniMonthCalendar.displayedCalendar.setSelectedDate(
                CalendarModel.convertDate(calendarModel.getCalendar().getTime()));
        miniMonthCalendar.update();
        miniMonthCalendar.highlightSelectedWeek();
    }

    // ------------------------------------------------------------------ //
    public void updateWeekDisplay() {
        weekDisplay.update();
        weekDisplay.changeDateSelectionDisplay();
    }

    public void updateDayDisplay() {
        this.dayView = new DayView(calendarModel, this);
        dayView.built();
    }
    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    private ObservableList<Ride> getRidesByDate(){
        try {
            return RideDAO.searchAllRides();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- FXML METHODS -------------------------- //

    /**
     * Sets the selected date in the calendar to today and
     * updates the mini Calendar and Week display and the date label
     */
    public void onBtnTodayClicked() {
        // set selected date to today
        calendarModel.setSelectedDate(calendarModel.getTodayDate());
        // update the selected day in the mini calendar
        updateMiniCalendar();
        weekDisplay.update();
        weekDisplay.changeDateSelectionDisplay();
        updateSelectedDateLabel(calendar.getTime());
    }

    // ------------------------------------------------------------------ //
    public void onbtnDayClicked() {
    }

    // ------------------------------------------------------------------ //
    public void onbtnWeekClicked() {
        TaxiApplication.showCalendarView("CalendarWeekView.fxml");
    }

    // ------------------------------------------------------------------ //
    public void onbtnMonthClicked() {
    }

    // ------------------------------------------------------------------ //
    public void onPrevWeekClicked() {
        weekDisplay.changeWeek(-7);
    }

    // ------------------------------------------------------------------ //
    public void onNextWeekClicked() {
        weekDisplay.changeWeek(7);
    }

    // ------------------------------------------------------------------ //
    public void onNextYear() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.YEAR, 1);
        miniMonthCalendar.update();
    }

    // ------------------------------------------------------------------ //
    public void onPrevYear() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.YEAR, -1);
        miniMonthCalendar.update();
    }

    // ------------------------------------------------------------------ //
    public void onNextMonth() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.MONTH, 1);
        miniMonthCalendar.update();
    }

    // ------------------------------------------------------------------ //
    public void onPrevMonth() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.MONTH, -1);
        miniMonthCalendar.update();
    }

    //endregion
    // ------------------------------------------------------------------ //

}
