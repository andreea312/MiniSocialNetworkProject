package com.example.socialnetworkgradlefx.domain.validators;

import com.example.socialnetworkgradlefx.domain.Friendship;


/**
 * Validator for Friendship
 */
public class FriendshipValidator implements Validator<Friendship> {
    /**
     * Validates the given Friendship object
     * @param entity Friendship - the friendship that has to be validated
     * @throws ValidationException exception is thrown if the friendship
     * is established between two users with the same id
     */

    @Override
    public void validate(Friendship entity) throws ValidationException {
        int friend1Id = entity.getFriendOneId();
        int friend2Id = entity.getFriendTwoId();
        if(friend1Id == friend2Id){
            throw new ValidationException("Friendship should be established between two different users!");
        }
    }
}
