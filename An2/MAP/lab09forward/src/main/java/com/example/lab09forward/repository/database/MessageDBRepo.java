package com.example.lab09forward.repository.database;

import com.example.lab09forward.domain.Entities.Friendship;
import com.example.lab09forward.domain.Entities.Message;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageDBRepo extends AbstractDBRepo<UUID, Message> {
    public MessageDBRepo(String dbLink, String username, String password, Validator<Message> validator) {
        super(dbLink, username, password, validator);
    }

    @Override
    public List<Message> loadAllData() {

        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Messages\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("uid"));
                UUID user1ID = UUID.fromString(resultSet.getString("user1ID"));
                UUID user2ID = UUID.fromString(resultSet.getString("user2ID"));
                String messageText = resultSet.getString("message");
                LocalDate sentDate = resultSet.getDate("sentDate").toLocalDate();
                Message message = new Message(user1ID, user2ID, messageText, sentDate);
                message.setId(id);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Users to be selected\n");
        }

    }

    public List<Message> findSentBy(UUID id) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Messages\" where \"user1ID\" = ? order by \"sentTime\" desc")) {
            ps.setString(1, id.toString());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                UUID id1 = UUID.fromString(resultSet.getString("uid"));
//                UUID user1ID = UUID.fromString(resultSet.getString("user1ID"));
                UUID user2ID = UUID.fromString(resultSet.getString("user2ID"));
                String messageText = resultSet.getString("message");
                LocalDate sentDate = resultSet.getDate("sentDate").toLocalDate();
                Message message = new Message(id, user2ID, messageText, sentDate);
                message.setId(id1);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Users to be selected\n");
        }
    }

    public List<Message> findReceivedBy(UUID id) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Messages\" where \"user2ID\" = ? order by \"sentTime\" desc")) {
            ps.setString(1, id.toString());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                UUID id1 = UUID.fromString(resultSet.getString("uid"));
                UUID user1ID = UUID.fromString(resultSet.getString("user1ID"));
//                UUID user2ID = UUID.fromString(resultSet.getString("user2ID"));
                String messageText = resultSet.getString("message");
                LocalDate sentDate = resultSet.getDate("sentDate").toLocalDate();
                Message message = new Message(user1ID, id, messageText, sentDate);
                message.setId(id1);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Users to be selected\n");
        }
    }

    @Override
    public Message extractEntity(UUID entityId) {
        return null;
    }


    public void saveMessage(Message message) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO \"Messages\" (\"uid\", \"user1ID\", \"user2ID\", \"message\", \"sentDate\") VALUES (" +
                "'" + message.getId().toString() + "'" +
                ", " +
                "'"  + message.getId1().toString() + "'" +
                ", " +
                "'"  + message.getId2().toString() + "'" +
                ", " +
                "'"  + message.getMessage() + "'" +
                ", " +
                "'"  + message.getDate().toString() + "'" +
                ");");
        super.execQueryNoResult(sql.toString());
    }

}
