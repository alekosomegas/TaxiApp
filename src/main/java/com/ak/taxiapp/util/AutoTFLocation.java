package com.ak.taxiapp.util;

import com.ak.taxiapp.model.ride.RideDAO;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

public class AutoTFLocation extends AutoCompleteTextField {
    private static SortedSet<String> locations = new TreeSet<>();
    public AutoTFLocation() {
        super();

        try {
            if(locations.isEmpty()) {getEntries().addAll(RideDAO.searchAllLocations());}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
