package com.ak.taxiapp.model.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarModel {
    private Calendar calendar; // tracks selected date

    private CalendarModel() {
        calendar = new GregorianCalendar();

        // Possible to use param in the future
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    }

    private Date getSelectedDate() {
        return calendar.getTime();
    }

    private void setSelectedDate(int field, int value) {
        calendar.set(field, value);
    }
    private void setSelectedDate(int year, int month, int day) {
        calendar.set(year, month, day);
    }
}
