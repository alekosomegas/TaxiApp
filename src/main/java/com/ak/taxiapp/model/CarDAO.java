package com.ak.taxiapp.model;

import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class CarDAO {
    public static ObservableList<Car> searchCars() throws SQLException {
        String selectStatement = "SELECT * FROM cars";
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            return getCarList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Car> getCarList(ResultSet rs) throws SQLException {
        ObservableList<Car> ls = FXCollections.observableArrayList();
        while (rs.next()) {
            Car car = new Car();
            car.setCar_id(rs.getInt("CARS_ID"));
            car.setCar_reg(rs.getString("CARS_REG"));
            car.setCar_make(rs.getString("CARS_MAKE"));
            car.setCar_model(rs.getString("CARS_MODEL"));
            car.setCar_mileage(rs.getInt("CARS_MILEAGE"));

            ls.add(car);
        }
        return ls;
    }

    public static Car searchCarById(int carID) throws SQLException {
        String selectStatement = "SELECT * FROM cars WHERE CARS_ID=" + carID;
        // Execute SELECT statement
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rs = DBUtil.dbExecuteQuery(selectStatement);
            // Send ResultSet to the getClientFromResultSet method and get client object
            return getCarFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("While searching a car with " + carID + " id, an error occurred: " + e);
            throw e;
        }
    }

    private static Car getCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = null;
        if (rs.next()) {
            car = new Car();
            car.setCar_id(rs.getInt("CARS_ID"));
            car.setCar_reg(rs.getString("CARS_REG"));
            car.setCar_make(rs.getString("CARS_MAKE"));
            car.setCar_model(rs.getString("CARS_MODEL"));
            car.setCar_mileage(rs.getInt("CARS_MILEAGE"));
        }
        return car;
    }

    public static void deleteCarWithId(Integer carID) throws SQLException {
        //Declare a DELETE statement
        String updateStatement = "DELETE FROM cars WHERE CARS_ID = " + carID + ";";
        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void insertCar(String reg, String make, String model, Integer mileage) throws SQLException {
        String values = "'" +reg+ "', '" +make+ "', '" +model+ "', '" +mileage+ "'";
        String updateStatement = "INSERT INTO cars " +
                "(CARS_REG, CARS_MAKE, CARS_MODEL, CARS_MILEAGE) VALUES (" +values+ ");";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while INSERT operation: " + e);
            throw e;
        }
    }

    public static void updateCar(HashMap<String, String> values) throws SQLException {
        String updateStatement = "UPDATE cars SET " +
                "  CARS_MAKE = " + "'" +values.get("carsMakeCol")+ "'" +
                ", CARS_MODEL = " + "'" +values.get("carsModelCol")+ "'" +
                ", CARS_MILEAGE = " + "'" +values.get("carsMilCol")+ "'" +
                ", CARS_REG = " + "'" +values.get("carsRegCol")+ "'" +
                " WHERE CARS_ID = " + "'" +values.get("carsIdCol")+ "'" + ";";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while UPDATE operation. " + e);
            throw e;
        }
    }
}
