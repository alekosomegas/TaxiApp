package com.ak.taxiapp.model.ride;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.calendar.CalendarModel;
import com.ak.taxiapp.model.car.CarDAO;
import com.ak.taxiapp.model.client.ClientDAO;
import com.ak.taxiapp.model.driver.DriverDAO;
import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

//endregion
// ------------------------------------------------------------------ //

public class RideDAO {

    // ------------------------------------------------------------------ //
    //region// ------------------------ MAIN METHODS ----------------------- //

    /**
     * Creates a new Ride object and sets its values from the Result set taken
     * from the SQL table
     * @param resultSet Data obtained from the database after a query, i.e. SELECT * FROM rides
     * @return A list of all the rides created from the result set
     */
    private static ObservableList<Ride> getRidesList(ResultSet resultSet) throws SQLException {
        //Declare an observable List which consist of Rides objects
        ObservableList<Ride> ridesList = FXCollections.observableArrayList();
        // goes through all the data/rows in the result set
        while (resultSet.next()) {
            // creates a new ride
            Ride ride = new Ride();
            // populates it with values taken from the columns of the result set
            setRideValuesFromRsData(resultSet, ride);

            if (ClientDAO.searchClientById(String.valueOf(ride.getRidesClientId())) != null) {
                ride.setRidesClient(ClientDAO.searchClientById(String.valueOf(ride.getRidesClientId())).getClient_name());
            }
            if (DriverDAO.searchDriverById(String.valueOf(ride.getRidesDriverId())) != null) {
                ride.setRidesDriver(DriverDAO.searchDriverById(String.valueOf(ride.getRidesDriverId())).getDriver_name());
            }
            ride.setRidesCar(CarDAO.searchCarById(ride.getRidesCarId()).getCar_reg());
            ride.setRidesDuration(calculateDuration(ride));
            ride.setRidesTotal(ride.getRidesCash() + ride.getRidesCredit());
            //Add client to ObservableList
            ridesList.add(ride);

            ride.setDriver(
                    DriverDAO.searchDriverById(String.valueOf(ride.getRidesDriverId()))
            );
            ride.setDate(LocalDate.parse(ride.getRidesDate()));

        }
        return ridesList;
    }



    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ---------------------- SQL QUERIES METHODS ----------------------- //

