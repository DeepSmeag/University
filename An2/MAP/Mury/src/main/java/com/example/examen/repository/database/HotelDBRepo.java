package com.example.examen.repository.database;

import com.example.examen.domain.entities.Hotel;
import com.example.examen.domain.entities.Location;
import com.example.examen.domain.entities.TypeEnum;
import com.example.examen.domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDBRepo extends AbstractDBRepo<Double, Hotel>{
    public HotelDBRepo(String dbLink, String dbUser, String dbPassword, Validator<Hotel> validator) {
        super(dbLink, dbUser, dbPassword, validator);
    }

    @Override
    public List<Hotel> loadAllData() {
        List<Hotel> hotels = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"hotels\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Double id = Double.parseDouble(resultSet.getString("hotelId"));
                Double locationId = Double.parseDouble(resultSet.getString("locationId"));
                String hotelName = resultSet.getString("hotelName");
                int noRooms = Integer.parseInt(resultSet.getString("noRooms"));
                double pricePerNight = Double.parseDouble(resultSet.getString("pricePerNight"));
                TypeEnum type = TypeEnum.valueOf(resultSet.getString("type"));
                Hotel hotel = new Hotel(id, locationId, hotelName, noRooms, pricePerNight, type);

                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Hotels to be selected\n");
        }
    }

    @Override
    public Hotel extractEntity(Double entityId) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"hotels\" where hotelId = ?")) {
            ps.setString(1, entityId.toString());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            Double id = Double.parseDouble(resultSet.getString("hotelId"));
            Double locationId = Double.parseDouble(resultSet.getString("locationId"));
            String hotelName = resultSet.getString("hotelName");
            int noRooms = Integer.parseInt(resultSet.getString("noRooms"));
            double pricePerNight = Double.parseDouble(resultSet.getString("pricePerNight"));
            TypeEnum type = TypeEnum.valueOf(resultSet.getString("type"));
            Hotel hotel = new Hotel(id, locationId, hotelName, noRooms, pricePerNight, type);

            return hotel;
        } catch (
                SQLException e) {
            throw new RuntimeException("\n\tThere is no Hotel to be selected\n");
        }
    }


}
