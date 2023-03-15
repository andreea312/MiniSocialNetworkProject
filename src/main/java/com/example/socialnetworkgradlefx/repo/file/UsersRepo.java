package com.example.socialnetworkgradlefx.repo.file;
import com.example.socialnetworkgradlefx.domain.User;


/**
 * Class repo for users
 */
public class UsersRepo extends FileRepo<User> {

    /**
     * Constructor for User repository
     * @param fileName String - name of text file
     */
    public UsersRepo(String fileName) {
        super(fileName);
    }

    /**
     * Converts text file line to User object
     * @param line String - line from the text file
     * @return User
     */
    @Override
    protected User lineToEntity(String line) {
        String []attributes = line.split(",");
        int id = Integer.parseInt(attributes[0]);
        String firstName = attributes[1];
        String lastName = attributes[2];
        String email = attributes[3];

        return new User(id, firstName, lastName, email);
    }

    /**
     * Converts User object to String so that it can be inserted into the text file
     * @param user User
     * @return String
     */
    @Override
    protected String entityToLine(User user) {
        return user.getId() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.getEmail();
    }

    /**
     * Verifies if a User object already exists in the list of users
     * @param entity User
     * @return boolean - true if it already exists, false otherwise
     */
    @Override
    public boolean alreadyExists(User entity){
        for (User u : getAll()){
            if(u.getEmail().equals(entity.getEmail())){
                return true;
            }
        }
        return false;
    }
}

