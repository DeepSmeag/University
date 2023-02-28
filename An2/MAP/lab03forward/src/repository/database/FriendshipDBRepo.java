package socialnetwork.repository.database;

import socialnetwork.domain.Entities.Friendship;
import socialnetwork.domain.exceptions.RepoException;
import socialnetwork.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendshipDBRepo extends AbstractDBRepo<UUID, Friendship> {
    public FriendshipDBRepo(String dbLink, String username, String password, Validator<Friendship> validator) {
        super(dbLink, username, password, validator);
    }

    @Override
    public List<Friendship> loadAllData() {
        List<Friendship> friendships = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Friendships\"")) {
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("uid"));
                UUID id1 = UUID.fromString(resultSet.getString("user1ID"));
                UUID id2 = UUID.fromString(resultSet.getString("user2ID"));
                LocalDate date = resultSet.getDate("date").toLocalDate();
                Boolean confirmed = resultSet.getBoolean("confirmed");
                Friendship friendship = new Friendship(id1, id2, date, confirmed);
                friendship.setId(id);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            throw new RuntimeException("\n\tThere are no Friendships to be selected\n");
        }
    }

    @Override
    public Friendship extractEntity(UUID entityId) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Friendships\" where uid = ?")) {
            ps.setString(1, entityId.toString());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            UUID id1 = UUID.fromString(resultSet.getString("user1ID"));
            UUID id2 = UUID.fromString(resultSet.getString("user2ID"));
            LocalDate date = resultSet.getDate("date").toLocalDate();
            Boolean confirmed = resultSet.getBoolean("confirmed");
            Friendship friendship = new Friendship(id1, id2, date, confirmed);
            friendship.setId(entityId);
            return friendship;

        } catch (SQLException e) {
            return null;
        }
    }


    public Friendship findByUUIDs(UUID id1, UUID id2) {
        try (Connection connection = DriverManager.getConnection(dbLink, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Friendships\" where (\"user1ID\" = ? and \"user2ID\" = ?) or (\"user1ID\" = ? and \"user2ID\" = ?)")) {
            ps.setString(1, id1.toString());
            ps.setString(2, id2.toString());
            ps.setString(3, id2.toString());
            ps.setString(4, id1.toString());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            UUID id = UUID.fromString(resultSet.getString("uid"));
            LocalDate date = resultSet.getDate("date").toLocalDate();
            Boolean confirmed = resultSet.getBoolean("confirmed");
            Friendship friendship = new Friendship(id1, id2, date, confirmed);
            friendship.setId(id);
            return friendship;

        } catch (SQLException e) {
            return null;
        }
    }

    public void saveFriendship(Friendship friendship) throws RepoException {
        validator.validate(friendship);
        StringBuilder sql = new StringBuilder();
        if (extractEntity(friendship.getId()) != null) {
            sql.append("UPDATE \"Friendships\" SET date = " +
                    "'"  + friendship.getDate().toString() + "'" +
                    ", confirmed = " +
                    "'"  + friendship.getConfirmed() + "'" +
                    "WHERE uid = " +
                    "'"  + friendship.getId().toString() + "'" +
                    ";");
        } else {
            sql.append("INSERT INTO \"Friendships\" (uid, \"user1ID\", \"user2ID\", date, confirmed) VALUES (" +
                    "'" + friendship.getId().toString() + "'" +
                    ", " +
                    "'"  + friendship.getId1().toString() + "'" +
                    ", " +
                    "'"  + friendship.getId2().toString() + "'" +
                    ", " +
                    "'"  + friendship.getDate().toString() + "'" +
                    ", " +
                    "'"  + friendship.getConfirmed() + "'" +
                    ");");
        }
        super.execQueryNoResult(sql.toString());
    }

    public void deleteFriendship(UUID id) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM \"Friendships\" WHERE uid = " +
                "'"  + id.toString() + "'" +
                ";");
        super.execQueryNoResult(sql.toString());
    }

    public void deleteUsersFriendship(UUID id) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM \"Friendships\" WHERE \"user1ID\" = " +
                "'"  + id.toString() + "'" +
                "or \"user2ID\" = " +
                "'"  + id.toString() + "'" +
                ";");
        super.execQueryNoResult(sql.toString());
    }
}
