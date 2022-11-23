package com.ak.taxiapp.controller;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.DriverDAO;
import com.ak.taxiapp.model.Ride;
import com.ak.taxiapp.model.RideDAO;
import com.ak.taxiapp.model.calendar.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class CalendarDayViewController extends Controller {
    public Button btnDayView;
    public Button btnWeekView;
    public Button btnMonthView;
    public GridPane gpDayView;
    public Label lblSelectedDay;
    public TreeView<String> tvRidesByDriver;
    public Group gpTogglebtn;
    public ToggleGroup Days;
    public HBox hbBoxArea;
    public GridPane gpMonth;
    public Label lblMonth;
    public Label lblYear;
    public HBox hbDayBtns;
    private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
    private Calendar calendar = new GregorianCalendar();
    private Calendar tempCalendar = (Calendar) calendar.clone(); // tracks the first day of the week -1
    private Calendar tempCalendarMonth = (Calendar) calendar.clone();
    private int startingDayIndex = adjustDayIndex(calendar.get(Calendar.DAY_OF_WEEK));
    private int indexOfLblForMondayOfCurrentWeek;
    private ToggleButton selectedDayBtn;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private ObservableList<Label> dateLabels = FXCollections.observableArrayList();
    private Date selectedDateOld;
    private Calendar selectedCalendar;
    private int selectedTBtnIndex = startingDayIndex;
    private int selctedDayNumb = calendar.get(Calendar.DAY_OF_MONTH);
    private Toggle todayTogle = null;



    private CalendarModel calendarModel;
    private DayView dayView;
    public MiniMonthCalendar miniMonthCalendar;
    public WeekDisplay weekDisplay;
    private LocalDate selectedDate;

    /**
     * Creates the instances of the components assign them to global variables.
     */
    private void loadComponents() {
        this.calendarModel = new CalendarModel();

        this.selectedDate = calendarModel.getTodayDate();

        // Create new instances;
        this.dayView = new DayView();
        this.miniMonthCalendar = new MiniMonthCalendar(calendarModel, this);
        this.weekDisplay = new WeekDisplay(calendarModel, this);
    }

    /**
     * Uses the components built function
     */
    private void buildComponents() {
        //dayView.built();
        miniMonthCalendar.built();
        weekDisplay.built();
    }

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

    @FXML
    public void initialize() throws SQLException {
        // Start by creating instances of components
        loadComponents();
        // Then build them by calling their build function
        buildComponents();
        // Use the FXML variables to display components
        displayComponents();


        initGridPane();

        tempCalendar.add(Calendar.DAY_OF_MONTH, -startingDayIndex -1); //first day -1


        initToggleButtons();
    
        initTreeView();

        initBoxArea();


        uodateSelectedDate(calendar);
        highlightSelectedDate();
    }

    public void updateSelectedDateLabel(Date date) {
        lblSelectedDay.setText(dateFormat.format(date));

    }
    private void uodateSelectedDate(Calendar calendar) {
        selectedDateOld = calendar.getTime();
        lblSelectedDay.setText(dateFormat.format(selectedDateOld));
        selectedCalendar = (Calendar) calendar.clone();
    }

    private int adjustDayIndex(int i) {
        if (i == 1) {
            return 6;
        } else return i-2;
    }

    private void removeStyleFromAllDays(String style) {
        for (Label lbl : dateLabels) {
            lbl.getStyleClass().remove(style);
        }
    }

    private void initBoxArea() throws SQLException {
        for (Ride ride : Objects.requireNonNull(getRidesByDate())) {
            double MINUTES_FACTOR = 0.5;
            double HOURS_FACTOR = MINUTES_FACTOR * 60;
            int Y_FACTOR = 28;
            double height = Integer.parseInt(
                    ride.getRidesDuration().split("h")[0]) * HOURS_FACTOR +
                    Integer.parseInt(
                            ride.getRidesDuration().split("h")[1].split("m")[0])
                    * MINUTES_FACTOR;
            Rectangle rectangle = new Rectangle(50,height);
            hbBoxArea.getChildren().add(rectangle);
            rectangle.setTranslateY(
                    Integer.parseInt(ride.getRidesTimeStart().split(":")[0]) *
                    Y_FACTOR);

            Color color = Color.valueOf(
                    DriverDAO.searchDriverById(
                            ride.getRidesDriverId()).getDriver_color());
            rectangle.setFill(color);
        }
    }

    private ObservableList<Ride> getRidesByDate(){
        try {
            return RideDAO.searchRides();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initTreeView() {
        TreeItem<String> rootNode =
                new TreeItem<String>("Rides By Driver");
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
                TreeItem<String> driverNode = new TreeItem<String>(
                        ride.getRidesDriver());
                rootNode.getChildren().add(driverNode);
                driverNode.getChildren().add(leaf);
            }
        }
    }

    private void initToggleButtons() {
        for (ToggleButton tb: toggleButtons) {
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
            tb.setText(String.valueOf(tempCalendar.get(Calendar.DAY_OF_MONTH)));

            tb.getStyleClass().remove("today");
            tb.getStyleClass().remove("selectedDay");
            if (tempCalendar.getTime().equals(calendar.getTime())) {
                tb.getStyleClass().add("today");
                tb.getStyleClass().add("selectedDay");
                // only the first time select today
                if (selectedDayBtn == null) {
                    tb.setSelected(true);
                    tb.setDisable(true);
                    selectedDayBtn = tb;
                    if (todayTogle == null) {todayTogle = selectedDayBtn;}
                }
            }
        }
    }


    private void initGridPane() {
        String text;
        for (int i = 0; i < 25; i++) {
            text = ":00";
            if(i < 10) {
                text = "0" + i + text;
            } else {
                text = i + text;
            }
            gpDayView.addRow(i, new Label(text));
            gpDayView.add(
                    new Label("--------------------------------------------------"), 1,i);
        }
        gpDayView.setPrefHeight(30);
        gpDayView.setVgap(10);
    }

    public void onbtnDayClicked() {
    }

    public void onbtnWeekClicked() {
        TaxiApplication.showCalendarView("CalendarWeekView.fxml");
    }

    public void onbtnMonthClicked() {
    }

    public void onPrevWeekClicked() {
        weekDisplay.changeWeek(-7);
    }

    public void onNextWeekClicked() {
        weekDisplay.changeWeek(7);
    }

    public void onNextYear() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.YEAR, 1);
        miniMonthCalendar.update();
    }

    public void onPrevYear() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.YEAR, -1);
        miniMonthCalendar.update();
    }

    public void onNextMonth() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.MONTH, 1);
        miniMonthCalendar.update();
    }

    public void onPrevMonth() {
        miniMonthCalendar.updateMiniMonthLabels(Calendar.MONTH, -1);
        miniMonthCalendar.update();
    }

    private void highlightSelectedWeek(int mondayIndexOfWeekLbl) {
        for (int i = 0; i < 7; i++) {
            dateLabels.get(mondayIndexOfWeekLbl + i).getStyleClass().add("selectedWeek");
        }
    }

    private void highlightSelectedDate() {
        removeStyleFromAllDays("selectedDay");
        for (Label lbl : dateLabels) {
            if (Objects.equals(String.valueOf(selectedCalendar.get(Calendar.DAY_OF_MONTH)), lbl.getText()) &&
                    !lbl.getStyleClass().contains("otherMonth")) {
                    lbl.getStyleClass().add("selectedDay");
                }
        }
    }

    public void onBtnTodayClicked() {
        // set selected date to today
        calendarModel.setSelectedDate(calendarModel.getTodayDate());
        miniMonthCalendar.displayedCalendar.setSelectedDate(calendarModel.getTodayDate());

        miniMonthCalendar.update();
        miniMonthCalendar.highlightSelectedWeek();
    }

}
