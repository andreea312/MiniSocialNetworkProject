package com.example.socialnetworkgradlefx.controller;

import com.example.socialnetworkgradlefx.RunApplication;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;


public class UserController {
    private User user;
    private Service service;
    ObservableList<User> friendsOfUser = FXCollections.observableArrayList();

    @FXML
    TableView<User> tableView;
    @FXML
    Button logoutButton;
    @FXML
    Button chatButton;
    @FXML
    Button deleteFriendButton;
    @FXML
    Button addFriendButton;
    @FXML
    Button receivedRequestsButton;
    @FXML
    Button sentRequestsButton;
    @FXML
    TableColumn<User, String> firstNameColumn;
    @FXML
    TableColumn<User, String> lastNameColumn;
    @FXML
    TableColumn<User, String> emailColumn;

    public void setUser(User user) {
        this.user = user;
    }
    public void setFriendsOfUser(ObservableList<User> friendsOfUser) {
        this.friendsOfUser = friendsOfUser;
    }

    @FXML
    public void initialize() {
        tableView.getColumns().clear();
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableView.setItems(friendsOfUser);
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn);
    }

    @FXML
    public void logoutButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("loginView.fxml"));
        Scene scene = new Scene(loader.load(),387,282);
        LoginController ctrl=loader.getController();
        ctrl.setService(service);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Log in");
        stage.show();

        Stage userStage = (Stage) logoutButton.getScene().getWindow();
        userStage.close();
    }

    @FXML
    public void chatButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("chatView.fxml"));
        Scene scene = new Scene(loader.load(),700,400);
        ChatController ctrl=loader.getController();
        ctrl.setUser(user);
        ctrl.setService(service);
        ctrl.setFriendsOfUser(friendsOfUser);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Chat");
        stage.show();
    }

    @FXML
    public void deleteFriendButtonClicked() {
        User selectedFriend = (User)tableView.getSelectionModel().getSelectedItem();
        friendsOfUser.remove(selectedFriend);
        try {
            service.removeFriendshipByIdsUsers(user.getId(), selectedFriend.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Friend deleted");
            alert.setContentText("SUCCESSFULLY");
            alert.showAndWait();
        }
        catch (RepoException re) {
            System.out.println(re.getMessage());
        }
    }

    @FXML
    public void addFriendButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("addFriendView.fxml"));
        Scene scene = new Scene(loader.load(), 563, 400);
        AddFriendController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.setService(service);
        ctrl.setFriendsOfUser(friendsOfUser);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void receivedRequestsButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("requestsView.fxml"));
        Scene scene = new Scene(loader.load(), 563, 400);
        ReceivedRequestsController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.setService(service);
        ctrl.setFriendsOfUser(friendsOfUser);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sentRequestsButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("sentRequestsView.fxml"));
        Scene scene = new Scene(loader.load(), 563, 400);
        SentRequestsController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.setService(service);
        ctrl.setFriendsOfUser(friendsOfUser);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void setService(Service service) {
        this.service = service;
        friendsOfUser.setAll(service.getUserFriendsss(user.getId()));
    }
}