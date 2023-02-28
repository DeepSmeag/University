package com.example.examen.domain.exceptions;

import com.example.examen.domain.utils.TextColor;

/**
 * Exception thrown when a service error occurs
 */
public class ServiceException extends RuntimeException implements TextColor {
    /**
     * Constructor for ServiceException
     * @param problem - the problem that caused the exception, as a string
     */
    public ServiceException(String problem) {
        super(ANSI_RED + problem + ANSI_RESET);
    }

    /**
     * Constructor for ServiceException - private to prevent instantiation with no params
     */
    private ServiceException() {
    }

    /**
     * Constructor variant for ServiceException
     * @param message - the message to be displayed
     * @param cause - the cause of the exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor variant for ServiceException
     * @param cause - the cause of the exception
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor variant for ServiceException
     * @param message - the message to be displayed
     * @param cause - the cause of the exception
     * @param enableSuppression - whether to enable suppression
     * @param writableStackTrace - whether to write the stack trace
     */
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
