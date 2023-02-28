package com.example.examen.repository.database;

import com.example.examen.domain.entities.TestEntity;
import com.example.examen.domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDBRepo extends AbstractDBRepo<Integer, TestEntity> {
    /**
     * Constructor for InMemoryRepository0
     *
     * @param dbLink
     * @param username
     * @param password
     * @param validator - Validator
     */
    public TestDBRepo(String dbLink, String username, String password, Validator<TestEntity> validator) {
        super(dbLink, username, password, validator);
    }


    @Override
    public List<TestEntity> loadAllData() {
        List<TestEntity> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"testing\"")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String name = resultSet.getString("name");
                Date date = resultSet.getDate("date");

                TestEntity user = new TestEntity(id, name, date);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Users to be selected\n");
        }


    }

    @Override
    public TestEntity extractEntity(Integer entityId) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"testing\" where uid = ?")) {
            ps.setString(1, entityId.toString());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            Date date = resultSet.getDate("date");

            TestEntity user = new TestEntity(entityId, name, date);

            return user;
        } catch (SQLException e) {
            return null;
        }
    }
//    TODO: not to-do, but saving this for quick access
//    public void saveFriendship(Friendship friendship) throws RepoException {
//        validator.validate(friendship);
//        StringBuilder sql = new StringBuilder();
//        if (extractEntity(friendship.getId()) != null) {
//            sql.append("UPDATE \"Friendships\" SET date = " +
//                    "'"  + friendship.getDate().toString() + "'" +
//                    ", confirmed = " +
//                    "'"  + friendship.getConfirmed() + "'" +
//                    "WHERE uid = " +
//                    "'"  + friendship.getId().toString() + "'" +
//                    ";");
//        } else {
//            sql.append("INSERT INTO \"Friendships\" (uid, \"user1ID\", \"user2ID\", date, confirmed) VALUES (" +
//                    "'" + friendship.getId().toString() + "'" +
//                    ", " +
//                    "'"  + friendship.getId1().toString() + "'" +
//                    ", " +
//                    "'"  + friendship.getId2().toString() + "'" +
//                    ", " +
//                    "'"  + friendship.getDate().toString() + "'" +
//                    ", " +
//                    "'"  + friendship.getConfirmed() + "'" +
//                    ");");
//        }
//        super.execQueryNoResult(sql.toString());
//    }
}
