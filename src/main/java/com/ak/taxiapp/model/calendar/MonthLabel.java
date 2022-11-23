package com.ak.taxiapp.model.calendar;

import javafx.scene.control.Label;

import java.time.LocalDate;

public class MonthLabel {
    private final Label label;
    private LocalDate date;
    private final int column;
    private final int row;

    public Label getLabel() {
        return label;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date and the text for the label
     */
    public void setDate(LocalDate date) {
        this.date = date;
        label.setText(String.valueOf(date.getDayOfMonth()));
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    /**
     * Holds the label handle, position and date
     */
    public MonthLabel(Label label, int x, int y) {
        this.label = label;
        this.column = x;
        this.row = y;
    }

    public void removeAllStyles() {
        this.label.getStyleClass().clear();
    }

}
