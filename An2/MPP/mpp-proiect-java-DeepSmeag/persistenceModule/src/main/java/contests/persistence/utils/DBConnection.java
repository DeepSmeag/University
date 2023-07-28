package contests.persistence.utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static String url;
    private static String user;
    private static String password;
    public static Connection getNewConnection() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("db.config"));
            url = props.getProperty("url");
            user = props.getProperty("username");
            password = props.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection " + e);
        }
        return null;
    }

}
