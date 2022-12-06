package com.ak.taxiapp.model.calendar;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.Layouts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
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
    public String getSelectedDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(calendar.getTime());
    }
    public void changeDayBy(int adjustment) {
        Calendar old = (Calendar) calendar.clone();
        calendar.add(Calendar.DAY_OF_MONTH, adjustment);
        support.firePropertyChange("calendar", old, calendar);
    }
    public void setSelectedDate(int field, int value) {
        Calendar old = (Calendar) calendar.clone();
        calendar.set(field, value);
        support.firePropertyChange("calendar", old, calendar);
    }
    public void setSelectedDate(LocalDate localDate) {
        Calendar old = (Calendar) calendar.clone();
        calendar.setTime(convertDate(localDate));
        support.firePropertyChange("calendar", old, calendar);
    }
    public int getDayOfWeekIndex() {
        return adjustDayIndex(calendar.get(Calendar.DAY_OF_WEEK));
    }
    public void nextDay() {
        Calendar old = (Calendar) calendar.clone();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        support.firePropertyChange("calendar", old, calendar);
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

        support = new PropertyChangeSupport(this);

    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    /**
     * Calendar's first day of the week is Sunday, ind =1. This method adjusts
     * the values so Monday will be ind=1 and Sunday ind =6 etc.
     * @param i Calendar.DAY_OF_THE_WEEK
     * @return correct index where monday as first day of the week instead of sunday
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

    public LocalDate getMondayOfSelectedWeek() {
        Date prevDate = calendar.getTime();
        LocalDate date;
        // remove the number od days form monday
        calendar.add(Calendar.DAY_OF_MONTH, -adjustDayIndex(calendar.get(Calendar.DAY_OF_WEEK)));
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

    private PropertyChangeSupport support;

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }



}
