package com.example.socialnetworkgradlefx.service;

import com.example.socialnetworkgradlefx.domain.*;
import com.example.socialnetworkgradlefx.domain.validators.ValidationException;
import com.example.socialnetworkgradlefx.domain.validators.Validator;
import com.example.socialnetworkgradlefx.repo.Repository;
import com.example.socialnetworkgradlefx.repo.exceptions.FriendshipNotFoundException;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.repo.exceptions.UserNotFoundException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

public class Service {
    private final Validator<User> userValidator;
    private final Validator<Friendship> friendshipValidator;
    private final Repository<User> usersRepo;
    private final Repository<Friendship> friendshipsRepo;

    private final Repository<FriendshipRequest> friendshipsRequestsRepo;
    private final Repository<Message> messagesRepo;

    /**
     * Service constructor
     *
     * @param userValidator            Validator
     * @param friendshipValidator      Validator
     * @param usersRepo                Repository
     * @param friendshipsRepo          Repository
     * @param friendshipsRequestsRepo  Repository
     * @param messagesRepo             Repository
     */
    public Service(Validator<User> userValidator, Validator<Friendship> friendshipValidator, Repository<User> usersRepo, Repository<Friendship> friendshipsRepo, Repository<FriendshipRequest> friendshipsRequestsRepo, Repository<Message> messagesRepo) {
        this.userValidator = userValidator;
        this.friendshipValidator = friendshipValidator;
        this.usersRepo = usersRepo;
        this.friendshipsRepo = friendshipsRepo;
        this.friendshipsRequestsRepo = friendshipsRequestsRepo;
        this.messagesRepo = messagesRepo;
    }


    /**
     * Generate id for the User
     * @return Integer - the id of the user
     */
    public int generateIdUser() {
        //uuid
        int max = 0;
        for (User entity : usersRepo.getAll()){
            if(entity.getId() > max){
                max = entity.getId();
            }
        }
        return max+1;
    }

    /**
     * Generate id for the Friendship
     * @return Integer - the id of the friendship
     */
    public int generateIdFriendship() {
        //uuid
        int max = 0;
        for (Friendship entity : friendshipsRepo.getAll()){
            if(entity.getId() > max){
                max = entity.getId();
            }
        }
        return max+1;
    }

    /**
     * Generate id for the FriendshipRequest
     * @return Integer - the id of the friendship request
     */
    public int generateIdFriendshipRequest() {
        int max = 0;
        for (FriendshipRequest entity : friendshipsRequestsRepo.getAll()){
            if(entity.getId() > max){
                max = entity.getId();
            }
        }
        return max+1;
    }

    /**
     * Generate id for the Message
     * @return Integer - the id of the message
     */
    public int generateIdMessage() {
        int max = 0;
        for (Message entity : messagesRepo.getAll()){
            if(entity.getId() > max){
                max = entity.getId();
            }
        }
        return max+1;
    }

