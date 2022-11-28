package com.ak.taxiapp.model.calendar;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.controller.calendar.CalendarDayViewController;
import com.ak.taxiapp.controller.Controller;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//endregion
// ------------------------------------------------------------------ //

public class MiniMonthCalendar extends CalendarComponent{
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private final ArrayList<MonthLabel> monthLabelAList; //holds all labels
    public CalendarModel displayedCalendar; // used to display the miniCalendar display
    private MonthLabel selectedMonthLabel; // to make highlighting easier
    private MonthLabel selectedWeekMondayMothLabel; // Monday of selected week
    private final CalendarDayViewController controller;

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //

    /**
     * @return ArrayList of the 42 MonthLabel of the mini calendar display
     */
    public ArrayList<MonthLabel> getMonthLabelAList() {
        return monthLabelAList;
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// --------------------------- CONSTRUCTOR -------------------------- //

    /**
     * Creates a calendar displaying the whole month and the date selected in the day
     * view taken from the calendar. Sets the selected date and highlights the selected week
     * @param calendarModel the calendar that tracks the selected date
     * @param controllerGeneric the controller of the view as Controller
     */
    public MiniMonthCalendar(CalendarModel calendarModel, Controller controllerGeneric) {
        // cast the controller to the CalendarDayViewController to use its functions
        this.controller = ((CalendarDayViewController) controllerGeneric);
        setCalendarModel(calendarModel);
        // start by using the current calendar as the displayed calendar
        displayedCalendar = new CalendarModel();
        monthLabelAList = new ArrayList<>(42);
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- MAIN METHODS -------------------------- //

    /**
     * Builds the calendar view for the selected day
     * Populates the monthLabelAList with all the labels and sets their onMouseClicked
     * method. Updates the monthLabels and highlight the selected week
     */
    @Override
    public void built() {
        // Goes through each row and column of the grid pane and creates
        // a new monthLabel object and assigns the selectDate function
        for (int j = 1; j < 7; j++) {
            for (int i = 0; i < 7; i++) {
                Label label = new Label();
                MonthLabel monthLabel = new MonthLabel(label, i, j);
                monthLabelAList.add(monthLabel);

                label.setOnMouseClicked(event -> selectDate(monthLabel));
            }
        }
        update();
        highlightSelectedWeek();
    }

    // ------------------------------------------------------------------ //
    /**
     * Updates all the monthLabels using the calendar to find the selected date
     * and highlights it and the displayedCalendar to find the correct value for each label.
     * Updates the labels showing the month and year displayed.
     */
    @Override
    public void update() {
        // save previous date before making changes to calendar
        Date prevDate = displayedCalendar.getCalendar().getTime();
        // set the date to the first of the current month
        displayedCalendar.setSelectedDate(Calendar.DAY_OF_MONTH, 1);
        LocalDate firstDay = displayedCalendar.getSelectedDate();
        LocalDate lastDay = displayedCalendar.getLastDateOfMonth();
        // finds the column index of the first day of the month
        int firstDayIndex = displayedCalendar.getDayOfWeekIndex();
        // set the date to the first date from previous month to be displayed by
        // substituting the number of days between it and the first of the current month
        displayedCalendar.getCalendar().add(Calendar.DAY_OF_MONTH, -firstDayIndex);
        // Used to decide whether to display last row with next month's dates
        boolean deleteLastRow = false;

        for (MonthLabel monthLabel : monthLabelAList) {
            // removes all the styles from all the labels
            monthLabel.removeAllStyles();
            // set date and text to be displayed
            monthLabel.setDate(displayedCalendar.getSelectedDate());
            // Checks if the label contains today's date and adds the 'today' style
            setStyleIfToday(monthLabel);
            // if the label is before the first date of month or after the last day then add the other month style
            setStyleForOtherMonthLabels(firstDay, lastDay, monthLabel);
            // find if it is the selected date and highlight it
            if (isSelectedDate(monthLabel)) {selectAndHighlightLabel(monthLabel);}
            // if the label is the first of the last row set the deleteLastRow to true
            if (isFirstDayOfLastRow(lastDay, monthLabel) || deleteLastRow) {
                deleteLastRow = true;
                monthLabel.removeAllStyles();
                monthLabel.getLabel().setText(" ");
            }
            displayedCalendar.nextDay();
        }
        // return to previous selected date, keep in current month, so it's easier to change month
        displayedCalendar.getCalendar().setTime(prevDate);
        updateMiniMonthLabels(0,0);
    }

    // ------------------------------------------------------------------ //
    /**
     * Method for clicking on the labels. Changes the selected day in the calendar
     * and changes the highlighted day. Updates the selected day label and
     * highlights the selected week. Changes the week display accordingly and
     * selects the appropriate button
     */
    private void selectDate(MonthLabel monthLabel) {
        // changes the selected day
        calendarModel.setSelectedDate(monthLabel.getDate());
        // highlight
        selectAndHighlightLabel(monthLabel);
        highlightSelectedWeek();
        // updates date label
        controller.updateSelectedDateLabel(CalendarModel.convertDate(calendarModel.getSelectedDate()));
        // updates week display
        controller.updateWeekDisplay();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    /**
     * Checks if the label contains today's date and adds the 'today' style
     */
    private void setStyleIfToday(MonthLabel monthLabel) {
        if (monthLabel.getDate().compareTo(calendarModel.getTodayDate()) == 0) {
            monthLabel.getLabel().getStyleClass().add("today");
        }
    }

    // ------------------------------------------------------------------ //
    /**
     * Sets the 'otherMonth' style if the label is not part of the selected month
     */
    private void setStyleForOtherMonthLabels(
            LocalDate firstDay, LocalDate lastDay, MonthLabel monthLabel) {
        if (isOtherMonth(firstDay,lastDay,monthLabel)) {
            monthLabel.getLabel().getStyleClass().add("otherMonth");
        }
    }

    // ------------------------------------------------------------------ //
    /**
     * Checks if label is not part of the selected month
     */
    private boolean isOtherMonth(
            LocalDate firstDay, LocalDate lastDay, MonthLabel monthLabel) {
        return monthLabel.getDate().compareTo(firstDay) < 0 ||
                monthLabel.getDate().compareTo(lastDay) > 0;
    }

    // ------------------------------------------------------------------ //
    /**
     * Checks if label is the first of the last row to determine whether the
     * row should be displayed
     */
    private boolean isFirstDayOfLastRow(LocalDate lastDay, MonthLabel monthLabel) {
        return (monthLabel.getDate().compareTo(lastDay)) > 0 &&
                (monthLabel.getRow() == 6) &&
                (monthLabel.getColumn() == 0);
    }

    // ------------------------------------------------------------------ //
    /**
     * Checks if the label has the same date value as the selected day in the calendar
     */
    private boolean isSelectedDate(MonthLabel monthLabel) {
        return monthLabel.getDate().compareTo(calendarModel.getSelectedDate()) == 0;
    }

    // ------------------------------------------------------------------ //
    /**
     * Finds the index of the selected day from the selectedMonthLabel and its
     * day index to get the returned MonthLabel from the monthLabelAList
     * @return The MonthLabel of the label that represents the Monday of the week
     *         that contains the selected date.
     */
    private MonthLabel findMondayOfSelectedWeek() {
        int indexDay = selectedMonthLabel.getColumn();
        int indexSelected = monthLabelAList.indexOf(selectedMonthLabel);
        return monthLabelAList.get(indexSelected - indexDay);
    }

    // ------------------------------------------------------------------ //
    /**
     * Initialises the selectedMonthLabel. Removes the style from the
     * previously selected label, changes the selected label stored in
     * selectedMonthLabel and highlights it
     * @param monthLabel The monthLabel to select and highlight
     */
    private void selectAndHighlightLabel(MonthLabel monthLabel) {
        // sets the selection to today, when is used the first time
        if(selectedMonthLabel == null) {
            selectedMonthLabel = monthLabel;}
        selectedMonthLabel.getLabel().getStyleClass().remove("selectedDay");
        selectedMonthLabel = monthLabel;
        selectedMonthLabel.getLabel().getStyleClass().add("selectedDay");
    }

    // ------------------------------------------------------------------ //
    /**
     * Updates the month and year display on top of the mini calendar.
     * Changes the displayCalendar.
     * @param field Calendar.MONTH or CALENDAR.YEAR
     * @param amount int
     */
    public void updateMiniMonthLabels(int field, int amount) {
        // changes month or year in the displayedCalendar
        displayedCalendar.getCalendar().add(field, amount);
        // changes the label text using the displayedCalendar
        controller.lblMonth.setText(displayedCalendar.getSelectedDate().getMonth().name().toLowerCase());
        controller.lblYear.setText(String.valueOf(displayedCalendar.getSelectedDate().getYear()));
    }

    // ------------------------------------------------------------------ //
    /**
     * Highlights the selected week in the mini calendar starting from monday
     */
    public void highlightSelectedWeek() {
        // sets the selection to today, when is used the first time
        if(selectedWeekMondayMothLabel == null) {selectedWeekMondayMothLabel = findMondayOfSelectedWeek();}
        // removes the style from the previous monday and +6 days
        for (int i = 0; i < 7; i++) {
            monthLabelAList.get(
                            monthLabelAList.indexOf(selectedWeekMondayMothLabel) + i)
                    .getLabel().getStyleClass().remove("selectedWeek");
        }
        // sets the new selection
        selectedWeekMondayMothLabel = findMondayOfSelectedWeek();
        // adds the style to the new selected monday and +6 days
        for (int i = 0; i < 7; i++) {
            monthLabelAList.get(
                            monthLabelAList.indexOf(selectedWeekMondayMothLabel) + i)
                    .getLabel().getStyleClass().add("selectedWeek");
        }
    }

    //endregion
    // ------------------------------------------------------------------ //


}
