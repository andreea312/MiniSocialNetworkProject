package com.example.socialnetworkgradlefx.ui;

import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.domain.validators.ValidationException;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * UI class
 */
public class UI {

    private final Scanner scanner;
    private final Service service;

    public UI(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            functioning();
        }
    }

    private void functioning() {
        printMenu();
        String string = readLine(">>> ");
        try {
            switch (string) {
                case "1" -> addUser();
                case "2" -> removeUser();
                case "3" -> updateUser();
                case "4" -> addFriendship();
                case "5" -> removeFriendshipByIdsUsers();
                case "6" -> updateFriendship();
                case "7" -> userFriendsList();
                case "8" -> communitiesNumber();
                case "9" -> largestCommunity();
                case "10" -> communitiesUsers();
                case "11" -> mostSociableCommunity();
                default -> {}
            }
        }
        catch (Error e) { System.out.println(e.getMessage());}
    }

    private void printMenu() {
        System.out.println("USERS options:");
        System.out.println("1. Add user");
        System.out.println("2. Remove user");
        System.out.println("3. Update user");
        System.out.println("FRIENDSHIPS options:");
        System.out.println("4. Add friendship");
        System.out.println("5. Remove friendship");
        System.out.println("6. Update friendship");
        System.out.println("7. User's friends list");
        System.out.println("COMMUNITIES options:");
        System.out.println("8. Communities number");
        System.out.println("9. Largest community");
        System.out.println("10. Communities users");
        System.out.println("11. Most sociable community");
    }

    private String readLine(String helper) {
        System.out.print(helper);
        return scanner.nextLine();
    }

    private void addUser() {
        String firstName = readLine("UserFirstName: ");
        String lastName = readLine("UserLastName: ");
        String email = readLine("UserEmail: ");
        try{
            service.addUser(firstName, lastName, email);
        }
        catch(ValidationException | RepoException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void removeUser() {
        String userIdString = readLine("UserId: ");
        boolean ok = true;
        try{
            int userId = Integer.parseInt(userIdString);
        }
        catch(Exception ex){
            System.out.println("Id should be of type Integer!");
            ok = false;
        }
        if(ok) {
            try{
                service.removeUser(Integer.parseInt(userIdString));
            }
            catch(RepoException re){
                System.out.println(re.getMessage());
            }
        }
    }

    private void updateUser() {
        String userIdString = readLine("UserId: ");
        boolean ok = true;
        try{
            int userId = Integer.parseInt(userIdString);
        }
        catch(Exception ex){
            System.out.println("Id should be of type Integer!");
            ok = false;
        }
        if(ok) {
            try{
                String firstName = readLine("New first name: ");
                String lastName = readLine("New last name: ");
                String email = readLine("New email: ");
                service.updateUser(Integer.parseInt(userIdString), firstName, lastName, email);
            }
            catch(RepoException re){
                System.out.println(re.getMessage());
            }
        }
    }

    private void addFriendship() {
        String friend1IdString = readLine("Friend1Id: ");
        String friend2IdString = readLine("Friend2Id: ");
        boolean ok = true;
        try{
            int friend1Id = Integer.parseInt(friend1IdString);
            int friend2Id = Integer.parseInt(friend2IdString);
        }
        catch(Exception ex){
            System.out.println("Ids should be both of type Integer!");
            ok = false;
        }
        if(ok) {
            try{
                service.addFriendship(Integer.parseInt(friend1IdString), Integer.parseInt(friend2IdString));
            }
            catch(RepoException re){
                System.out.println(re.getMessage());
            }
        }
    }

    private void removeFriendship() {
        String friendshipIdString = readLine("FriendshipId: ");
        boolean ok = true;
        try{
            int friendshipId = Integer.parseInt(friendshipIdString);
        }
        catch(Exception ex){
            System.out.println("Id should be of type Integer!");
            ok = false;
        }
        if(ok) {
            try {
                service.removeFriendship(Integer.parseInt(friendshipIdString));
            }
            catch(RepoException re){
                System.out.println(re.getMessage());
            }
        }
    }

    private void removeFriendshipByIdsUsers() {
        String friend1IdString = readLine("Friend1Id: ");
        String friend2IdString = readLine("Friend2Id: ");
        boolean ok = true;
        try {
            int friend1Id = Integer.parseInt(friend1IdString);
            int friend2Id = Integer.parseInt(friend2IdString);
        } catch (Exception ex) {
            System.out.println("Ids should be both of type Integer!");
            ok = false;
        }
        if (ok) {
            try {
                service.removeFriendshipByIdsUsers(Integer.parseInt(friend1IdString), Integer.parseInt(friend2IdString));
            } catch (RepoException re) {
                System.out.println(re.getMessage());
            }
        }
    }

    private void updateFriendship() {
        String friend1IdString = readLine("Friend1Id: ");
        String friend2IdString = readLine("Friend2Id: ");
        boolean ok = true;
        try{
            int friend1Id = Integer.parseInt(friend1IdString);
            int friend2Id = Integer.parseInt(friend2IdString);
        }
        catch(Exception ex){
            System.out.println("Ids should be of type Integer!");
            ok = false;
        }
        if(ok) {
            try{
                service.updateFriendship(Integer.parseInt(friend1IdString), Integer.parseInt(friend2IdString));
            }
            catch(RepoException re){
                System.out.println(re.getMessage());
            }
        }
    }

    private void userFriendsList() {
        String userIdString = readLine("userId: ");
        boolean ok = true;
        try{
            int friendshipId = Integer.parseInt(userIdString);
        }
        catch(Exception ex){
            System.out.println("Id should be of type Integer!");
            ok = false;
        }
        if(ok) {
            try{
                System.out.println("Friends of user " + Integer.parseInt(userIdString));
                for (User friend : service.getUserFriends(Integer.parseInt(userIdString))) {
                    System.out.println(friend);
                }
                System.out.println("--------------");
            }
            catch(RepoException re){
                System.out.println(re.getMessage());
            }
        }
    }

    public void communitiesNumber() {
        System.out.println("Number of communities: " + service.getCommunitiesNumber());
    }

    private void largestCommunity(){
        System.out.println("Largest community: " + (service.getLargestCommunity()-1));
    }

    private void communitiesUsers(){
        service.getCommunitiesUsers();
    }

    private void mostSociableCommunity(){
        List<Integer> mostSociable = new ArrayList<Integer>(service.getMostSociableCommunity());
        System.out.println("Most sociable community is formed by the users with the following IDs:");
        for(Integer userId: mostSociable){
            System.out.print(userId + "  ");
        }
        System.out.println("\n");
    }
}


