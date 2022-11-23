package com.ak.taxiapp.controller;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.DriverDAO;
import com.ak.taxiapp.model.Ride;
import com.ak.taxiapp.model.RideDAO;
import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.calendar.DayView;
import com.ak.taxiapp.model.calendar.MiniMonthCalendar;
import com.ak.taxiapp.model.calendar.WeekDisplay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarDayViewControllerOld extends Controller {
    public Button btnDayView;
    public Button btnWeekView;
    public Button btnMonthView;
    public GridPane gpDay;
    public Label lblSelectedDay;
    public TreeView<String> tvRidesByDriver;
    public Group gpTogglebtn;
    public ToggleGroup Days;
    public HBox hbBoxArea;
    public GridPane gpMonth;
    public Label lblMonth;
    public Label lblYear;
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
    private Date selectedDate;
    private Calendar selectedCalendar;
    private int selectedTBtnIndex = startingDayIndex;
    private int selctedDayNumb = calendar.get(Calendar.DAY_OF_MONTH);
    private Toggle todayTogle = null;

    @FXML
    public void initialize() throws SQLException {
        initGridPane();

        tempCalendar.add(Calendar.DAY_OF_MONTH, -startingDayIndex -1); //first day -1

        buildToggleBtnList();

        initToggleButtons();
    
        initTreeView();

        initBoxArea();

        initmonthView();

        uodateSelectedDate(calendar);
        highlightSelectedDate();
    }

    private void uodateSelectedDate(Calendar calendar) {
        selectedDate = calendar.getTime();
        lblSelectedDay.setText(dateFormat.format(selectedDate));
        selectedCalendar = (Calendar) calendar.clone();
    }

    private void initmonthView() {
        for (int j = 1; j < 7; j++) {
            for (int i = 0; i < 7; i++) {
                Label lbl = new Label();
                lbl.setOnMouseClicked(event -> {
                    onPickDayClicked(lbl);
                });
                gpMonth.add(lbl,i, j);
                dateLabels.add(lbl);
            }
        }
        updateDateLabel(0,0);
        addDates();
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

    private void addDates() {
        tempCalendarMonth.set(tempCalendarMonth.get(Calendar.YEAR),
                tempCalendarMonth.get(Calendar.MONTH), 1); // set it to the first
    int indxOfFirstDay = adjustDayIndex(tempCalendarMonth.get(Calendar.DAY_OF_WEEK));
        int dateOflastDay = tempCalendarMonth.getActualMaximum(Calendar.DATE);

        tempCalendarMonth.add(Calendar.DAY_OF_MONTH, -indxOfFirstDay); //get value for first label

        boolean deleteLastrow = false;

        for (Label lbl : dateLabels) {
            lbl.getStyleClass().remove("today");
            lbl.getStyleClass().remove("otherMonth");

            if (dateLabels.indexOf(lbl) < indxOfFirstDay) {
                lbl.getStyleClass().add("otherMonth");
            }

            if (dateLabels.indexOf(lbl) > (dateOflastDay + indxOfFirstDay-1)) {
                if(dateLabels.indexOf(lbl) == 35 || deleteLastrow) {
                    deleteLastrow = true;
                    lbl.setText(" ");
                } else {
                    lbl.getStyleClass().add("otherMonth");
                    lbl.setText(String.valueOf(
                            tempCalendarMonth.get(Calendar.DAY_OF_MONTH)));
                }
                tempCalendarMonth.add(Calendar.DAY_OF_MONTH, 1);
                continue;
            }

            lbl.setText(String.valueOf(
                    tempCalendarMonth.get(Calendar.DAY_OF_MONTH)));

            if(calendar.getTime().equals(tempCalendarMonth.getTime())) {
                indexOfLblForMondayOfCurrentWeek = dateLabels.indexOf(lbl)-startingDayIndex;
                highlightSelectedWeek(indexOfLblForMondayOfCurrentWeek);
                lbl.getStyleClass().add("today");
            }
            tempCalendarMonth.add(Calendar.DAY_OF_MONTH, 1);
        }
        tempCalendarMonth.add(Calendar.DAY_OF_MONTH, -1);
        tempCalendarMonth.add(Calendar.MONTH, -1);
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

    private void buildToggleBtnList() {
        for (Node node : gpTogglebtn.getChildren()) {
            if (Objects.equals(node.getClass().toString(), ToggleButton.class.toString())) {
                toggleButtons.add((ToggleButton) node);
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
            gpDay.addRow(i, new Label(text));
            gpDay.add(
                    new Label("--------------------------------------------------"), 1,i);
        }
        gpDay.setPrefHeight(30);
        gpDay.setVgap(10);
    }

    public void onbtnDayClicked() {
    }

    public void onbtnWeekClicked() {
        TaxiApplication.showCalendarView("CalendarWeekView.fxml");
    }

    public void onbtnMonthClicked() {
    }

    private int findIndexOfLblForMondayOfCurrentWeek() {
        ToggleButton monday = (ToggleButton) Days.getToggles().get(0);
        ToggleButton sunday = (ToggleButton) Days.getToggles().get(6);
        String firstdateofWeek = monday.getText();
        String lastdateofWeek = sunday.getText();

        ArrayList<String> monLabels = new ArrayList<>();
        ArrayList<String> sunLabels = new ArrayList<>();
        for (int i = 0; i < 36; i+=7) {
            monLabels.add(dateLabels.get(i).getText());
            sunLabels.add(dateLabels.get(i+6).getText());
        }

        for (int i = 0; i < 7; i++) {
            if(Objects.equals(monLabels.get(i), firstdateofWeek) &&
                    Objects.equals(sunLabels.get(i), lastdateofWeek)) {
                return i*7;
            }
        }
        return 0;
    }

    public void onPrevWeekClicked() {
        int originalMonth = selectedCalendar.get(Calendar.MONTH);
        // sets temp to first day of previous week
        tempCalendar.add(Calendar.DAY_OF_MONTH, -14);
        initToggleButtons();
        onTgBtnDayClicked();

        if (selectedCalendar.get(Calendar.MONTH) != originalMonth) {
            onPrevMonth();
            indexOfLblForMondayOfCurrentWeek = findIndexOfLblForMondayOfCurrentWeek();
        } else {
            indexOfLblForMondayOfCurrentWeek -= 7;
        }
        removeStyleFromAllDays("selectedWeek");
        highlightSelectedWeek(indexOfLblForMondayOfCurrentWeek);
        highlightSelectedDate();
    }

    public void onNextWeekClicked() {
        initToggleButtons();
        onTgBtnDayClicked();

        if (selectedCalendar.get(Calendar.MONTH) != tempCalendarMonth.get(Calendar.MONTH)) {
            onNextMonth();
            indexOfLblForMondayOfCurrentWeek = findIndexOfLblForMondayOfCurrentWeek();
        } else {
            indexOfLblForMondayOfCurrentWeek += 7;
        }
        removeStyleFromAllDays("selectedWeek");
        highlightSelectedWeek(indexOfLblForMondayOfCurrentWeek);
        highlightSelectedDate();
    }

    /**
     it does a lot
      */
    public void onTgBtnDayClicked() {
        for (Toggle btn : Days.getToggles()) {
            ToggleButton tbtn = (ToggleButton) btn;
            tbtn.setDisable(false);
        }
        selectedDayBtn = (ToggleButton) Days.getSelectedToggle();
        selectedDayBtn.setDisable(true);
        int i = gpTogglebtn.getChildren().indexOf(selectedDayBtn);
        selectedTBtnIndex = Math.abs((selectedTBtnIndex - i) % 6);
        selctedDayNumb = Integer.parseInt(selectedDayBtn.getText());
        for (Node node : gpTogglebtn.getChildren()) {
            node.getStyleClass().remove("selectedDay");
        }
        selectedDayBtn.getStyleClass().add("selectedDay");
        /* find the date of the selected btn and reset to previous value
        // to change the label
        // -6 +i because the temp is pointing at the last day of the week */
        tempCalendar.add(Calendar.DAY_OF_MONTH, -6+i);
        uodateSelectedDate(tempCalendar);
        highlightSelectedDate();
        tempCalendar.add(Calendar.DAY_OF_MONTH, 6-i);
    }

    public void onNextYear() {
        updateDateLabel(Calendar.YEAR, 1);
        addDates();
    }

    public void onPrevYear() {
        updateDateLabel(Calendar.YEAR, -1);
        addDates();
    }

    public void onNextMonth() {
        updateDateLabel(Calendar.MONTH, 1);
        addDates();
    }

    public void onPrevMonth() {
        updateDateLabel(Calendar.MONTH, -1);
        addDates();
    }

    private void updateDateLabel(int field, int amount) {
        tempCalendarMonth.add(field, amount);
        lblMonth.setText(monthFormat.format(tempCalendarMonth.getTime()));
        lblYear.setText(yearFormat.format(tempCalendarMonth.getTime()));
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

    private void updateTogles(ToggleButton tb) {
        for (Toggle btn : Days.getToggles()) {
            ToggleButton tbtn = (ToggleButton) btn;
            tbtn.setDisable(false);
            tbtn.setSelected(false);
        }
        ToggleButton ttb = tb;
        ttb.setSelected(true);
        ttb.setDisable(true);
    }
    private void updateTogles() {
        for (Toggle btn : Days.getToggles()) {
            ToggleButton tbtn = (ToggleButton) btn;
            tbtn.setDisable(false);
            tbtn.setSelected(false);
        }
    }

    public void onBtnTodayClicked() {
        if(todayTogle == null) {return;}

        tempCalendar = (Calendar) calendar.clone();
        tempCalendarMonth = (Calendar) calendar.clone();
        uodateSelectedDate(calendar);
        updateDateLabel(0,0);
        removeStyleFromAllDays("selectedWeek");
        removeStyleFromAllDays("selectedDay");

        updateTogles((ToggleButton) todayTogle);
        tempCalendar.add(Calendar.DAY_OF_MONTH, -startingDayIndex -1); //first day -1
        initToggleButtons();

        addDates();
        highlightSelectedDate();
    }

    private void onPickDayClicked(Label lbl) {
        int dayOfMonth = Integer.parseInt(lbl.getText());
        boolean currMonth = !lbl.getStyleClass().contains("otherMonth");
        Calendar temp = (Calendar) calendar.clone();

        removeStyleFromAllDays("selectedWeek");
        removeStyleFromAllDays("selectedDay");

        if (currMonth) {
            tempCalendarMonth.set(tempCalendarMonth.get(Calendar.YEAR),
                    tempCalendarMonth.get(Calendar.MONTH), dayOfMonth);
            tempCalendar.set(tempCalendar.get(Calendar.YEAR),
                    tempCalendarMonth.get(Calendar.MONTH), dayOfMonth);
            calendar = (Calendar) tempCalendar.clone();

            int indexofDay = adjustDayIndex(tempCalendar.get(Calendar.DAY_OF_WEEK));

            uodateSelectedDate(tempCalendar);
            updateDateLabel(0,0);
            lbl.getStyleClass().add("selectedDay");
            updateTogles();
            tempCalendar.add(Calendar.DAY_OF_MONTH, -indexofDay -1); //first day -1
            initToggleButtons();
            calendar = (Calendar) temp.clone();

            addDates();
            highlightSelectedDate();
//            highlightSelectedWeek();
        } else {
            if(Integer.parseInt(lbl.getText()) < 7) {
                tempCalendar.add(Calendar.MONTH, 1);
                tempCalendar.set(tempCalendar.get(Calendar.YEAR),
                        tempCalendar.get(Calendar.MONTH), dayOfMonth);


                onNextMonth();
                getLblWithValue(dayOfMonth).getStyleClass().add("selectedDay");
                uodateSelectedDate(tempCalendar);
                int indexofDay = adjustDayIndex(tempCalendar.get(Calendar.DAY_OF_WEEK));
                updateTogles();
                calendar = (Calendar) tempCalendar.clone();
                tempCalendar.add(Calendar.DAY_OF_MONTH, -indexofDay -1); //first day -1

                initToggleButtons();
            } else {
                System.out.println(tempCalendar.getTime());
                System.out.println("prev");
                onPrevMonth();
                System.out.println(tempCalendar.getTime());
                tempCalendar.set(tempCalendar.get(Calendar.YEAR),
                        tempCalendarMonth.get(Calendar.MONTH), dayOfMonth);

                System.out.println(tempCalendar.getTime());
                getLblWithValue(dayOfMonth).getStyleClass().add("selectedDay");
                uodateSelectedDate(tempCalendar);
                int indexofDay = adjustDayIndex(tempCalendar.get(Calendar.DAY_OF_WEEK));
                updateTogles();
                calendar = (Calendar) tempCalendar.clone();
                tempCalendar.add(Calendar.DAY_OF_MONTH, -indexofDay -1); //first day -1

                initToggleButtons();
            }
        }
        calendar = (Calendar) temp.clone();

    }

    private Label getLblWithValue(int val) {
        for (Label lbl : dateLabels) {
            try {
            if (Integer.parseInt(lbl.getText()) == val) {
                return lbl;
            }
        } catch (NumberFormatException e) {
            }
    }
        return null;
    }
}
