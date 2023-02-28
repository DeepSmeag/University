package com.example.examen.repository.database;

import com.example.examen.domain.entities.Location;
import com.example.examen.domain.entities.SpecialOffer;
import com.example.examen.domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffersDBRepo extends AbstractDBRepo<Double, SpecialOffer> {
    public OffersDBRepo(String dbLink, String username, String password, Validator<SpecialOffer> validator) {
        super(dbLink, username, password, validator);
    }

    @Override
    public List<SpecialOffer> loadAllData() {
        List<SpecialOffer> offers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"specialoffers\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("specialofferid");
                Double hotelId = resultSet.getDouble("hotelid");
                Date startDate = resultSet.getDate("startdate");
                Date endDate = resultSet.getDate("enddate");
                Integer discount = resultSet.getInt("percents");
                SpecialOffer offer = new SpecialOffer(id, hotelId, startDate, endDate, discount);

                offers.add(offer);
            }
            return offers;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Locations to be selected\n");
        }
    }

    @Override
    public SpecialOffer extractEntity(Double entityId) {
        return null;
    }
}
