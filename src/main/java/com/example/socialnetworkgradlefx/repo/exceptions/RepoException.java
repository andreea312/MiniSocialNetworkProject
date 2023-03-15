package com.example.socialnetworkgradlefx.repo.exceptions;

/**
 * Repository exception class
 */
public class RepoException extends Exception{
    /**
     * RepoException constructor with no argument
     */
    public RepoException() {
    }

    /**
     * RepoException constructor with message argument
     * @param message String - the message that should be displayed when exception is thrown
     */
    public RepoException(String message) {
        super(message);
    }

    /**
     * RepoException constructor with message and cause arguments
     * @param message String - the message that should be displayed when exception is thrown
     * @param cause Throwable
     */
    public RepoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * RepoException constructor with cause argument
     * @param cause Throwable
     */
    public RepoException(Throwable cause) {
        super(cause);
    }

    /**
     * ValidationException constructor with more arguments
     * @param message String - the message that should be displayed when exception is thrown
     * @param cause Throwable
     * @param enableSuppression boolean
     * @param writableStackTrace writableStackTrace
     */
    public RepoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}