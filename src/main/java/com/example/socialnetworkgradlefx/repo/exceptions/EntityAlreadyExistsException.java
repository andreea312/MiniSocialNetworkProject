package com.example.socialnetworkgradlefx.repo.exceptions;

/**
 * Class that represents a specific RepoException
 */
public class EntityAlreadyExistsException extends RepoException{

    /**
     * Returns an "Entity already exists" message
     */
    public EntityAlreadyExistsException() {
        super("Entity already exists!");
    }
}