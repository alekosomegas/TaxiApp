package com.ak.taxiapp.model.calendar;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.controller.CalendarDayViewController;
import com.ak.taxiapp.controller.Controller;

import com.ak.taxiapp.model.Ride;
import com.ak.taxiapp.model.RideDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.sql.SQLException;
import java.time.LocalDate;

//endregion
// ------------------------------------------------------------------ //

public class DayView extends CalendarComponent {

    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private final CalendarDayViewController controller;
    private HBox hBoxRectArea; // ride rectangles
    private Pane pnTimes; // hour labels
    private Pane pnLines; // hour lines
    public static final double SPACING = 27; // spacing between lines/labels, one hour
    public static double ADJFACTSPACING = -9; // adjustment for line placement

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //


    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// --------------------------- CONSTRUCTOR -------------------------- //

    /**
     * Creates the day display that shows all the rides of the selected date.
     * @param calendarModel the calendar that tracks the selected date
     * @param controllerGeneric the controller of the view as Controller
     */
    public DayView(CalendarModel calendarModel, Controller controllerGeneric) {
        // cast the controller to the CalendarDayViewController to use its functions
        this.controller = ((CalendarDayViewController) controllerGeneric);
        setCalendarModel(calendarModel);
        // get access to the FXML nodes to add the rides info
        getNodesFromController();
        // Find the rides and populate the rides pane
        displayRides(calendarModel);
        // now line
        builtNowLine();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- MAIN METHODS -------------------------- //

    /**
     * Creates the hours and lines fot the day calendar
     */
    @Override
    public void built() {
        String textHour;
        // create 24 lines and labels
        for (int i = 0; i < 25; i++) {
            Label lblTextHour = new Label();
            Line line = new Line(
                    0,SPACING*i -ADJFACTSPACING,250,SPACING*i -ADJFACTSPACING);
            line.setStroke(Color.LIGHTGRAY);

            textHour = ":00";
            if(i < 10) {textHour = "0" + i + textHour;
                } else {textHour = i + textHour;
            }
            // text label settings
            lblTextHour.setText(textHour);
            lblTextHour.setLayoutY(SPACING*i);
            lblTextHour.setLayoutX(2);

            addToPanes(lblTextHour, line);
        }
    }

    // ------------------------------------------------------------------ //
    /**
     * Finds the day's rides and displays them their pane
     * @param calendarModel To find the selected day
     */
    private void displayRides(CalendarModel calendarModel) {
        hBoxRectArea.getChildren().clear();
        pnLines.getChildren().clear();
        ObservableList<Ride> rides = getRidesByDate(calendarModel.getSelectedDate());

        //TODO: should the rectangle be created elsewhere, don't we need a list of them?
        assert rides != null;
        for (Ride ride : rides) {
            hBoxRectArea.getChildren().add(new DayCalendarRectangle(ride, controller).getRectangle());
        }

        hBoxRectArea.toFront();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    /**
     * Uses the RideDao to find rides with the same date as the selected day.
     * @param date the selected date
     * @return list of rides or null if nothing has been found
     */
    private ObservableList<Ride> getRidesByDate(LocalDate date){
        try {
            return RideDAO.searchRidesByDate(date.toString());
        } catch (SQLException e) {
            return null;
        }
    }

    // ------------------------------------------------------------------ //
    /**
     * Assigns the FXML panes to the corresponding instance's nodes.
     */
    private void getNodesFromController() {
        hBoxRectArea = controller.hbBoxArea;
        pnLines = controller.pnLines;
        pnTimes = controller.pnTimes;
    }

    // ------------------------------------------------------------------ //
    /**
     * Adds the nodes in the appropriate panes
     * @param lblTextHour the labels that represent an hour in the dau calendar
     * @param line the line that represents an hour in the dau calendar
     */
    private void addToPanes(Label lblTextHour, Line line) {
        pnTimes.getChildren().add(lblTextHour);
        pnLines.getChildren().add(line);
    }

    // ------------------------------------------------------------------ //
    /**
     * Looks at the selected date in the calendar and the today's date
     */
    private boolean isToday() {
        return calendarModel.getSelectedDate().compareTo(calendarModel.getTodayDate()) == 0;
    }

    // ------------------------------------------------------------------ //

    /**
     * Builds the now line everytime the day changes.
     */
    private void builtNowLine() {
        if (isToday()) {
            Line lineNow = new Line(0,0,250,0);
            lineNow.setStroke(Color.RED);
            lineNow.setLayoutY(calendarModel.getNow().getHour() *
                    SPACING + calendarModel.getNow().getMinute());
            pnLines.getChildren().add(lineNow);
            lineNow.toFront();
        }
    }

    //endregion
    // ------------------------------------------------------------------ //


}
