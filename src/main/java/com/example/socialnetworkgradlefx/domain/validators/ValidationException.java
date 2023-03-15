package com.example.socialnetworkgradlefx.domain.validators;

/**
 * Class ValidationException
 */
public class ValidationException extends Exception {

    /**
     * ValidationException constructor with no argument
     */
    public ValidationException() {
    }

    /**
     * ValidationException constructor with message argument
     * @param message String - the message that should be displayed when exception is thrown
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * ValidationException constructor with message and cause arguments
     * @param message String - the message that should be displayed when exception is thrown
     * @param cause Throwable
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ValidationException constructor with cause argument
     * @param cause Throwable
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * ValidationException constructor with more arguments
     * @param message String - the message that should be displayed when exception is thrown
     * @param cause Throwable
     * @param enableSuppression boolean
     * @param writableStackTrace writableStackTrace
     */
    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}