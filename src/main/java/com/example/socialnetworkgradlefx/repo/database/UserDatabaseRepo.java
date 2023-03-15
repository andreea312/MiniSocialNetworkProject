package com.example.socialnetworkgradlefx.repo.database;


import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.repo.memory.MemoryRepo;

import java.sql.*;
import java.util.List;

public class UserDatabaseRepo extends MemoryRepo<User> {
    protected String url;
    private String userName;
    private String password;

    /**
     * Constructor for the user database repository
     * @param url String
     * @param userName String
     * @param password String
     */
    public UserDatabaseRepo(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        loadUsersFromDatabase();
    }

    /**
     * Saves users from the given list of users to the database
     * @param all List
     */
    public void saveUsersToDatabase(List<User> all) {
        String sql1 = "DELETE FROM users";
        try(Connection connection = DriverManager.getConnection(url, userName, password);
            PreparedStatement ps = connection.prepareStatement(sql1)){
            ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        for(User u: all) {
            String sql = "INSERT INTO users(id, firstName, lastName, email) VALUES(?,?,?,?);";
            try(Connection connection = DriverManager.getConnection(url, userName, password);
                PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,u.getId());
                ps.setString(2,u.getFirstName());
                ps.setString(3,u.getLastName());
                ps.setString(4,u.getEmail());
                ps.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads users from the database to the memory list of users
     */
    public void loadUsersFromDatabase() {
        try(Connection connection = DriverManager.getConnection(url, userName, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                int id = rs.getInt("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String email = rs.getString("email");
                User user = new User(id, firstName, lastName, email);
                try {
                    super.add(user);
                }
                catch (RepoException ex){
                    throw new RuntimeException(ex);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Checks if a user already exists
     * @param user User
     * @return boolean = true if it exists, false otherwise
     */
    public boolean alreadyExists(User user){
        for (User u : getAll()){
            if(u.getEmail().equals(user.getEmail())){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds user
     * @param entity User
     * @throws RepoException if the user we want to add already exists
     */
    @Override
    public void add(User entity) throws RepoException{
        super.add(entity);
        saveUsersToDatabase(super.getAll());
    }

    /**
     * Updates user
     * @param entity User
     */
    @Override
    public void update(User entity) throws RepoException{
        super.update(entity);
        saveUsersToDatabase(super.getAll());
    }

    /**
     * Update without verifying if the user already exists
     * @param entity User
     */
    @Override
    public void updateWithoutException(User entity) {
        super.updateWithoutException(entity);
        saveUsersToDatabase(super.getAll());
    }

    /**
     * Remove a user
     * @param entity User
     */
    @Override
    public void remove(User entity) {
        super.remove(entity);
        saveUsersToDatabase(super.getAll());
    }

    /**
     * Get a user by id
     * @param id Int
     * @return User
     */
    @Override
    public User getById(int id) {
        return super.getById(id);
    }

    /**
     * Get all users
     * @return List
     */
    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    /**
     * Get size of users list
     * @return Integer
     */
    @Override
    public int sizee(){return super.sizee();}

}
