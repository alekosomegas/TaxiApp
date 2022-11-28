package com.ak.taxiapp.controller.client;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.controller.Controller;
import com.ak.taxiapp.model.client.Client;
import com.ak.taxiapp.model.client.ClientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientDbController extends Controller {
    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, Integer> clientIDCol;
    @FXML
    private TableColumn<Client, String> clientNameCol;
    @FXML
    private TableColumn<Client, String> clientAddressCol;
    @FXML
    private TableColumn<Client, String> clientEmailCol;
    @FXML
    private TableColumn<Client, String> clientTelCol;
    private Client selectedClient;

    @FXML
    public void selectClient() {
        selectedClient = clientsTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void deleteSelectedClient() {
        if(selectedClient != null) {
            String id = String.valueOf(selectedClient.getClient_id());
            try {
                ClientDAO.deleteClntWithId(id);
                rlc.setResultText("Client deleted! Client id: " + id + "\n");
                searchClients();
            } catch (SQLException e) {
                rlc.setResultText("Problem occurred while deleting employee " + e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Search all clients
    @FXML
    private void searchClients() throws SQLException, ClassNotFoundException {
        try {
            //Get all Clients information
            ObservableList<Client> clntData = ClientDAO.searchAllClients();
            //Populate Clients on TableView
            populateClients(clntData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Client objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe
        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */
        clientIDCol.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());
        clientNameCol.setCellValueFactory(cellData -> cellData.getValue().client_nameProperty());
        clientAddressCol.setCellValueFactory(cellData -> cellData.getValue().client_addressProperty());
        clientEmailCol.setCellValueFactory(cellData -> cellData.getValue().client_emailProperty());
        clientTelCol.setCellValueFactory(cellData -> cellData.getValue().client_telProperty());
        searchClients();
    }

    //Populate Client
    @FXML
    private void populateClient(Client clnt) {
        //Declare and ObservableList for table view
        ObservableList<Client> clntData = FXCollections.observableArrayList();
        //Add client to the ObservableList
        clntData.add(clnt);
        //Set items to the employeeTable
        clientsTable.setItems(clntData);
    }

    //Populate Clients for TableView
    @FXML
    private void populateClients(ObservableList<Client> clntData) {
        //Set items to the clientsTable
        clientsTable.setItems(clntData);
    }


    //Insert a client to the DB
    @FXML @Override
    public void insert() {
        int result = TaxiApplication.showNewClientDialog();
        if (result == 0) {
            rlc.setResultText("Client inserted! \n");
        } else rlc.setResultText("Problem occurred while inserting client ");

    }

    public void onEditClicked() {
        HashMap<String, String> values = new HashMap<>();
        if(selectedClient != null) {
            int id = selectedClient.getClient_id();
            ObservableList<TableColumn<Client,?>> columns = clientsTable.getColumns();
            for (TableColumn<Client, ?> column : columns) {
                values.put(column.getId(), column.getCellData(selectedClient).toString());
            }
        }
        TaxiApplication.showEditClientDialog(values);
    }
}