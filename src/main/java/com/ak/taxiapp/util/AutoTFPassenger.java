package com.ak.taxiapp.util;

import com.ak.taxiapp.model.ride.RideDAO;

import java.sql.SQLException;

public class AutoTFPassenger extends AutoCompleteTextField{
    public AutoTFPassenger() {
        super();

        loadEntries();
    }

    private void loadEntries() {
        try {
            getEntries().addAll(RideDAO.searchAllPassengers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
