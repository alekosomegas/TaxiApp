package com.ak.taxiapp.model.client;

import com.ak.taxiapp.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientDAO {
    // Select a client
    public static Client searchClientById(String clientID) throws SQLException {
        String selectStatement = "SELECT * FROM clients WHERE CLIENTS_ID=" + clientID;
        // Execute SELECT statement
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rsClient = DBUtil.dbExecuteQuery(selectStatement);
            // Send ResultSet to the getClientFromResultSet method and get client object
            return getClientFromResultSet(rsClient);
        } catch (SQLException e) {
            System.out.println("While searching a client with " + clientID + " id, an error occurred: " + e);
            throw e;
        }
    }

    public static Client searchClientByName(String clientName) throws SQLException {
        String selectStatement = "SELECT * FROM clients WHERE CLIENTS_NAME = " + "'" +clientName+ "'";
        // Execute SELECT statement
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rsClient = DBUtil.dbExecuteQuery(selectStatement);
            // Send ResultSet to the getClientFromResultSet method and get client object
            return getClientFromResultSet(rsClient);
        } catch (SQLException e) {
            System.out.println("While searching a client with " + clientName + " name, an error occurred: " + e);
            throw e;
        }
    }
    // Use ResultSet from DB as parameter and set Client Object's attributes and return client object.
    private static Client getClientFromResultSet(ResultSet rs) throws SQLException {
        Client client = null;
        if (rs.next()) {
            client = new Client();
            client.setClient_id(rs.getInt("CLIENTS_ID"));
            client.setClient_name(rs.getString("CLIENTS_NAME"));
            client.setClient_address(rs.getString("CLIENTS_ADDRESS"));
            client.setClient_email(rs.getString("CLIENTS_EMAIL"));
            client.setClient_tel(rs.getString("CLIENTS_TEL"));
        }
        return client;
    }

    // SELECT Client
    public static ObservableList<Client> searchAllClients() throws SQLException {
        String selectStatement = "SELECT * FROM clients";
        try {
            ResultSet rsClnt = DBUtil.dbExecuteQuery(selectStatement);
            //Send ResultSet to the getClientList method and get client object
            return getClientList(rsClnt);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }
    public static ObservableList<String> getClientsNamesList() throws SQLException {
        String selectStatement = "SELECT * FROM clients";
        try {
            ResultSet rsClnt = DBUtil.dbExecuteQuery(selectStatement);
            ObservableList<String> clientsNamesList = FXCollections.observableArrayList();;
            while (rsClnt.next()) {
                clientsNamesList.add(rsClnt.getString("CLIENTS_NAME"));
            }
            return clientsNamesList;

        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        }
    }

    // Select * from clients operation
    private static ObservableList<Client> getClientList(ResultSet rs) throws SQLException {
        //Declare an observable List which comprises of Client objects
        ObservableList<Client> clientList = FXCollections.observableArrayList();

        while (rs.next()) {
            Client client = new Client();
            client.setClient_id(rs.getInt("CLIENTS_ID"));
            client.setClient_name(rs.getString("CLIENTS_NAME"));
            client.setClient_address(rs.getString("CLIENTS_ADDRESS"));
            client.setClient_email(rs.getString("CLIENTS_EMAIL"));
            client.setClient_tel(rs.getString("CLIENTS_TEL"));
            //Add client to ObservableList
            clientList.add(client);
        }
        return clientList;
    }

    // UPDATE client's email

    // DELETE a client
    public static void deleteClntWithId(String clientId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStatement = "DELETE FROM clients WHERE CLIENTS_ID = " + clientId + ";";
        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
    // INSERT a client
    public static void insert(String name, String address, String email, String tel) throws SQLException {
        String values = "'" +name+ "', '" +address+ "', '" +email+ "', '" +tel+ "'";
        String updateStatement = "INSERT INTO clients " +
                "(CLIENTS_NAME, CLIENTS_ADDRESS, CLIENTS_EMAIL, CLIENTS_TEL) VALUES (" +values+ ");";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while INSERT operation: " + e);
            throw e;
        }
    }

    public static void update(HashMap<String, String> values) throws SQLException {
        String updateStatement = "UPDATE clients SET " +
                "  CLIENTS_NAME = " + "'" +values.get("clientNameCol")+ "'" +
                ", CLIENTS_ADDRESS = " + "'" +values.get("clientAddressCol")+ "'" +
                ", CLIENTS_EMAIL = " + "'" +values.get("clientEmailCol")+ "'" +
                ", CLIENTS_TEL = " + "'" +values.get("clientTelCol")+ "'" +
                " WHERE CLIENTS_ID = " + "'" +values.get("clientIDCol")+ "'" + ";";
        try {
            DBUtil.dbExecuteUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Error occurred while UPDATE operation. " + e);
            throw e;
        }
    }
}
