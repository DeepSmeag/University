package com.example.lab09forward.repository.database;

import com.example.lab09forward.domain.Entities.Friendship;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.domain.exceptions.RepoException;
import com.example.lab09forward.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepo extends AbstractDBRepo<UUID, User> {


    public UserDBRepo(String dbLink, String username, String password, Validator<User> validator) {
        super(dbLink, username, password, validator);
    }

    @Override
    public List<User> loadAllData() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Users\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getBoolean("deleted")) {
                    UUID id = UUID.fromString(resultSet.getString("uid"));
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    Boolean deleted = resultSet.getBoolean("deleted");
                    User user = new User(firstName, lastName, deleted);
                    user.setId(id);
                    users.add(user);
                }
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Users to be selected\n");
        }


    }

    @Override
    public User extractEntity(UUID entityId) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Users\" where uid = ?")) {
            ps.setString(1, entityId.toString());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            Boolean deleted = resultSet.getBoolean("deleted");
            User user = new User(firstName, lastName, deleted);
            user.setId(entityId);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }


    public void saveUser(User entity) {
        validator.validate(entity);
        StringBuilder sql = new StringBuilder();
        if (extractEntity(entity.getId()) != null) {
            sql.append("UPDATE \"Users\" SET \"firstName\" = " +
                    "'" + entity.getFirstName() + "'" +
                    ", \"lastName\" = " +
                    "'" + entity.getLastName() + "'" +
                    ", deleted = " +
                    "'" + entity.getDeleted() + "'" +
                    " WHERE uid = " +
                    "'" + entity.getId() + "';");
        } else {
            sql.append("INSERT INTO \"Users\" (uid, \"firstName\", \"lastName\", deleted) VALUES (" +
                    "'" + entity.getId() + "'" +
                    ", " +
                    "'" + entity.getFirstName() + "'" +
                    ", " +
                    "'" + entity.getLastName() + "'" +
                    ", " +
                    "'" + entity.getDeleted() + "'" +
                    ");");
        }

        super.execQueryNoResult(sql.toString());
    }

    public User findByName(String firstName, String lastName) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT * FROM \"Users\" where \"firstName\" = " +
                             "'" + firstName + "'" +
                             " and \"lastName\" = " +
                             "'" + lastName + "'")) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            UUID id = UUID.fromString(resultSet.getString("uid"));
            Boolean deleted = resultSet.getBoolean("deleted");
            User user = new User(firstName, lastName, deleted);
            user.setId(id);
            return user;

        } catch (SQLException e) {
            return null;
        }
    }

    public void deleteUser(UUID id) throws RepoException {
        User user = extractEntity(id);
        if (user != null && !user.getDeleted()) {
            user.setDeleted(true);
            saveUser(user);
            return;
        }
        throw new RepoException("\n\tUser does not exist; cannot delete.\n");
    }
}