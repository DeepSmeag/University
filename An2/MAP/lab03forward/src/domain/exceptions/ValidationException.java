package socialnetwork.domain.exceptions;

import socialnetwork.domain.utils.TextColor;

/**
 * Exception thrown when a validation error occurs
 */
public class ValidationException extends RuntimeException implements TextColor {
    /**
     * Constructor for ValidationException
     * @param problem - the problem that caused the exception, as a string
     */
    public ValidationException(String problem) {
        super(ANSI_RED + String.format("Input is not valid:\n" +
                "\t%s\n" + ANSI_RESET, problem));
    }

    /**
     * Constructor for ValidationException - private to prevent instantiation with no params
     */
    private ValidationException() {
    }

    /**
     * Constructor variant for ValidationException
     * @param message - the message to be displayed
     * @param cause - the cause of the exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor variant for ValidationException
     * @param cause - the cause of the exception
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor variant for ValidationException
     * @param message - the message to be displayed
     * @param cause - the cause of the exception
     * @param enableSuppression - whether to enable suppression
     * @param writableStackTrace - whether to write the stack trace
     */
    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
