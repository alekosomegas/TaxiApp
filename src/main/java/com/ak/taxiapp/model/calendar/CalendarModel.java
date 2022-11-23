package com.ak.taxiapp.model.calendar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarModel {
    // TODO: no need for selected date? Use only calendar
    private Calendar calendar; // tracks selected date
    private final LocalDate today; // today's date

    public CalendarModel() {
        calendar = new GregorianCalendar();
        // store today's date, convert to LocalDate object
        today = convertDate(calendar.getTime());
    }

    private int adjustDayIndex(int i) {
        if (i == 1) {
            return 6;
        } else return i-2;
    }

    public static LocalDate convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date convertDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    public LocalDate getTodayDate() {
        return this.today;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public LocalDate getSelectedDate() {
        return convertDate(calendar.getTime());
    }

    public void changeDayBy(int adjucstment) {
        calendar.add(Calendar.DAY_OF_MONTH, adjucstment);
    }
    public void setSelectedDate(int field, int value) {
        calendar.set(field, value);
    }
    public void setSelectedDate(int year, int month, int day) {
        calendar.set(year, month, day);
    }
    public void setSelectedDate(LocalDate localDate) {
        calendar.setTime(convertDate(localDate));
    }
    public int getDayOfWeekIndex() {
        return adjustDayIndex(calendar.get(Calendar.DAY_OF_WEEK));
    }
    public LocalDate getLastDateOfMonth() {
        Date prevDate = calendar.getTime();
        LocalDate date;
        setSelectedDate(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
        date = getSelectedDate();
        calendar.setTime(prevDate);
        return date;
    }

    public void nextDay() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
    }


}
