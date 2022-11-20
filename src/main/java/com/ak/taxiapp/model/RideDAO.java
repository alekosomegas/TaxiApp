package com.ak.taxiapp.model;

import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RideDAO {

    private static ObservableList<Ride> getRidesList(ResultSet rs) throws SQLException {
        //Declare an observable List which comprises of Client objects
        ObservableList<Ride> ridesList = FXCollections.observableArrayList();

        while (rs.next()) {
            Ride rd = new Ride();
            rd.setRides_id(rs.getInt("RIDES_ID"));
            rd.setRidesDate(rs.getString("RIDES_DATE"));
            rd.setRidesTimeStart(rs.getString("RIDES_TIME_START"));
            rd.setRidesTimeEnd(rs.getString("RIDES_TIME_END"));
            rd.setRidesClientId(rs.getInt("RIDES_CLIENT_ID"));
            rd.setRidesDriverId(rs.getInt("RIDES_DRIVER_ID"));
            rd.setRidesCarId(rs.getInt("RIDES_CAR_ID"));
            rd.setRidesFrom(rs.getString("RIDES_FROM"));
            rd.setRidesStops(rs.getString("RIDES_STOPS"));
            rd.setRidesTo(rs.getString("RIDES_TO"));
            rd.setRidesCash(rs.getInt("RIDES_CASH"));
            rd.setRidesStatus(rs.getInt("RIDES_STATUS"));
            rd.setRidesCredit(rs.getInt("RIDES_CREDIT"));
            rd.setRidesPaid(rs.getInt("RIDES_PAID"));

            if (ClientDAO.searchClientById(String.valueOf(rd.getRidesClientId())) != null) {
                rd.setRidesClient(ClientDAO.searchClientById(String.valueOf(rd.getRidesClientId())).getClient_name());
            }
            if (DriverDAO.searchDriverById(rd.getRidesClientId()) != null) {
                rd.setRidesDriver(DriverDAO.searchDriverById(rd.getRidesDriverId()).getDriver_name());
            }
            rd.setRidesCar(CarDAO.searchCarById(rd.getRidesCarId()).getCar_reg());
            rd.setRidesDuration(calculateDuration(rd));
            rd.setRidesTotal(rd.getRidesCash() + rd.getRidesCredit());
            //Add client to ObservableList
            ridesList.add(rd);
        }
        return ridesList;
    }

    private static String calculateDuration(Ride rd) {

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("HH:mm");

        try {
            Date timeStart = simpleDateFormat.parse(rd.getRidesTimeStart());
            Date timeEnd = simpleDateFormat.parse(rd.getRidesTimeEnd());

            // Calculating the difference in milliseconds
            long differenceInMilliSeconds
                    = Math.abs(timeEnd.getTime() - timeStart.getTime());
            long differenceInHours
                    = (differenceInMilliSeconds / (60 * 60 * 1000))
                    % 24;
            long differenceInMinutes
                    = (differenceInMilliSeconds / (60 * 1000)) % 60;

            return differenceInHours +"h"+ differenceInMinutes +"m";

        } catch (ParseException e) {
            System.out.println("RIDEDAO - Something went wrong when parsing time");
        }
        return "ERROR";
    }


    public static ObservableList<Ride> searchRides() throws SQLException {
        String selectStatement = "SELECT * FROM rides";
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

    /// ??????//////??????????
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
// update !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static void insertRide(String ridesDate, String ridesTimeStart, String ridesTimeEnd,
                                  Integer ridesClientId, Integer ridesDriverId, Integer ridesCarId,
                                  String ridesFrom, String ridesStops, String ridesTo,
                                  Integer ridesCash,Integer ridesStatus, Integer ridesCredit,
                                  Integer ridesPaid, String ridesNotes, Integer ridesPaidd) throws SQLException {
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
                            +ridesStatus+ "', '"
                            +ridesCredit+ "', '"
                            +ridesPaid+ "', '"
                            +ridesNotes+ "', '"
                            +ridesPaidd+ "'";


        String updateStatement =
                "INSERT INTO rides (RIDES_DATE,RIDES_TIME_START,RIDES_TIME_END,RIDES_CLIENT_ID,RIDES_DRIVER_ID," +
                        "RIDES_CAR_ID,RIDES_FROM,RIDES_STOPS,RIDES_TO,RIDES_CASH,RIDES_STATUS,RIDES_CREDIT," +
                        "RIDES_PAID,RIDES_NOTES,RIDES_PAIDD)" +
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
                ", RIDES_STATUS = " + "'" +values.get("ridesStatusCol")+ "'" +
                ", RIDES_CAR_ID = " + "'" +values.get("ridesCarIdCol")+ "'" +
                ", RIDES_TO = " + "'" +values.get("ridesToCol")+ "'" +
                ", RIDES_STOPS = " + "'" +values.get("ridesStopsCol")+ "'" +
                ", RIDES_CREDIT = " + "'" +values.get("ridesCreditCol")+ "'" +
                ", RIDES_PAID = " + "'" +values.get("ridesPaidCol")+ "'" +
                ", RIDES_PAIDD = " + "'" +values.get("ridesPaiddCol")+ "'" +
                ", RIDES_NOTES = " + "'" +values.get("ridesNotesCol")+ "'" +
                " WHERE RIDES_ID = " + "'" +values.get("ridesIdCol")+ "'" + ";";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while UPDATE operation. " + e);
            throw e;
        }
    }
}
