package com.ak.taxiapp.model.calendar;

import com.ak.taxiapp.controller.CalendarDayViewController;

import java.util.Date;

/**
 * Creates a calendar displaying the whole month and the date selected in the
 * day view. Can change the selected date and highlights the selected week displayed
 * on top of the day view and today's date. Can change month and year.
 */
public class MiniMonthCalendar extends CalendarComponent{

    private MiniMonthCalendar(CalendarModel calendarModel, CalendarDayViewController controller, Date date) {
        setController(controller);
        setCalendarModel(calendarModel);
        setSelectedDate(date);
    }


    /**
     * Builds the calendar view for the selected day.
     */
    private void builtCalendarView() {

    }

    /**
     * Updates the calendar view
     */
    @Override
    void update() {

    }

    /**
     * Selects a date
     */
    private void selectDate() {

    }

    /**
     * Changes the displayed month or Year
     */
    private void changeDisplayedMonth() {

    }
}
