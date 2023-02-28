package socialnetwork.domain.exceptions;

import socialnetwork.domain.utils.TextColor;

/**
 * Exception thrown when a repository error occurs
 */
public class RepoException extends RuntimeException implements TextColor {
    /**
     * Constructor for RepoException
     * @param problem - the problem that caused the exception, as a string
     */
    public RepoException(String problem) {
        super(ANSI_RED + problem + ANSI_RESET);
    }
    /**
     * Constructor for RepoException - private to prevent instantiation with no params
     */
    private RepoException() {
    }
    /**
     * Constructor variant for RepoException
     * @param message - the message to be displayed
     * @param cause - the cause of the exception
     */
    public RepoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor variant for RepoException
     * @param cause - the cause of the exception
     */
    public RepoException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor variant for RepoException
     * @param message - the message to be displayed
     * @param cause - the cause of the exception
     * @param enableSuppression - whether to enable supression
     * @param writableStackTrace - whether to write the stack trace
     */
    public RepoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
