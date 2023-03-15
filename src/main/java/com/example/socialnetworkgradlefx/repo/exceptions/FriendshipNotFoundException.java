package com.example.socialnetworkgradlefx.repo.exceptions;

/**
 * Class for FriendshipNotFoundException
 */
public class FriendshipNotFoundException extends RepoException{

    /**
     * Returns message "Friendship not found!"
     */
    public FriendshipNotFoundException() {
        super("Friendship not found!");
    }
}