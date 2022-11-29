package com.ak.taxiapp.controller.calendar;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarWeekViewController extends Controller {
    public Button btnDayView;
    public Button btnWeekView;
    public Button btnMonthView;
    public GridPane gpWeek;
    public Label lblMonth;
    public GridPane gpWeekFields;

    private Label[] gridPaneDaysArray = new Label[7];
    private Label[] gridPaneDatesArray = new Label[7];
    private List<String> days =  Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");

    private Calendar calendar = new GregorianCalendar();
    private int day = calendar.get(Calendar.DAY_OF_WEEK);
    Calendar tempCalendar = (Calendar) calendar.clone(); // tracks the first day of the week -1
    SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");

    @FXML
    public void initialize() {
        tempCalendar.add(Calendar.DAY_OF_MONTH, -day + 1); //first day -1
        lblMonth.setText(monthYearFormat.format(calendar.getTime())); // start by today's month

        for (int i = 0; i < 7; i++) {
            Label lblDay = new Label(days.get(i));
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
            Label lblDate = new Label(String.valueOf(tempCalendar.get(Calendar.DAY_OF_MONTH)));
            gpWeek.add(lblDay, i, 0);
            gpWeek.add(lblDate, i, 1);
            gridPaneDaysArray[i] = lblDay;
            gridPaneDatesArray[i] = lblDate;

            lblDay.getStyleClass().clear();
            lblDate.getStyleClass().clear();
            if (tempCalendar.getTime().equals(calendar.getTime())) {
                lblDate.getStyleClass().add("today");
                lblDay.getStyleClass().add("today");
            }
        }

        String text;
        for (int i = 0; i < 25; i++) {
            text = ":00";
            if (i < 10) {
                text = "0" + i + text;
            } else {
                text = i + text;
            }
            gpWeekFields.addRow(i, new Label(text));
            for (int j = 1; j < 7; j++) {
                gpWeekFields.add(new Label("--------------------"), j, i);
            }
            gpWeekFields.setPrefHeight(30);
            gpWeekFields.setVgap(10);
            gpWeekFields.setHgap(0);
        }
    }

    public void onbtnDayClicked() {
        TaxiApplication.showCalendarView("fxml/calendar/CalendarDayView.fxml");
    }

    public void onbtnMonthClicked() {
    }

    public void onbtnWeekClicked() {
    }

    public void onNextDayClicked() {
        Collections.rotate(days, -1);
        tempCalendar.add(Calendar.DAY_OF_MONTH, -6); //jump back to firstday of week -1

        for (int i = 0; i < 7; i++) {
            gridPaneDaysArray[i].setText(days.get(i));

            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
            gridPaneDatesArray[i].setText(String.valueOf(tempCalendar.get(Calendar.DAY_OF_MONTH)));
            System.out.println(new SimpleDateFormat("dd/MM/yy").format(tempCalendar.getTime()));

            gridPaneDaysArray[i].getStyleClass().clear();
            gridPaneDatesArray[i].getStyleClass().clear();
            if(tempCalendar.getTime().equals(calendar.getTime())) {
                gridPaneDaysArray[i].getStyleClass().add("today");
                gridPaneDatesArray[i].getStyleClass().add("today");
            }
            if(i==0) {
                lblMonth.setText(monthYearFormat.format(tempCalendar.getTime()));
            }
        }
        System.out.println("------------------------------");
    }

    public void onPrevDayClicked() {
        Collections.rotate(days, 1);

        tempCalendar.add(Calendar.DAY_OF_MONTH, -8);//jump back to firstday of week -1

        for (int i = 0; i < 7; i++) {
            gridPaneDaysArray[i].setText(days.get(i));
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
            gridPaneDatesArray[i].setText(String.valueOf(tempCalendar.get(Calendar.DAY_OF_MONTH)));

            gridPaneDaysArray[i].getStyleClass().clear();
            gridPaneDatesArray[i].getStyleClass().clear();
            if(tempCalendar.getTime().equals(calendar.getTime())) {
                gridPaneDaysArray[i].getStyleClass().add("today");
                gridPaneDatesArray[i].getStyleClass().add("today");
            }

            if(i==0) {
                lblMonth.setText(monthYearFormat.format(tempCalendar.getTime()));
            }
        }
    }


}
