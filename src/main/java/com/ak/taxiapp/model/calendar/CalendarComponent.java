package com.ak.taxiapp.model.calendar;

import com.ak.taxiapp.controller.Controller;

import java.util.Date;

public class CalendarComponent {

    // TODO: check if this is unique for each instance
    Controller controller;
    CalendarModel calendarModel;
    Date selectedDate;

    void setController(Controller controller) {
        this.controller = controller;
    }
    void setCalendarModel(CalendarModel calendarModel) {
        this.calendarModel = calendarModel;
    }

    void update() {

    }

    /**
     * Retrieves selected date from the CalendarModel
     */
    void setSelectedDate(Date date) {
        this.selectedDate = date;

    }

    /**
     * Retrieves today's date from the CalendarModel
     */
    void getTodayDate() {

    }
}
