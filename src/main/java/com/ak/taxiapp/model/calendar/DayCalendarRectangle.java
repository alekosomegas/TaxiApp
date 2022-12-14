// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

package com.ak.taxiapp.model.calendar;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.controller.calendar.CalendarDayViewController;
import com.ak.taxiapp.model.ride.Ride;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

//endregion
// ------------------------------------------------------------------ //

public class DayCalendarRectangle {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private final Rectangle rectangle; // the writable to be displayed
    private final Ride ride; // the ride associated with the rectangle
    private LocalDate date; // the date of the ride
    private Label info; // info about the ride
    public CalendarDayViewController controller;

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------ GETTERS & SETTERS ----------------------- //

    public Rectangle getRectangle() {
        return rectangle;
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// --------------------------- CONSTRUCTOR -------------------------- //

    /**
     * Colored rectangles in the day view, for each ride. Color is associated with
     * a driver.
     */
    public DayCalendarRectangle(Ride ride, CalendarDayViewController controller) {
        this.ride = ride;
        this.controller = controller;
        this.date = ride.getDate();
        this.rectangle = new Rectangle();
        this.info = new Label();
        // create and display the rectangle
        builtRectangle();
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- MAIN METHODS -------------------------- //

    /**
     * Creates the rectangle
     */
    private void builtRectangle() {
        double WIDTH = 50;

        rectangle.setTranslateY(calculateRecY());
        rectangle.setHeight(calculateHeight());

        rectangle.setWidth(WIDTH);
        rectangle.getStyleClass().add("rectangle");

        onMouseClicked();

        setColor();
    }


    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    private void onMouseClicked() {
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {



                    HashMap<String, String> values = new HashMap<>();

                        values.put("ridesDurationCol", ride.getRidesDuration());
                        values.put("ridesClientIdCol", String.valueOf(ride.getRidesClientId()));
                        values.put("ridesClientCol", ride.getRidesClient());
                        values.put("ridesFromCol", ride.getRidesFrom());
                        values.put("ridesDriverCol", ride.getRidesDriver());
                        values.put("ridesCashCol", String.valueOf(ride.getRidesCash()));
                        values.put("ridesDriverIdCol", String.valueOf(ride.getRidesDriverId()));
                        values.put("ridesCarIdCol", String.valueOf(ride.getRidesCarId()));
                        values.put("ridesCarCol", String.valueOf(ride.getRidesCar()));
                        values.put("ridesToCol", String.valueOf(ride.getRidesTo()));
                        values.put("ridesCreditCol", String.valueOf(ride.getRidesCredit()));

                    TaxiApplication.showEditRideDialog(values);


                System.out.println(date);
            }
        });
    }
    // ------------------------------------------------------------------ //
    /**
     * Calculates the height of the rectangle in pixels depending on its ride's duration
     */
    private double calculateHeight() {

        //TODO: condition when end time is at next day, if duration is negative,
        // somehow inform day view that need to draw another rectangle
        Duration duration = ride.getDuration();
        if (duration == null) {
            return 1;
        }
        double minutes = duration.toMinutes();

        if(minutes < 0) {
            // fill till the end of day
            return DayView.SPACING /60 * 1440 - calculateRecY() - 2 * DayView.ADJFACTSPACING;
        }

        return DayView.SPACING /60 * minutes;

    }

    // ------------------------------------------------------------------ //
    /**
     * Find the starting Y position for the rectangle in pixels according to its ride's
     * staring time
     */
    private double calculateRecY() {
        double minutes = 0;
        if (ride.getStartDate() != null) {
            LocalTime time = ride.getStartDate().toLocalTime();

            minutes += time.getHour() * 60 + time.getMinute();
        }


        return DayView.SPACING / 60 * minutes - DayView.ADJFACTSPACING;
    }

    // ------------------------------------------------------------------ //
    /**
     * Gets the color from the driver, reduces its opacity and colors the rectangle
     * staring time
     */
    private void setColor() {
        String colorst =  ride.getDriver().getDriver_color().substring(0,8);
        String opacityValue = "80";
        Color color = Color.valueOf(colorst + opacityValue);
        rectangle.setFill(color);

    }

    //endregion
    // ------------------------------------------------------------------ //


}
