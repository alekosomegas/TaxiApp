package com.ak.taxiapp.model.driver;

import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DriverDAO {
    public static ObservableList<Driver> searchDrivers() throws SQLException {
        String selectStatement = "SELECT * FROM drivers";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            return getDriverList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Driver> getDriverList(ResultSet rs) throws SQLException {
        ObservableList<Driver> ls = FXCollections.observableArrayList();
        while (rs.next()) {
            Driver driver = new Driver();
            driver.setDriver_id(rs.getInt("DRIVERS_ID"));
            driver.setDriver_name(rs.getString("DRIVERS_NAME"));
            driver.setDriver_car(rs.getInt("DRIVERS_CAR"));
            driver.setDriver_color(rs.getString("DRIVERS_COLOR"));

            ls.add(driver);
        }
        return ls;
    }

    public static Driver searchDriverById(String driverID) throws SQLException {
        String selectStatement = "SELECT * FROM drivers WHERE DRIVERS_ID=" + driverID;
        // Execute SELECT statement
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            // Send ResultSet to the getClientFromResultSet method and get client object
            return getDriverFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("While searching a driver with " + driverID + " id, an error occurred: " + e);
            throw e;
        }
    }

    private static Driver getDriverFromResultSet(ResultSet rs) throws SQLException {
        Driver driver = null;
        if (rs.next()) {
            driver = new Driver();
            driver.setDriver_id(rs.getInt("DRIVERS_ID"));
            driver.setDriver_name(rs.getString("DRIVERS_NAME"));
            driver.setDriver_car(rs.getInt("DRIVERS_CAR"));
            driver.setDriver_color(rs.getString("DRIVERS_COLOR"));
        }
        return driver;
    }

    public static void deleteDriverWithId(Integer driverID) throws SQLException {
        //Declare a DELETE statement
        String updateStatement = "DELETE FROM drivers WHERE DRIVERS_ID = " + driverID + ";";
        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
    public static void insert(String name, Integer carId, String color) throws SQLException {
        String values = "'" +name+ "', '" +carId+ "', '" +color+ "'";
        String updateStatement = "INSERT INTO drivers " +
                "(DRIVERS_NAME, DRIVERS_CAR, DRIVERS_COLOR) VALUES (" +values+ ");";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while INSERT operation: " + e);
            throw e;
        }
    }

    public static void update(HashMap<String, String> values) throws SQLException {
        String updateStatement = "UPDATE drivers SET " +
                "  DRIVERS_NAME = " + "'" +values.get("driversNameCol")+ "'" +
                ", DRIVERS_CAR = " + "'" +values.get("driversCarCol")+ "'" +
                ", DRIVERS_COLOR = " + "'" +values.get("driversColorCol")+ "'" +
                " WHERE DRIVERS_ID = " + "'" +values.get("driverIdCol")+ "'" + ";";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while UPDATE operation. " + e);
            throw e;
        }
    }
}
