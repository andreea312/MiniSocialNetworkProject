package com.example.socialnetworkgradlefx.repo.exceptions;

/**
 * Class UserNotFoundException
 */
public class UserNotFoundException extends RepoException{

    /**
     * Returns message "User not found!"
     */
    public UserNotFoundException() {
        super("User not found!");
    }
}
