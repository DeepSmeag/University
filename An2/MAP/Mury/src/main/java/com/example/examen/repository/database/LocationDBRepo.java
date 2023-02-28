package com.example.examen.repository.database;

import com.example.examen.domain.entities.Location;
import com.example.examen.domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDBRepo extends AbstractDBRepo<Double, Location> {

    public LocationDBRepo(String dbLink, String username, String password, Validator<Location> validator) {
        super(dbLink, username, password, validator);
    }

    @Override
    public List<Location> loadAllData() {
        List<Location> locations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"locations\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("locationId");
                String locationName = resultSet.getString("locationName");
                Location location = new Location(id, locationName);

                locations.add(location);
            }
            return locations;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Locations to be selected\n");
        }
    }

    @Override
    public Location extractEntity(Double entityId) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"locations\" where \"locationId\" = " + entityId.toString())) {
//            ps.setString(1, entityId.toString());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            Double id = resultSet.getDouble("locationId");
            String locationName = resultSet.getString("locationName");
            Location location = new Location(id, locationName);

            return location;
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("\n\tThere is no Location to be selected\n");
        }
    }

}
