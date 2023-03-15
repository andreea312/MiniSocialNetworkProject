package com.example.socialnetworkgradlefx.repo.database;

import com.example.socialnetworkgradlefx.domain.FriendshipRequest;
import com.example.socialnetworkgradlefx.domain.Message;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.repo.memory.MemoryRepo;

import java.sql.*;
import java.util.List;

public class MessageDatabaseRepo extends MemoryRepo<Message> {
    protected String url;
    private String message;
    private String password;

    /**
     * Constructor for the message database repository
     * @param url String
     * @param message String
     * @param password String
     */
    public MessageDatabaseRepo(String url, String message, String password) {
        this.url = url;
        this.message = message;
        this.password = password;
        loadMessagesFromDatabase();
    }

    /**
     * Saves messages from the given list of messages to the database
     * @param all List
     */
    public void saveMessagesToDatabase(List<Message> all) {
        String sql1 = "DELETE FROM messages";
        try(Connection connection = DriverManager.getConnection(url, message, password);
            PreparedStatement ps = connection.prepareStatement(sql1)){
            ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        for(Message m: all) {
            String sql = "INSERT INTO messages(id, sender, receiver, dataSent, messageText) VALUES(?,?,?,?,?);";
            try(Connection connection = DriverManager.getConnection(url, message, password);
                PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,m.getId());
                ps.setInt(2,m.getSender());
                ps.setInt(3,m.getReceiver());
                ps.setString(4,m.getDataSent());
                ps.setString(5,m.getMessageText());
                ps.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads messages from the database to the memory list of messages
     */
    public void loadMessagesFromDatabase() {
        try(Connection connection = DriverManager.getConnection(url, message, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM messages");
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                int id = rs.getInt("id");
                int sender = rs.getInt("sender");
                int receiver = rs.getInt("receiver");
                String dataSent = rs.getString("datasent");
                String messageText = rs.getString("messagetext");
                Message message = new Message(id, sender, receiver, dataSent, messageText);
                try {
                    super.add(message);
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
     * Adds message
     * @param entity Message
     * @throws RepoException if the message we want to add already exists
     */
    @Override
    public void add(Message entity) throws RepoException{
        super.add(entity);
        saveMessagesToDatabase(super.getAll());
    }

    /**
     * Remove a message
     * @param entity Message
     */
    @Override
    public void remove(Message entity) {
        super.remove(entity);
        saveMessagesToDatabase(super.getAll());
    }

    /**
     * Get a message by id
     * @param id Int
     * @return Message
     */
    @Override
    public Message getById(int id) {
        return super.getById(id);
    }

    /**
     * Get all messages
     * @return List
     */
    @Override
    public List<Message> getAll() {
        return super.getAll();
    }

    /**
     * Get size of messages list
     * @return Integer
     */
    @Override
    public int sizee(){return super.sizee();}
}
