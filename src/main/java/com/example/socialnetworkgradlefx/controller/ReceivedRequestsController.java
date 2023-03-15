package com.example.socialnetworkgradlefx.controller;

import com.example.socialnetworkgradlefx.RunApplication;
import com.example.socialnetworkgradlefx.domain.FriendshipRequest;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class ReceivedRequestsController {
    private User user;
    private Service service;
    ObservableList<FriendshipRequest> requestsForUser = FXCollections.observableArrayList();
    ObservableList<User> friendsOfUser;

    @FXML
    TableView<FriendshipRequest> tableView3;
    @FXML
    TableColumn<FriendshipRequest, String> firstNameColumn3;
    @FXML
    TableColumn<FriendshipRequest, String> lastNameColumn3;
    @FXML
    TableColumn<FriendshipRequest, String> dateColumn;
    @FXML
    TableColumn<FriendshipRequest, String> statusColumn;
    @FXML
    Button acceptRequestButton;
    @FXML
    Button denyRequestButton;

    public void setUser(User user) {
        this.user = user;
    }
    public void setFriendsOfUser(ObservableList<User> friendsOfUser) {
        this.friendsOfUser = friendsOfUser;
    }

    @FXML
    public void initialize() {
        tableView3.getColumns().clear();
        firstNameColumn3.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FriendshipRequest,String>, ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<FriendshipRequest, String> p){
                        FriendshipRequest req = p.getValue();

                        User user = null;
                        int userId = req.getFriendship().getFriendOneId();
                        for(User u: service.getAll()){
                            if(u.getId() == userId) {
                                user = u;
                            }
                        }
                        if(user == null){
                            return new SimpleStringProperty("");
                        }
                        return new SimpleStringProperty(user.getFirstName());
                    }
                }
        );
        lastNameColumn3.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FriendshipRequest,String>, ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<FriendshipRequest, String> p){
                        FriendshipRequest req = p.getValue();

                        User user = null;
                        int userId = req.getFriendship().getFriendOneId();
                        for(User u: service.getAll()){
                            if(u.getId() == userId) {
                                user = u;
                            }
                        }
                        if(user == null){
                            return new SimpleStringProperty("");
                        }
                        return new SimpleStringProperty(user.getLastName());
                    }
                }
        );
        dateColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FriendshipRequest,String>, ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<FriendshipRequest, String> p){
                        FriendshipRequest req = p.getValue();
                        String friendsForm = req.getFriendship().getFriendsForm();
                        return new SimpleStringProperty(friendsForm);
                    }
                }
        );

        statusColumn.setCellValueFactory(new PropertyValueFactory<FriendshipRequest, String>("status"));
        tableView3.setItems(requestsForUser);
        tableView3.getColumns().addAll(firstNameColumn3, lastNameColumn3, dateColumn, statusColumn);
    }

    @FXML
    public void acceptRequestButtonClicked() {
        FriendshipRequest selectedRequest = (FriendshipRequest)tableView3.getSelectionModel().getSelectedItem();
        if(selectedRequest.getStatus().equals("waiting")) {
            try {
                service.updateFriendshipRequest(selectedRequest, "accepted");
                service.addFriendship(selectedRequest.getFriendship().getFriendOneId(), selectedRequest.getFriendship().getFriendTwoId());
                requestsForUser.setAll(service.getRequestsForUser(user.getId()));
                User newFriendOfUser = null;
                for (User u : service.getAll())
                    if (u.getId() == selectedRequest.getFriendship().getFriendOneId())
                        newFriendOfUser = u;
                friendsOfUser.add(newFriendOfUser);

                FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("userView.fxml"));
                UserController ctrl = loader.getController();
                ctrl.setFriendsOfUser(friendsOfUser);
            } catch (RepoException re) {
                System.out.println(re.getMessage());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Request already addressed");
            alert.show();
        }
    }

    @FXML
    public void denyRequestButtonClicked() {
        FriendshipRequest selectedRequest = (FriendshipRequest)tableView3.getSelectionModel().getSelectedItem();
        if(selectedRequest.getStatus().equals("waiting")) {
            service.updateFriendshipRequest(selectedRequest, "denied");
            service.removeFriendshipRequestByIdsUsers(selectedRequest.getFriendship().getFriendOneId(), selectedRequest.getFriendship().getFriendTwoId());
            requestsForUser.setAll(service.getRequestsForUser(user.getId()));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Request already addressed");
            alert.show();
        }
    }

    public void setService(Service service) {
        this.service = service;
        requestsForUser.setAll(service.getRequestsForUser(user.getId()));
    }
}
