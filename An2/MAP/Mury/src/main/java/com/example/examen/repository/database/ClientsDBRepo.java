package com.example.examen.repository.database;

import com.example.examen.domain.entities.*;
import com.example.examen.domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientsDBRepo extends AbstractDBRepo<Long, Client>{
    public ClientsDBRepo(String dbLink, String username, String password, Validator<Client> validator) {
        super(dbLink, username, password, validator);
    }
    @Override
    public List loadAllData() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"clients\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("clientid");
                String name = resultSet.getString("name");
                int fidelityGrade = resultSet.getInt("fidelitygrade");
                int varsta = resultSet.getInt("varsta");
                String hobby = resultSet.getString("hobbies");
                Client client = new Client(id, name, fidelityGrade, varsta, HobbyEnum.valueOf(hobby));

                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Hotels to be selected\n");
        }
    }

    @Override
    public Client extractEntity(Long entityId) {
        return null;
    }

}
