// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

package com.ak.taxiapp.model.calendar;

import com.ak.taxiapp.controller.CalendarDayViewController;
import com.ak.taxiapp.controller.Controller;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// endregion
// ------------------------------------------------------------------ //


public class WeekDisplay extends CalendarComponent{
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private final CalendarDayViewController controller;
    private final ArrayList<WeekButton> weekButtonsAList;
    private WeekButton selectedWeekButton; // for highlighting
    private final CalendarModel displayedCalendar;
    private final ToggleGroup toggleGroup;

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //

    /**
     * @return ArrayList of the 7 weekButtons of the week display
     */
    public ArrayList<WeekButton> getWeekButtonsAList() {
        return weekButtonsAList;
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// --------------------------- CONSTRUCTOR -------------------------- //

    /**
     * Displays the current week selected starting with Monday
     * @param calendarModel the calendar that tracks the selected date
     * @param controllerGeneric the controller of the view as Controller
     */
    public WeekDisplay(CalendarModel calendarModel, Controller controllerGeneric) {
        // cast the controller to the CalendarDayViewController to use its functions
        this.controller = (CalendarDayViewController) controllerGeneric;
        setCalendarModel(calendarModel);

        // start by using the current calendar as the displayed calendar
        displayedCalendar = new CalendarModel();

        weekButtonsAList = new ArrayList<>(7);

        toggleGroup = new ToggleGroup();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- MAIN METHODS -------------------------- //

    /**
     * Builds the week display on top of the day view for the selected day
     * Populates the weekButtonsAList with all the buttons
     */
    @Override
    public void built() {
        // built list and Nodes
        for (int i = 0; i < 7; i++) {
            ToggleButton toggleButton = new ToggleButton();
            WeekButton weekButton = new WeekButton(i, toggleButton, toggleGroup);
            weekButtonsAList.add(weekButton);
        }
        // update the display
        setSelectedWeekButton();
        update();
        highlightSelected();
    }

    // ------------------------------------------------------------------ //
    /**
     * Updates the display using the calendar to find the selected week
     * and the displayedCalendar to find the correct values and
     * assigns the onAction method.
     * Restores the previous date in the displayedCalendar(not sure why)
     */
    @Override
    public void update() {
        // store previous value
        Date prevDate = displayedCalendar.getCalendar().getTime();
        // set calendar to monday
        displayedCalendar.setSelectedDate(calendarModel.getSelectedDate());
        displayedCalendar.getCalendar().add(Calendar.DAY_OF_MONTH,
                -displayedCalendar.getDayOfWeekIndex());

        for (WeekButton weekButton : weekButtonsAList) {
            weekButton.setDate(displayedCalendar.getSelectedDate());
            weekButton.getToggleButton().setText(
                    String.valueOf(displayedCalendar.getSelectedDate().getDayOfMonth()));
            displayedCalendar.nextDay();

            weekButton.getToggleButton().setOnAction(event -> selectDate(weekButton));
        }
        // restore to selected date
        displayedCalendar.getCalendar().setTime(prevDate);
    }

    // ------------------------------------------------------------------ //
    /**
     * Method for nextWeek and PrevWeek buttons.
     * Changes the selected date by one week. Updates the date label and
     * the week display after changing the displayCalendar to monday-7 or monday+7.
     * Updates mini calendar display and highlights its selected week.
     */
    public void changeWeek(int adjustment) {
        //change the selected date by one week
        calendarModel.changeDayBy(adjustment);
        //update the date label to display selected date
        controller.updateSelectedDateLabel(calendarModel.getCalendar().getTime());

        update();
        changeDateSelectionDisplay();
        // update the selected day in the mini calendar
        updateMiniCalendar();
    }

    // ------------------------------------------------------------------ //
    /**
     * Method for day Toggle button clicked.
     * Updates the selected day in the calendar and the week display.
     * Updates date the label and mini calendar
     */
    private void selectDate(WeekButton weekButton) {
        // change the date
        calendarModel.setSelectedDate(weekButton.getDate());
        // update the display
        changeDateSelectionDisplay();
        // updates te label showing the date
        controller.updateSelectedDateLabel(CalendarModel.convertDate(calendarModel.getSelectedDate()));
        // update the selected day in the mini calendar
        updateMiniCalendar();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    /**
     * Changes the style of the button stored in the selectedWeekButton variable
     */
    public void highlightSelected() {
        selectedWeekButton.highlight();
        selectedWeekButton.getToggleButton().setSelected(true);
//        selectedWeekButton.getToggleButton().setDisable(true);
    }

    // ------------------------------------------------------------------ //
    /**
     * Updates week display to highlight the selected day taken from the calendar.
     * De-highlights the previously selected button, changes the selectedWeekButton to
     * match the date from the calendar and highlight the corresponding button
     */
    public void changeDateSelectionDisplay() {
        selectedWeekButton.dehighlight();
        setSelectedWeekButton();
        highlightSelected();
    }

    // ------------------------------------------------------------------ //
    /**
     * Gets the selected date from the calendar and assigns it to selectedWeekButton
     */
    private void setSelectedWeekButton() {
        selectedWeekButton = weekButtonsAList.get(
                calendarModel.getDayOfWeekIndex());
    }

    // ------------------------------------------------------------------ //
    /**
     * Sets the displayedCalendar date to selected date form the calendar and
     * updates the mini calendar display
     */
    private void updateMiniCalendar() {
        controller.miniMonthCalendar.displayedCalendar.setSelectedDate(CalendarModel.convertDate(calendarModel.getCalendar().getTime()));
        controller.miniMonthCalendar.update();
        controller.miniMonthCalendar.highlightSelectedWeek();
    }

    //endregion
    // ------------------------------------------------------------------ //


}
