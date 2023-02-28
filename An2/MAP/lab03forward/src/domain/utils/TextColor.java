package socialnetwork.domain.utils;

/**
 * Utility class holding constant values for text color
 */
public interface TextColor {
    /**
     * ANSI escape code to reset text
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * ANSI escape code to set text color to black
     */
    public static final String ANSI_BLACK = "\u001B[30m";
    /**
     * ANSI escape code to set text color to red
     */
    public static final String ANSI_RED = "\u001B[31m";
    /**
     * ANSI escape code to set text color to green
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * ANSI escape code to set text color to yellow
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * ANSI escape code to set text color to blue
     */
    public static final String ANSI_BLUE = "\u001B[34m";
    /**
     * ANSI escape code to set text color to purple
     */
    public static final String ANSI_PURPLE = "\u001B[35m";
    /**
     * ANSI escape code to set text color to cyan
     */
    public static final String ANSI_CYAN = "\u001B[36m";
    /**
     * ANSI escape code to set text color to white
     */
    public static final String ANSI_WHITE = "\u001B[37m";
}
