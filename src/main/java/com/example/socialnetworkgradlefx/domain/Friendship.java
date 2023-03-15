package com.example.socialnetworkgradlefx.domain;

/**
 * Friendship class
 */
public class Friendship extends Entity{
    private final int friendOneId;
    private final int friendTwoId;

    private final String friendsForm;

    /**
     * Friendship constructor
     * @param id Integer - friendship id
     * @param friendOne - id of a friend
     * @param friendTwo - id of another friend
     * @param friendsForm - date and time when friendship between two users forms
     */
    public Friendship(int id, int friendOne, int friendTwo, String friendsForm) {
        super(id);
        this.friendOneId = friendOne;
        this.friendTwoId = friendTwo;
        this.friendsForm = friendsForm;
    }

    /**
     * Getter for first friend id
     * @return String
     */
    public int getFriendOneId() {
        return friendOneId;
    }

    /**
     * Getter for second friend id
     * @return String
     */
    public int getFriendTwoId() {
        return friendTwoId;
    }

    /**
     * Getter for the date and time when the friendship between two users was created
     * @return String
     */
    public String getFriendsForm() {
        return friendsForm;
    }

    /**
     * Forms a string out of a Friendship object
     * @return String
     */
    @Override
    public String toString() {
        return "Friendship{" + "id='" + id + '\'' + ", friendOneId='" + friendOneId + '\'' +
                ", friendTwoId='" + friendTwoId + '\'' + ", time='" + friendsForm + '}';
    }
}
