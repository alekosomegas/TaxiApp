package com.ak.taxiapp.util;

import java.sql.*;

public class DBUtil {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/taxi";
    private static final String DATABASE_USERNAME  = "root";
    private static final String DATABASE_PASSWORD  = "2121";

    private static Connection conn = null;

    public static void dbConnect() throws SQLException {
        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ResultSet dbExecuteQuery(String queryStatement) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            dbConnect();
            // Create statement
            statement = conn.createStatement();
            // Execute select query operation
            resultSet = statement.executeQuery(queryStatement);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
//            if (resultSet != null) {
//                resultSet.close();
//            }
//            if (statement != null) {
//                statement.close();
//            }
//            dbDisconnect();
        }
        return resultSet;
    }

    public static void dbExecuteUpdate(String sqlStatement) throws SQLException {
        Statement statement = null;
        try {
            dbConnect();
            statement = conn.createStatement();
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            System.out.println(sqlStatement);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            dbDisconnect();
        }
    }



}
