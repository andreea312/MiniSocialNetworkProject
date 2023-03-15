package com.example.socialnetworkgradlefx.repo.file;
import com.example.socialnetworkgradlefx.domain.Friendship;

/**
 * Class repository for friendships
 */
public class FriendshipsRepo extends FileRepo<Friendship> {
    /**
     * FriendshipRepo constructor
     * @param fileName String - name of text file
     */
    public FriendshipsRepo(String fileName) {
        super(fileName);
    }

    /**
     * Converts text file line to Friendship object
     * @param line String - line from the text file
     * @return Friendship
     */
    @Override
    protected Friendship lineToEntity(String line) {
        String []attributes = line.split(",");
        int id = Integer.parseInt(attributes[0]);
        int friendOneId = Integer.parseInt(attributes[1]);
        int friendTwoId = Integer.parseInt(attributes[2]);
        String friendsForm = attributes[3];
        return new Friendship(id, friendOneId, friendTwoId, friendsForm);
    }

    /**
     * Converts Friendship object to String so that it can be inserted into the text file
     * @param friendship Friendship
     * @return String
     */
    @Override
    protected String entityToLine(Friendship friendship) {
        return friendship.getId() + "," + friendship.getFriendOneId() + "," + friendship.getFriendTwoId() + "," + friendship.getFriendsForm();
    }

    /**
     * Verifies if a Friendship object already exists in the list of friendships
     * @param entity Friendship
     * @return boolean - true if it already exists, false otherwise
     */
    @Override
    public boolean alreadyExists(Friendship entity){
        for (Friendship f : getAll()){
            int f1 = f.getFriendOneId();
            int f2 = f.getFriendTwoId();
            int e1 = entity.getFriendOneId();
            int e2 = entity.getFriendTwoId();
            if((f1 == e1 && f2 == e2) || (f1 == e2 && f2 == e1)) {
                return true;
            }
        }
        return false;
    }
}
