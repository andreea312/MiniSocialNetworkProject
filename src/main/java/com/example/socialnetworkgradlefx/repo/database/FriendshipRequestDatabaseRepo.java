package com.example.socialnetworkgradlefx.repo.database;

import com.example.socialnetworkgradlefx.domain.Friendship;
import com.example.socialnetworkgradlefx.domain.FriendshipRequest;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.repo.memory.MemoryRepo;

import java.sql.*;
import java.util.List;

public class FriendshipRequestDatabaseRepo extends MemoryRepo<FriendshipRequest> {
    protected String url;
    private String friends;
    private String password;

    /**
     * Constructor for the friendship requests database repository
     * @param url String
     * @param friends String
     * @param password String
     */
    public FriendshipRequestDatabaseRepo(String url, String friends, String password) {
        this.url = url;
        this.friends = friends;
        this.password = password;
        loadFriendshipsRequestsFromDatabase();
    }

    /**
     * Saves friendships requests from the given list of friendships requests to the database
     * @param all List
     */
    public void saveFriendshipsRequestsToDatabase(List<FriendshipRequest> all) {
        String sql1 = "DELETE FROM friendshipsrequests";
        try(Connection connection = DriverManager.getConnection(url, friends, password);
            PreparedStatement ps = connection.prepareStatement(sql1)){
            ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        for(FriendshipRequest f: all) {
            String sql = "INSERT INTO friendshipsrequests(id, idf, friendoneid, friendtwoid, friendsform, status) VALUES(?,?,?,?,?,?);";
            try(Connection connection = DriverManager.getConnection(url, friends, password);
                PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,f.getId());
                ps.setInt(2,f.getFriendship().getId());
                ps.setInt(3,f.getFriendship().getFriendOneId());
                ps.setInt(4,f.getFriendship().getFriendTwoId());
                ps.setString(5,f.getFriendship().getFriendsForm());
                ps.setString(6,f.getStatus());
                ps.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads friendships requests from the database to the memory list of friendships requests
     */
    public void loadFriendshipsRequestsFromDatabase() {
        try(Connection connection = DriverManager.getConnection(url, friends, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM friendshipsrequests");
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                int id = rs.getInt("id");
                int idf = rs.getInt("idf");
                int friendOneId = rs.getInt("friendoneid");
                int friendTwoId = rs.getInt("friendtwoid");
                String friendsForm = rs.getString("friendsform");
                String status = rs.getString("status");
                Friendship friendship = new Friendship(idf, friendOneId, friendTwoId, friendsForm);
                FriendshipRequest request = new FriendshipRequest(id, friendship, status);
                try {
                    super.add(request);
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
     * Checks if a friendship request already exists
     * @param request FriendshipRequest
     * @return boolean = true if it exists, false otherwise
     */
    public boolean alreadyExists(FriendshipRequest request){
        for (FriendshipRequest f : getAll()){
            int f1 = f.getFriendship().getFriendOneId();
            int f2 = f.getFriendship().getFriendTwoId();
            int r1 = request.getFriendship().getFriendOneId();
            int r2 = request.getFriendship().getFriendTwoId();
            String s1 = f.getStatus();
            String s2 = request.getStatus();
            if((f1 == r1 && f2 == r2) || (f1 == r2 && f2 == r1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds friendship request
     * @param entity FriendshipRequest
     * @throws RepoException if the user we want to add already exists
     */
    @Override
    public void add(FriendshipRequest entity) throws RepoException{
        super.add(entity);
        saveFriendshipsRequestsToDatabase(super.getAll());
    }

    /**
     * Updates friendship request
     * @param entity FriendshipRequest
     */
    @Override
    public void update(FriendshipRequest entity) throws RepoException{
        super.update(entity);
        saveFriendshipsRequestsToDatabase(super.getAll());
    }

    /**
     * Update without verifying if the friendship request already exists
     * @param entity FriendshipRequest
     */
    @Override
    public void updateWithoutException(FriendshipRequest entity) {
        super.updateWithoutException(entity);
        saveFriendshipsRequestsToDatabase(super.getAll());
    }

    /**
     * Remove a friendship request
     * @param entity FriendshipRequest
     */
    @Override
    public void remove(FriendshipRequest entity) {
        super.remove(entity);
        saveFriendshipsRequestsToDatabase(super.getAll());
    }


    /**
     * Get a friendship request by id
     * @param id Int
     * @return FriendshipRequest
     */
    @Override
    public FriendshipRequest getById(int id) {
        return super.getById(id);
    }

    /**
     * Get all friendships requests
     * @return List
     */
    @Override
    public List<FriendshipRequest> getAll() {
        return super.getAll();
    }

    /**
     * Get size of friendships requests list
     * @return Integer
     */
    @Override
    public int sizee(){return super.sizee();}
}

