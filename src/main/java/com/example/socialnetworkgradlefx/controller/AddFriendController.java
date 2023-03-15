package com.example.socialnetworkgradlefx.controller;

import com.example.socialnetworkgradlefx.RunApplication;
import com.example.socialnetworkgradlefx.domain.Friendship;
import com.example.socialnetworkgradlefx.domain.FriendshipRequest;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class AddFriendController {
    private User user;
    private Service service;
    ObservableList<User> allUsers = FXCollections.observableArrayList();
    ObservableList<User> friendsOfUser;
    ObservableList<FriendshipRequest> requestsFromUser;

    @FXML
    TableView<User> tableView2;
    @FXML
    Label searchLabel;
    @FXML
    Button sendFriendshipRequestButton;
    @FXML
    Button searchButton;
    @FXML
    TextField textFieldFirstName2;
    @FXML
    TextField textFieldLastName2;
    @FXML
    TableColumn<User, String> firstNameColumn2;
    @FXML
    TableColumn<User, String> lastNameColumn2;
    @FXML
    TableColumn<User, String> emailColumn2;

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriendsOfUser(ObservableList<User> friendsOfUser){
        this.friendsOfUser = friendsOfUser;
    }

    @FXML
    public void initialize() {
        tableView2.getColumns().clear();
        firstNameColumn2.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastNameColumn2.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        emailColumn2.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableView2.setItems(allUsers);
        tableView2.getColumns().addAll(firstNameColumn2, lastNameColumn2, emailColumn2);
    }

    @FXML
    public void searchButtonClicked() {
        String firstName = textFieldFirstName2.getText();
        String lastName = textFieldLastName2.getText();
        textFieldFirstName2.clear();
        textFieldLastName2.clear();

        ObservableList<User> foundUsers = FXCollections.observableArrayList();
        foundUsers.addAll(service.findUsersByName(firstName, lastName));
        foundUsers.remove(user);
        tableView2.setItems(foundUsers);
    }

    @FXML
    public void sendFriendshipRequestButtonClicked() {
        User selectedFriend = (User)tableView2.getSelectionModel().getSelectedItem();
        Friendship f = new Friendship(service.generateIdFriendship(), user.getId(), selectedFriend.getId(), service.generateDateTimeFriendship());
        try {
            service.addFriendshipRequest(f);
            FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("sentRequestsView.fxml"));
            SentRequestsController ctrl = loader.getController();
            requestsFromUser.remove(service.getRequestByIdsUsers(user.getId(), selectedFriend.getId()));
            ctrl.setRequestsFromUser(requestsFromUser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Friend request sent");
            alert.setContentText("SUCCESSFULLY");
            alert.showAndWait();
        }
        catch (RepoException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Friend request not sent");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        tableView2.setItems(this.allUsers);
    }

    public void setService(Service service) {
        this.service = service;
        allUsers.setAll(service.getAll());
        allUsers.remove(user);
    }
}
