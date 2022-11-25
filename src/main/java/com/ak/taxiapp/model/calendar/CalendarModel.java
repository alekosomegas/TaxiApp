package com.ak.taxiapp.model.calendar;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//endregion
// ------------------------------------------------------------------ //

public class CalendarModel {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private final Calendar calendar; // tracks selected date
    private final LocalDate today; // today's date
    private final LocalDateTime now; // today's date and time

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //

    public LocalDateTime getNow() {return now;}
    public LocalDate getTodayDate() {
        return this.today;
    }
    public Calendar getCalendar() {
        return calendar;
    }
    public LocalDate getSelectedDate() {
        return convertDate(calendar.getTime());
    }
    public void changeDayBy(int adjustment) {
        calendar.add(Calendar.DAY_OF_MONTH, adjustment);
    }
    public void setSelectedDate(int field, int value) {
        calendar.set(field, value);
    }
    public void setSelectedDate(LocalDate localDate) {
        calendar.setTime(convertDate(localDate));
    }
    public int getDayOfWeekIndex() {
        return adjustDayIndex(calendar.get(Calendar.DAY_OF_WEEK));
    }
    public void nextDay() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// --------------------------- CONSTRUCTOR -------------------------- //

    /**
     * Calendar object to hold the selected date, today's date and the displayed
     * calendar for each Calendar component
     */
    public CalendarModel() {
        calendar = new GregorianCalendar();
        // store today's date, convert to LocalDate object
        today = convertDate(calendar.getTime());
        now = LocalDateTime.now();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    /**
     * Calendar's first day of the week is Sunday, ind =1. This method adjusts
     * the values so Monday will be ind=1 and Sunday ind =6 etc.
     * @param i Calendar.DAY_OF_THE_WEEK
     * @return correct index when monday ois first day of the week instead of sun
     */
    private int adjustDayIndex(int i) {
        if (i == 1) { return 6;
        } else return i-2;
    }

    // ------------------------------------------------------------------ //

    /**
     * Returns the last date of the month of the currently selected date.
     * @return LocalDate object of the last day of the month
     */
    public LocalDate getLastDateOfMonth() {
        Date prevDate = calendar.getTime();
        LocalDate date;
        setSelectedDate(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
        date = getSelectedDate();
        calendar.setTime(prevDate);
        return date;
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- STATIC METHODS ------------------------- //

    public static LocalDate convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date convertDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    //endregion
    // ------------------------------------------------------------------ //


}
