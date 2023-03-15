package com.example.socialnetworkgradlefx.repo.database;


import com.example.socialnetworkgradlefx.domain.Friendship;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.repo.memory.MemoryRepo;


import java.sql.*;
import java.util.List;

public class FriendshipDatabaseRepo extends MemoryRepo<Friendship> {
    protected String url;
    private String friends;
    private String password;

    /**
     * Constructor for the friendship database repository
     * @param url String
     * @param friends String
     * @param password String
     */
    public FriendshipDatabaseRepo(String url, String friends, String password) {
        this.url = url;
        this.friends = friends;
        this.password = password;
        loadFriendshipsFromDatabase();
    }

    /**
     * Saves friendships from the given list of friendships to the database
     * @param all List
     */
    public void saveFriendshipsToDatabase(List<Friendship> all) {
        String sql1 = "DELETE FROM friendships";
        try(Connection connection = DriverManager.getConnection(url, friends, password);
            PreparedStatement ps = connection.prepareStatement(sql1)){
            ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        for(Friendship f: all) {
            String sql = "INSERT INTO friendships(id, friendOneId, friendTwoId, friendsForm) VALUES(?,?,?,?);";
            try(Connection connection = DriverManager.getConnection(url, friends, password);
                PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,f.getId());
                ps.setInt(2,f.getFriendOneId());
                ps.setInt(3,f.getFriendTwoId());
                ps.setString(4,f.getFriendsForm());
                ps.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads friendships from the database to the memory list of friendships
     */
    public void loadFriendshipsFromDatabase() {
        try(Connection connection = DriverManager.getConnection(url, friends, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM friendships");
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                int id = rs.getInt("id");
                int friendOneId = rs.getInt("friendoneid");
                int friendTwoId = rs.getInt("friendtwoid");
                String friendsForm = rs.getString("friendsform");
                Friendship friendship = new Friendship(id, friendOneId, friendTwoId, friendsForm);
                try {
                    super.add(friendship);
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
     * Checks if a friendship already exists
     * @param friendship Friendship
     * @return boolean = true if it exists, false otherwise
     */
    public boolean alreadyExists(Friendship friendship){
        for (Friendship f : getAll()){
            int f1 = f.getFriendOneId();
            int f2 = f.getFriendTwoId();
            int e1 = friendship.getFriendOneId();
            int e2 = friendship.getFriendTwoId();
            if((f1 == e1 && f2 == e2) || (f1 == e2 && f2 == e1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds user
     * @param entity User
     * @throws RepoException if the friendship we want to add already exists
     */
    @Override
    public void add(Friendship entity) throws RepoException{
        super.add(entity);
        saveFriendshipsToDatabase(super.getAll());
    }

    /**
     * Updates friendship
     * @param entity Friendship
     */
    @Override
    public void update(Friendship entity) throws RepoException{
        super.update(entity);
        saveFriendshipsToDatabase(super.getAll());
    }

    /**
     * Update without verifying if the friendship already exists
     * @param entity Friendship
     */
    @Override
    public void updateWithoutException(Friendship entity) {
        super.updateWithoutException(entity);
        saveFriendshipsToDatabase(super.getAll());
    }

    /**
     * Remove a friendship
     * @param entity Friendship
     */
    @Override
    public void remove(Friendship entity) {
        super.remove(entity);
        saveFriendshipsToDatabase(super.getAll());
    }


    /**
     * Get a friendship by id
     * @param id Int
     * @return Friendship
     */
    @Override
    public Friendship getById(int id) {
        return super.getById(id);
    }

    /**
     * Get all friendships
     * @return List
     */
    @Override
    public List<Friendship> getAll() {
        return super.getAll();
    }

    /**
     * Get size of friendships list
     * @return Integer
     */
    @Override
    public int sizee(){return super.sizee();}
}

