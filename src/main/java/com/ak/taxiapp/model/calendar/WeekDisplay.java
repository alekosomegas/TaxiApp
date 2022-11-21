package com.ak.taxiapp.model.calendar;

import com.ak.taxiapp.controller.CalendarDayViewController;

import java.util.Date;


public class WeekDisplay extends CalendarComponent{

    private WeekDisplay(CalendarModel calendarModel, CalendarDayViewController controller, Date date) {
        setCalendarModel(calendarModel);
        setController(controller);
        setSelectedDate(date);
    }

    private void builtDisplay() {

    }
    @Override
    void update() {

    }

    private void changeWeek() {

    }

    private void displaySelectedDay() {

    }

    private void selectDate() {

    }
}
