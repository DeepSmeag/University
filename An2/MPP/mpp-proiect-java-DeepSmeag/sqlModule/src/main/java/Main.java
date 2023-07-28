import java.io.FileReader;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
//        Do connection to postgres
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (Exception e) {

        }
        System.out.println(props.getProperty("dbLink"));
        try (Connection connection = DriverManager.getConnection(Config.dbLink, Config.username, Config.password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Users\"")) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Boolean deleted = resultSet.getBoolean("deleted");
                SocialUser socialUser = new SocialUser(firstName, lastName, deleted);
                System.out.println(socialUser.getFirstName() + " " + socialUser.getLastName() + " " + socialUser.isDeleted());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection(Config.dbLink, Config.username, Config.password);
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Users\" VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, "Ion");
            ps.setString(3, "Popescu");
            ps.setString(4, UUID.randomUUID().toString());
            ps.setBoolean(5, false);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection(Config.dbLink, Config.username, Config.password);
             PreparedStatement ps = connection.prepareStatement("UPDATE \"Users\" SET \"firstName\"=? WHERE \"firstName\"=?")) {
            ps.setString(1, "b");
            ps.setString(2, "a");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection(Config.dbLink, Config.username, Config.password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"firstName\"=?")) {
            ps.setString(1, "b");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Boolean deleted = resultSet.getBoolean("deleted");
                SocialUser socialUser = new SocialUser(firstName, lastName, deleted);
                System.out.println(socialUser.getFirstName() + " " + socialUser.getLastName() + " " + socialUser.isDeleted());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
