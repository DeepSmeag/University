package socialnetwork.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Class models Config type of entity
 */
public class Config {
    /**
     * properties class member
     */
    private Config() {}

//    public static String CONFIG_LOCATION = Config.class.getClassLoader()
//            .getResource("config.properties").getFile();
    /**
     * userFile class member
     */
    public static String usersFile = "users.csv";
    /**
     * friendshipsFile class member
     */
    public static String friendshipsFile = "friendships.csv";

    /**
     * dbLink class member
     */
    public static String dbLink = "jdbc:postgresql://localhost:5432/SocialNetwork";
    public static String username = "postgres";
    public static String password = "password";


//    public static Properties getProperties() {
//        Properties properties = new Properties();
//        try {
////            properties.load(new FileReader(CONFIG_LOCATION));
//            return properties;
//        } catch (IOException e) {
//            throw new RuntimeException("Cannot load config properties");
//        }
//    }
}