    /**
     * Selects all the data/rows(result set) from the rides' table in the database.
     * Calls the getRidesList to create a ride object for each result of the SQL query.
     * @return A list of all the ride objects created
     */
    public static ObservableList<Ride> searchAllRides() throws SQLException {
        String selectStatement = "SELECT * FROM rides";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            //Send ResultSet to the getClientList method and get rides objects and
            //return the list of all rides
            return getRidesList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    public static ObservableList<Ride> searchRidesByClientId(int clientID) throws SQLException {
        String selectStatement = "SELECT * FROM rides WHERE RIDES_CLIENT_ID = " + clientID;
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            //Send ResultSet to the getClientList method and get client object
            ObservableList<Ride> ridesList = getRidesList(rs);

            return ridesList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }


    public static ObservableList<Ride> searchRidesByClientName(String clientName) throws SQLException {
        String selectStatement = "SELECT * FROM rides WHERE RIDES_CLIENT_ID = " + "'" +clientName+"'";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            //Send ResultSet to the getClientList method and get client object
            ObservableList<Ride> ridesList = getRidesList(rs);

            return ridesList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    public static ObservableList<Ride> searchRidesByDate(String date) throws SQLException {
        String selectStatement = "SELECT * FROM rides WHERE RIDES_DATE = " + "'" +date+"'";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            //Send ResultSet to the getClientList method and get client object
            ObservableList<Ride> ridesList = getRidesList(rs);

            return ridesList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }


    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// -------------------------- SQL METHODS --------------------------- //

    public static void insert(String ridesDate, String ridesTimeStart, String ridesTimeEnd,
                              Integer ridesClientId, Integer ridesDriverId, Integer ridesCarId,
                              String ridesFrom, String ridesStops, String ridesTo,
                              Integer ridesCash, Integer ridesCredit,
                              String ridesNotes, String ridesPassenger) throws SQLException {

        String values = "'" +ridesDate+ "', '"
                            +ridesTimeStart+ "', '"
                            +ridesTimeEnd+ "', '"
                            +ridesClientId+ "', '"
                            +ridesDriverId+ "', '"
                            +ridesCarId+ "', '"
                            +ridesFrom+ "', '"
                            +ridesStops+ "', '"
                            +ridesTo+ "', '"
                            +ridesCash+ "', '"
                            +ridesCredit+ "', '"
                            +ridesNotes+ "', '"
                            +ridesPassenger+ "'";

        String updateStatement =
                "INSERT INTO rides (RIDES_DATE, RIDES_TIME_START, RIDES_TIME_END, " +
                        "RIDES_CLIENT_ID, RIDES_DRIVER_ID, RIDES_CAR_ID, RIDES_FROM," +
                        "RIDES_STOPS, RIDES_TO, RIDES_CASH, RIDES_CREDIT," +
                        "RIDES_NOTES, RIDES_PASSENGER)" +
                        " VALUES (" +values+ ");";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while INSERT operation: " + e);
            throw e;
        }
    }

    public static void deleteRideWithId(Integer rideID) throws SQLException {
        //Declare a DELETE statement
        String updateStatement = "DELETE FROM rides WHERE RIDES_ID = " + rideID + ";";
        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void update(HashMap<String, String> values) throws SQLException {
        String updateStatement = "UPDATE rides SET " +
                "  RIDES_DATE = " + "'" +values.get("ridesDateCol")+ "'" +
                ", RIDES_TIME_START = " + "'" +values.get("ridesStartCol")+ "'" +
                ", RIDES_TIME_END = " + "'" +values.get("ridesEndCol")+ "'" +
                ", RIDES_CLIENT_ID = " + "'" +values.get("ridesClientIdCol")+ "'" +
                ", RIDES_FROM = " + "'" +values.get("ridesFromCol")+ "'" +
                ", RIDES_CASH = " + "'" +values.get("ridesCashCol")+ "'" +
                ", RIDES_DRIVER_ID = " + "'" +values.get("ridesDriverIdCol")+ "'" +
                ", RIDES_CAR_ID = " + "'" +values.get("ridesCarIdCol")+ "'" +
                ", RIDES_TO = " + "'" +values.get("ridesToCol")+ "'" +
                ", RIDES_STOPS = " + "'" +values.get("ridesStopsCol")+ "'" +
                ", RIDES_CREDIT = " + "'" +values.get("ridesCreditCol")+ "'" +
                ", RIDES_PASSENGER = " + "'" +values.get("ridesPassengerCol")+ "'" +
                ", RIDES_NOTES = " + "'" +values.get("ridesNotesCol")+ "'" +
                " WHERE RIDES_ID = " + "'" +values.get("ridesIdCol")+ "'" + ";";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while UPDATE operation. " + e);
            throw e;
        }
    }

    //endregion
    // ------------------------------------------------------------------ //

    // ------------------------------------------------------------------ //
    //region// ------------------------- HELPER METHODS ------------------------- //

    /**
     * Populates the ride object with values taken from the columns of the result set
     * @param resultSet Data obtained from the database after a query, i.e. SELECT * FROM rides
     * @param ride Ride object holding all the information about a ride
     */
    private static void setRideValuesFromRsData(ResultSet resultSet, Ride ride) throws SQLException {
        ride.setRides_id(resultSet.getInt("RIDES_ID"));
        ride.setRidesDate(resultSet.getString("RIDES_DATE"));
        ride.setRidesTimeStart(resultSet.getString("RIDES_TIME_START"));
        ride.setRidesTimeEnd(resultSet.getString("RIDES_TIME_END"));
        ride.setRidesClientId(resultSet.getInt("RIDES_CLIENT_ID"));
        ride.setRidesDriverId(resultSet.getInt("RIDES_DRIVER_ID"));
        ride.setRidesCarId(resultSet.getInt("RIDES_CAR_ID"));
        ride.setRidesFrom(resultSet.getString("RIDES_FROM"));
        ride.setRidesStops(resultSet.getString("RIDES_STOPS"));
        ride.setRidesTo(resultSet.getString("RIDES_TO"));
        ride.setRidesCash(resultSet.getInt("RIDES_CASH"));
        ride.setRidesCredit(resultSet.getInt("RIDES_CREDIT"));
        ride.setRidesNotes(resultSet.getString("RIDES_NOTES"));
        ride.setRidesPassenger(resultSet.getString("RIDES_PASSENGER"));
    }

    private static String calculateDuration(Ride rd) {

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("HH:mm");

        SimpleDateFormat simpleDateFormat1
                = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date timeStart = simpleDateFormat.parse(rd.getRidesTimeStart());
            Date timeEnd = simpleDateFormat.parse(rd.getRidesTimeEnd());

            Date t1 = simpleDateFormat1.parse(rd.getRidesDate());

            Date t2 = simpleDateFormat1.parse(rd.getRidesDate());


            LocalDate start = CalendarModel.convertDate(t1);
            LocalDate endTest = CalendarModel.convertDate(t2);
            LocalDate end;

            if (endTest.isBefore(start)) {
                end = endTest.plusDays(1);
            } else { end = endTest;}

            LocalDateTime st = start.atTime(timeStart.getHours(), timeStart.getMinutes());
            LocalDateTime en = end.atTime(timeEnd.getHours(), timeEnd.getMinutes());

            Duration duration = Duration.between(st, en);

            rd.setStartDate(st);
            rd.setEndDate(en);
            rd.setDuration(duration);


            // Calculating the difference in milliseconds
            long differenceInMilliSeconds
                    = Math.abs(timeEnd.getTime() - timeStart.getTime());
            long differenceInHours
                    = (differenceInMilliSeconds / (60 * 60 * 1000))
                    % 24;
            long differenceInMinutes
                    = (differenceInMilliSeconds / (60 * 1000)) % 60;

            String zeroH = "";
            String zeroM = "";
            if (differenceInHours < 10) {zeroH = "0";}
            if (differenceInMinutes < 10) {zeroM = "0";}

            String stDuration = zeroH + differenceInHours +"h"
                    + zeroM + differenceInMinutes +"m";

            return stDuration;

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("RIDEDAO - Something went wrong when parsing time");
        }
        return "ERROR";
    }

    public static ObservableList<Ride> searchById(String ridesId) throws SQLException {
        String selectStatement = "SELECT * FROM rides WHERE RIDES_ID = " + "'" +ridesId+"'";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            //Send ResultSet to the getClientList method and get client object
            ObservableList<Ride> ridesList = getRidesList(rs);

            return ridesList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    //endregion
    // ------------------------------------------------------------------ //


}
