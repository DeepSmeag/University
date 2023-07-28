import java.util.logging.Logger;

public class LogManager {
    private static final Logger logger = Logger.getLogger(LogManager.class.getName());
    public static void log(String message) {
        logger.info(message);
    }
}
