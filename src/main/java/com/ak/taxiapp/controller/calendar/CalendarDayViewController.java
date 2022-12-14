package com.ak.taxiapp.controller.calendar;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.driver.Driver;
import com.ak.taxiapp.model.driver.DriverDAO;
import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.model.ride.Ride;
import com.ak.taxiapp.model.ride.RideDAO;
import com.ak.taxiapp.model.calendar.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

// endregion
// ------------------------------------------------------------------ //

//TODO:TIDY
public class CalendarDayViewController extends Controller implements PropertyChangeListener {
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

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Creates new instances of all the Calendar Components, calls their built method
     * and adds them to the corresponding FXML Nodes
     */
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

        calendarModel.addPropertyChangeListener(this);
    }

    @Override
    public void updateView() {
        initTreeView();
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

        tvRidesByDriver.setCellFactory(tv -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.getStyleClass().clear();
                if (empty) {
                    setText("");
                    this.getStyleClass().add("tree_view--empty");
                } else {
                    String id = item.split("#")[0];
                    if (Objects.equals(id, "NODE")) {
                        item = item.split("#")[1];
                        String color;

                        try {
                            Driver driver = DriverDAO.searchDriverByName(item);
                            color = driver.getDriver_color().replace("0x", "#");
                            color = color.substring(0,7);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

//                        this.setStyle("-fx-background-color: " +color+ "50 ;");
                        this.getStyleClass().add("tree_view--node");

                    } else {

                        this.getStyleClass().add("tree_view--leaf");
                    }
                    setText(item);

                }


            }
        });


        TreeItem<String> rootNode = new TreeItem<>();
        rootNode.setExpanded(true);
        tvRidesByDriver.setRoot(rootNode);
        tvRidesByDriver.setShowRoot(false);

        for (Ride ride : Objects.requireNonNull(getRidesByDate(calendarModel.getSelectedDateString()))) {
            boolean found = false;
            TreeItem<String> leaf =
                    new TreeItem<>(
                            "\n"+ ride.getRidesTimeStart() + "-"+
                                    ride.getRidesTimeEnd() + "\n"+
                            ride.getRidesFrom() +" - "+ ride.getRidesTo()
                                    + "\n________________________________________________");

            for (TreeItem<String> driverNode : rootNode.getChildren()) {
                String driver = driverNode.getValue();
                String id = driver.split("#")[0];
                if(Objects.equals(id, "NODE")) {
                    driver = driver.split("#")[1];
                }
                if (driver.contentEquals(ride.getRidesDriver())){
                    driverNode.getChildren().add(leaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> driverNode = new TreeItem<>(ride.getRidesDriver());
                driverNode.setValue("NODE" + "#"+ driverNode.getValue());
                driverNode.setExpanded(true);
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

    private ObservableList<Ride> getRidesByDate(String date){
        try {
            return RideDAO.searchRidesByDate(date);
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
        TaxiApplication.showCalendarView("fxml/calendar/CalendarWeekView.fxml");
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


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Calendar calendar1 = (Calendar) evt.getNewValue();
        initTreeView();
    }
}
