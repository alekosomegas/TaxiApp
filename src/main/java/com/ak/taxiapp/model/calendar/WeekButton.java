package com.ak.taxiapp.model.calendar;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.time.LocalDate;
import java.util.Calendar;

//TODO:TIDY
public class WeekButton {

    private LocalDate date;
    private final ToggleButton toggleButton;
    private final int index;


    public WeekButton(int index, ToggleButton toggleButton, ToggleGroup toggleGroup) {
        this.index = index;
        this.toggleButton = toggleButton;
        this.toggleButton.setToggleGroup(toggleGroup);
        this.toggleButton.getStyleClass().add("week_display__button");
//        toggleButton.setText(date.getDayOfWeek().toString());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date.isEqual(LocalDate.now())) {
            toggleButton.getStyleClass().add("week_display__button--today");
        } else {
            toggleButton.getStyleClass().remove("week_display__button--today");
        }
        this.date = date;
    }
    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    public void highlight() {
        toggleButton.getStyleClass().add("week_display__button--selected");
        toggleButton.getStyleClass().remove("week_display__button");
    }
    public void dehighlight() {
        toggleButton.getStyleClass().remove("week_display__button--selected");
        // prevent hovering over selected button
        toggleButton.getStyleClass().add("week_display__button");
    }
}
