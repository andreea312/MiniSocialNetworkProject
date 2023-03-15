package com.example.socialnetworkgradlefx.domain;

public class FriendshipRequest extends Entity{
    private String status;
    private Friendship friendship;

    /**
     * Friendship request constructor
     * @param id Integer - friendship request id
     * @param friendship Friendship
     * @param status String - waiting, accepted, denied
     */
    public FriendshipRequest(int id, Friendship friendship, String status) {
        super(id);
        this.friendship = friendship;
        this.status = status;
    }

    /**
     * Getter for the status
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for the status
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter for the friendship
     * @return Friendship
     */
    public Friendship getFriendship() {
        return friendship;
    }

    /**
     * Setter for the friendship
     * @param friendship Friendship
     */
    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    /**
     * Getter for the id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Forms a string out of a FriendshipRequest object
     * @return String
     */
    @Override
    public String toString() {
        return '{' + id + " " + friendship.getId() + " " + status + '}';
    }
}