    /**
     * Generate dateTime for the Friendship
     * @return String
     */
    public String generateDateTimeFriendship() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }

    /**
     * Generate dateTime for the Message
     * @return String
     */
    public String generateDateTimeMessage() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }

    /**
     * Finds user by name and email
     * @param firstName String - the first name of the user we want to find
     * @param lastName String - the last name of the user we want to find
     * @param email String - the email of the user we want to find
     * @return User - the user found
     * @throws RepoException UserNotFoundException if the user with the given email and name does not exist
     */
    public User findUserByNameEmail(String firstName, String lastName, String email) throws RepoException {
        User user = null;
        for (User u : usersRepo.getAll()){
            System.out.println(u.toString());
            if(Objects.equals(u.getEmail(),email) && Objects.equals(u.getFirstName(), firstName) && Objects.equals(u.getLastName(),lastName)){
                user = u;
            }
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    /**
     * Finds users that have the given name
     * @param firstName String
     * @param lastName String
     * @return List of found users
     */
    public List<User> findUsersByName(String firstName, String lastName) {
        List<User> foundUsers = new ArrayList<>();
        for (User u : usersRepo.getAll()){
            if(!firstName.equals("") && !lastName.equals("") && Objects.equals(u.getFirstName(), firstName) && Objects.equals(u.getLastName(),lastName)
            || (firstName.equals("") && !lastName.equals("") && Objects.equals(u.getLastName(),lastName))
            ||(!firstName.equals("") && lastName.equals("") && Objects.equals(u.getFirstName(),firstName))){
                foundUsers.add(u);
            }
        }
        return foundUsers;
    }

    /**
     * Add user
     * @param firstName String - first name of user we want to add
     * @param lastName String - last name of user we want to add
     * @param email String - email of user we want to add
     * @return User
     */
    public User addUser(String firstName, String lastName, String email) throws RepoException, ValidationException{
        for(User u: usersRepo.getAll()){
            System.out.println(u);
        }
        User newUser = null;

        newUser = new User(generateIdUser(), firstName, lastName, email);
        userValidator.validate(newUser);
        usersRepo.add(newUser);

        return newUser;
    }

    /**
     * Removes user with specified id
     * @param userId Integer - the id of the user we wat to remove
     * @throws RepoException UserNotFoundException if the user with the given id does not exist
     */
    public void removeUser(int userId) throws RepoException {//alt+enter
        User user = usersRepo.getById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        for (Friendship friendship : getUserFriendships(userId)) {
            friendshipsRepo.remove(friendship);
        }
        usersRepo.remove(user);
    }

    /**
     * Updates user with specified id
     * @param userId Integer - the id of the user we wat to remove
     * @param newFirstName String - the new first name of the user
     * @param newLastName String - the new last name of the user
     * @param newEmail String - the new email of the user
     * @throws RepoException UserNotFoundException if the user with the given id does not exist
     */
    public void updateUser(int userId, String newFirstName, String newLastName, String newEmail) throws RepoException {
        for(User u: usersRepo.getAll()){
            System.out.println(u);
        }
        User user = usersRepo.getById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String oldFirstName = user.getFirstName();
        String oldLastName = user.getLastName();
        String oldEmail= user.getEmail();

        if(newFirstName.equals("")){
            newFirstName = oldFirstName;
        }
        if(newLastName.equals("")){
            newLastName = oldLastName;
        }
        if(newEmail.equals("")){
            newEmail = oldEmail;

        }

        boolean sameEmailAsTheOldOne = false;
        if(newEmail.equals(oldEmail)) {
            sameEmailAsTheOldOne = true;
        }

        if(sameEmailAsTheOldOne){
            try {
                User newUser = new User(userId, newFirstName, newLastName, newEmail);
                userValidator.validate(newUser);
                usersRepo.updateWithoutException(newUser);
            } catch (ValidationException ex) {
                System.out.println(ex.getMessage());
            }
        }
        else {
            try {
                User newUser = new User(userId, newFirstName, newLastName, newEmail);
                userValidator.validate(newUser);
                usersRepo.update(newUser);
            } catch (ValidationException | RepoException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Add a friendship between two specified users
     * @param friend1Id Integer - the id of a user
     * @param friend2Id Integer - the id of another user
     * @throws RepoException UserNotFoundException if the users with the given ids do not exist
     */
    public void addFriendship(int friend1Id, int friend2Id) throws RepoException {
        User userB = usersRepo.getById(friend1Id);
        User userA = usersRepo.getById(friend2Id);
        if (userA == null || userB == null) {
            throw new UserNotFoundException();
        }
        try{
            Friendship newFriendship = new Friendship(generateIdFriendship(), friend1Id, friend2Id, generateDateTimeFriendship());
            friendshipValidator.validate(newFriendship);
            friendshipsRepo.add(newFriendship);
        }
        catch(ValidationException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Adds message that contains a specified text from sender to receiver
     * @param sender String
     * @param receiver String
     * @param messageText String
     * @return Message - the added message
     * @throws RepoException if the sender and the receiver are not friends
     */
    public Message addMessage(int sender, int receiver, String messageText) throws RepoException {
        int idFriendship = 0;
        for (Friendship f : friendshipsRepo.getAll()) {
            if ((f.getFriendOneId() == sender && f.getFriendTwoId() == receiver) ||
                    (f.getFriendOneId() == receiver && f.getFriendTwoId() == sender))
                idFriendship = f.getId();
        }
        Friendship friendship = friendshipsRepo.getById(idFriendship);
        if (friendship == null) {
            throw new FriendshipNotFoundException();
        }
        Message message = new Message(generateIdMessage(), sender, receiver, generateDateTimeMessage(), messageText);
        messagesRepo.add(message);
        return message;
    }

    /**
     * Adds a friendship request for a wanted-to-be-formed friendship
     * @param friendship Friendship
     * @throws RepoException if the friendship request already exists
     */
    public void addFriendshipRequest(Friendship friendship) throws RepoException {
        try{
            FriendshipRequest newFriendshipRequest = new FriendshipRequest(generateIdFriendshipRequest(), friendship, "waiting");
            friendshipValidator.validate(friendship);
            friendshipsRequestsRepo.add(newFriendshipRequest);
        }
        catch(ValidationException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Removes a friendship between two specified users by the given friendship id
     * @param friendshipId Integer
     * @throws RepoException FriendshipNotFoundException if the friendship with the given id does not exist
     */
    public void removeFriendship(int friendshipId) throws RepoException {
        Friendship friendship = friendshipsRepo.getById(friendshipId);
        if (friendship == null) {
            throw new FriendshipNotFoundException();
        }
        friendshipsRepo.remove(friendship);
    }

    /**
     * Removes friendship between two users by the given users ids
     * @param idUser1 Integer - id of an user
     * @param idUser2 Integer - id of another user
     * @throws RepoException FriendshipNotFoundException if friendship between the two users does not exist
     */
    public void removeFriendshipByIdsUsers(int idUser1, int idUser2) throws RepoException{
        int idFriendship = 0;
        for (Friendship f : friendshipsRepo.getAll()) {
            if ((f.getFriendOneId() == idUser1 && f.getFriendTwoId() == idUser2) ||
                    (f.getFriendOneId() == idUser2 && f.getFriendTwoId() == idUser1))
                idFriendship = f.getId();
        }
        Friendship friendship = friendshipsRepo.getById(idFriendship);
        if (friendship == null) {
            throw new FriendshipNotFoundException();
        }
        removeFriendshipRequestByIdsUsers(idUser1, idUser2);
        removeMessagesByIdsUsers(idUser1, idUser2);
        friendshipsRepo.remove(friendship);
    }

    /**
     * Removes friendship requests between two users by the given users ids
     * @param idUser1 Integer - id of an user
     * @param idUser2 Integer - id of another user
     */
    public void removeFriendshipRequestByIdsUsers(int idUser1, int idUser2) {
        for (FriendshipRequest fr : friendshipsRequestsRepo.getAll()) {
            if ((fr.getFriendship().getFriendOneId() == idUser1 && fr.getFriendship().getFriendTwoId() == idUser2) ||
                    (fr.getFriendship().getFriendOneId() == idUser2 && fr.getFriendship().getFriendTwoId() == idUser1)) {
                friendshipsRequestsRepo.remove(fr);
            }
        }
    }

    /**
     * Removes all messages between two users by the given users ids
     * @param idUser1 Integer - id of an user
     * @param idUser2 Integer - id of another user
     */
    public void removeMessagesByIdsUsers(int idUser1, int idUser2) {
        for (Message msg : messagesRepo.getAll()) {
            if ((msg.getSender() == idUser1 && msg.getReceiver() == idUser2) ||
                    (msg.getSender() == idUser2 && msg.getReceiver() == idUser1)) {
                messagesRepo.remove(msg);
            }
        }
    }

    /**
     * Gets all friendships requests
     * @return List
     */
    public List<FriendshipRequest> getAllRequests(){
        return friendshipsRequestsRepo.getAll();
    }

    /**
     * Gets friendship request by ids of users
     * @param idUser1 int
     * @param idUser2 int
     * @return FriendshipRequest
     */
    public FriendshipRequest getRequestByIdsUsers(int idUser1, int idUser2){
        FriendshipRequest friendshipRequest = null;
        for (FriendshipRequest fr : getAllRequests()) {
            if ((fr.getFriendship().getFriendOneId() == idUser1 && fr.getFriendship().getFriendTwoId() == idUser2) ||
                    (fr.getFriendship().getFriendOneId() == idUser2 && fr.getFriendship().getFriendTwoId() == idUser1)) {
                friendshipRequest = fr;
            }
        }
        return friendshipRequest;
    }


    /**
     * Updates Friendship, modifying its forming time
     * @param idUser1 Integer
     * @param idUser2 Integer
     * @throws RepoException if friendship was not found
     */
    public void updateFriendship(int idUser1, int idUser2) throws RepoException {
        int idFriendship = 0;
        for (Friendship f : friendshipsRepo.getAll()) {
            System.out.println(f);
            if ((f.getFriendOneId() == idUser1 && f.getFriendTwoId() == idUser2) ||
                    (f.getFriendOneId() == idUser2 && f.getFriendTwoId() == idUser1))
                idFriendship = f.getId();
        }
        System.out.println(idFriendship);
        Friendship friendship = friendshipsRepo.getById(idFriendship);
        if (friendship == null) {
            throw new FriendshipNotFoundException();
        }

        Friendship newFriendship = new Friendship(idFriendship, idUser1, idUser2, generateDateTimeFriendship());
        System.out.println(newFriendship);
        friendshipsRepo.updateWithoutException(newFriendship);
    }

    /**
     * Updates the status of a friendship request
     * @param fr FriendshipRequest - the friendship request whose status we want to update
     * @param status String - the new status
     */
    public void updateFriendshipRequest(FriendshipRequest fr, String status) {
        int idFriendshipRequest = fr.getId();
        Friendship newFriendship = new Friendship(fr.getFriendship().getId(), fr.getFriendship().getFriendOneId(), fr.getFriendship().getFriendTwoId(), generateDateTimeFriendship());
        FriendshipRequest newFriendshipRequest = new FriendshipRequest(idFriendshipRequest, newFriendship, status);
        friendshipsRequestsRepo.updateWithoutException(newFriendshipRequest);
    }

    /**
     * Get a list of friendships in which the user with the given id is implied
     * @param userId Integer - the id of the user
     * @return List
     * @throws RepoException UserNotFoundException if the user is not found in the users list
     */
    public List<Friendship> getUserFriendships(int userId) throws RepoException {
        User user = usersRepo.getById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        List<Friendship> friendships = new ArrayList<>();
        for (Friendship friendship : friendshipsRepo.getAll()) {
            if (friendship.getFriendOneId() == userId || friendship.getFriendTwoId() == userId) {
                friendships.add(friendship);
            }
        }
        return friendships;
    }

    public List<User>getAll(){
        return usersRepo.getAll();
    }

    /**
     * Get a list of users the user with the given id is friend with
     * @param userId Integer - the id of the user
     * @return List
     * @throws RepoException UserNotFoundException if the user is not found in the users list
     */
    public List<User> getUserFriends(int userId) throws RepoException {
        User user = usersRepo.getById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        List<User> friends = new ArrayList<>();
        for (Friendship friendship : friendshipsRepo.getAll()) {
            if (friendship.getFriendOneId() == userId){
                friends.add(usersRepo.getById(friendship.getFriendTwoId()));
            }
            else if (friendship.getFriendTwoId() == userId) {
                friends.add(usersRepo.getById(friendship.getFriendOneId()));
            }
        }
        return friends;
    }

    /**
     * Gets friends without raising exception
     * @param userId Integer
     * @return List
     */
    public List<User> getUserFriendsss(int userId)  {
        User user = usersRepo.getById(userId);
        List<User> friends = new ArrayList<>();
        for (Friendship friendship : friendshipsRepo.getAll()) {
            if (friendship.getFriendOneId() == userId){
                friends.add(usersRepo.getById(friendship.getFriendTwoId()));
            }
            else if (friendship.getFriendTwoId() == userId) {
                friends.add(usersRepo.getById(friendship.getFriendOneId()));
            }
        }
        return friends;
    }

    private int communitySize = 0;
    private int[] community;
    private static int N = 100;

    /**
     * DFS Recursive
     * @param start Integer
     * @param visited boolean[]
     */
    private void DFSRec(int start, boolean[] visited) {
        List<User> userList = usersRepo.getAll();
        //System.out.println("Vizitez "+ userList.get(start).getId());
        visited[start] = true;
        community[communitySize]=userList.get(start).getId();
        communitySize++;

        for (int x = 0; x < userList.size(); x++) {
            if (!visited[x]) {
                for (Friendship f : friendshipsRepo.getAll()) {
                    if (f.getFriendOneId()==userList.get(start).getId() && !visited[userList.indexOf(usersRepo.getById(f.getFriendTwoId()))]) {
                        DFSRec(userList.indexOf(usersRepo.getById(f.getFriendTwoId())), visited);
                    }
                    if (f.getFriendTwoId()==userList.get(start).getId() && !visited[userList.indexOf(usersRepo.getById(f.getFriendOneId()))]) {
                        DFSRec(userList.indexOf(usersRepo.getById(f.getFriendOneId())), visited);
                    }
                }
            }
        }
    }

    /**
     * DFS algorithm
     * @param start   int
     * @param visited boolean[]
     */
    private void DFS(int start, boolean[] visited) {
        List<User> userList = usersRepo.getAll();
        community = new int[N];
        community[communitySize] = userList.get(start).getId();
        DFSRec(start, visited);
    }
    /**
     * Get community numbers
     * @return int
     */
    public int getCommunitiesNumber() {
        List<User> userList = usersRepo.getAll();

        int communities = 0;
        boolean[] visited = new boolean[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            visited[i] = false;
        }
        for (int i = 0; i < userList.size(); i++) {
            if (!visited[i]) {
                DFS(i, visited);
                communities++;
            }
        }
        return communities;
    }

    /**
     * Gets the size of the community with the maximum number of users
     * @return Integer
     */
    public int getLargestCommunity() {
        List<User> userList = usersRepo.getAll();
        int maxCommunitySize = 0;
        communitySize = 1;
        boolean[] visited = new boolean[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            visited[i] = false;
        }
        for (int i = 0; i < userList.size(); i++) {
            if (!visited[i]) {
                DFS(i, visited);
                maxCommunitySize = Math.max(maxCommunitySize, communitySize);
                communitySize = 1;
            }
        }
        return maxCommunitySize;
    }

    /**
     * Prints the users of each community
     */
    public void getCommunitiesUsers(){
        List<User> userList = usersRepo.getAll();
        int lant_size = 0;
        boolean[] visited = new boolean[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            visited[i] = false;
        }
        System.out.println("Initialized");

        for (int i = 0; i < userList.size(); i++) {
            if (!visited[i]) {
                System.out.println("\n");
                System.out.println("DFS from user with Id " + userList.get(i).getId());
                communitySize = 0;
                DFS(i, visited);
                int[] comunitate = new int[10000];
                for (int j = 0; j < communitySize; j++) {
                    comunitate[j] = community[j];
                    System.out.print(comunitate[j] + " ");
                    community[j] = 0;
                }
            }
        }
        System.out.println("\n");
    }

    private int length = 0;
    private int maxLength = -1;
    Map<User, Boolean> visit = new HashMap<>();
    List<Integer> mostSociable;

    /**
     * DFS algorithm
     * @param user start of DFS
     */
    private void DFS2(User user) {
        length++;
        visit.put(user, true);
        mostSociable.add(user.getId());
        for (User u: getUserFriendsss(user.getId())) {
            if(!visit.get(u)){
                DFS2(u);
            }
        }
        if(length > maxLength){
            mostSociable = new ArrayList<Integer>();
            maxLength = length;
        }
        length--;
        visit.put(user, false);
    }

    /**
     * Gets most sociable community members
     * @return List
     */
    public List<Integer> getMostSociableCommunity(){
        length = 0;
        maxLength = -1;
        for(User u: usersRepo.getAll()){
            visit.put(u, false);
        }
        for(User u: usersRepo.getAll()){
            if(!visit.get(u)){
                mostSociable = new ArrayList<Integer>();
                DFS2(u);
                System.out.println("Fac DFS din " + u.getId() + "\n");
            }
        }
        for(User u: usersRepo.getAll()){
            visit.put(u, false);
        }
        System.out.println(maxLength);
        return mostSociable;
    }

    /**
     * Gets all friendships requests received by user with the given id
     * @param id Integer - id of the user
     * @return List - list pf friendship requests
     */
    public List<FriendshipRequest> getRequestsForUser(int id) {
        List<FriendshipRequest> requests = new ArrayList<>();
        for(FriendshipRequest fr: friendshipsRequestsRepo.getAll()){
            if(fr.getFriendship().getFriendTwoId() == id){
                requests.add(fr);
            }
        }
        return requests;
    }

    /**
     * Gets all friendships requests sent by user with the given id
     * @param id Integer - id of the user
     * @return List - list pf friendship requests
     */
    public List<FriendshipRequest> getRequestsFromUser(int id) {
        List<FriendshipRequest> requests = new ArrayList<>();
        for(FriendshipRequest fr: friendshipsRequestsRepo.getAll()){
            if(fr.getFriendship().getFriendOneId() == id){
                requests.add(fr);
            }
        }
        return requests;
    }

    /**
     * Gets all the messages between two friends ordered by the date and time of sending
     * @param id1 Integer - id of a user
     * @param id2 Integer - id of the other user
     * @return List - list of messages
     */
    public List<Message> getOrderedMessagesBetweenTwoUsers(int id1, int id2) {
        List<Message> messages = new ArrayList<>();
        for(Message m: messagesRepo.getAll()){
            if((m.getSender() == id1 && m.getReceiver() == id2) ||
                    (m.getSender() == id2 && m.getReceiver() == id1)){
                messages.add(m);
            }
        }
        messages.sort((Message m1, Message m2) -> m1.getDataSent().compareTo(m2.getDataSent()));
        return messages;
    }


    /**
     * Gets the list of messages pairs which will complete the two columns of the chat table in the interface
     * @param id1 Integer - id of a user
     * @param id2 Integer - id of other user
     * @return List - list of messages pairs
     */
    public List<StringPair> messagesInTable(int id1, int id2) {
        List<StringPair> pairs = new ArrayList<>();
        List<Message> messages = getOrderedMessagesBetweenTwoUsers(id1, id2);
        for(Message m: messages){
            if(m.getSender() == id1 && m.getReceiver() == id2){
                pairs.add(new StringPair(m.getMessageText(), ""));
            }
            else if (m.getSender() == id2 && m.getReceiver() == id1) {
                pairs.add(new StringPair("", m.getMessageText()));
            }
        }
        return pairs;
    }
}