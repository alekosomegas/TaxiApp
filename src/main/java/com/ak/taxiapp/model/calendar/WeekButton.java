package com.ak.taxiapp.model.calendar;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.time.LocalDate;

public class WeekButton {

    private LocalDate date;
    private final ToggleButton toggleButton;
    private final int index;


    public WeekButton(int index, ToggleButton toggleButton, ToggleGroup toggleGroup) {
        this.index = index;
        this.toggleButton = toggleButton;
        this.toggleButton.setToggleGroup(toggleGroup);

//        toggleButton.setText(date.getDayOfWeek().toString());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    public void highlight() {
        toggleButton.getStyleClass().add("selectedDay");
    }
    public void dehighlight() {
        toggleButton.getStyleClass().remove("selectedDay");
    }
}
