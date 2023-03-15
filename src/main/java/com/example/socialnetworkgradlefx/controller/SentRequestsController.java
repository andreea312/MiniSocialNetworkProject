package com.example.socialnetworkgradlefx.controller;

import com.example.socialnetworkgradlefx.domain.FriendshipRequest;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SentRequestsController {
    private User user;
    private Service service;
    ObservableList<FriendshipRequest> requestsFromUser = FXCollections.observableArrayList();
    ObservableList<User> friendsOfUser;

    @FXML
    TableView<FriendshipRequest> tableView4;
    @FXML
    TableColumn<FriendshipRequest, String> firstNameColumn4;
    @FXML
    TableColumn<FriendshipRequest, String> lastNameColumn4;
    @FXML
    TableColumn<FriendshipRequest, String> dateColumn4;
    @FXML
    TableColumn<FriendshipRequest, String> statusColumn4;
    @FXML
    Button unsendRequestButton;

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriendsOfUser(ObservableList<User> friendsOfUser) {
        this.friendsOfUser = friendsOfUser;
    }
    public void setRequestsFromUser(ObservableList<FriendshipRequest> requestsFromUser) {
        this.requestsFromUser = requestsFromUser;
    }

    @FXML
    public void initialize() {
        tableView4.getColumns().clear();
        firstNameColumn4.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FriendshipRequest,String>, ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<FriendshipRequest, String> p){
                        FriendshipRequest req = p.getValue();

                        User user = null;
                        int userId = req.getFriendship().getFriendTwoId();
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
        lastNameColumn4.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FriendshipRequest,String>, ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<FriendshipRequest, String> p){
                        FriendshipRequest req = p.getValue();

                        User user = null;
                        int userId = req.getFriendship().getFriendTwoId();
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
        dateColumn4.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FriendshipRequest,String>, ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<FriendshipRequest, String> p){
                        FriendshipRequest req = p.getValue();
                        String friendsForm = req.getFriendship().getFriendsForm();
                        return new SimpleStringProperty(friendsForm);
                    }
                }
        );

        statusColumn4.setCellValueFactory(new PropertyValueFactory<FriendshipRequest, String>("status"));
        tableView4.setItems(requestsFromUser);
        tableView4.getColumns().addAll(firstNameColumn4, lastNameColumn4, dateColumn4, statusColumn4);
    }

    @FXML
    public void unsendRequestButtonClicked() {
        FriendshipRequest selectedRequest = (FriendshipRequest)tableView4.getSelectionModel().getSelectedItem();
        if(selectedRequest.getStatus().equals("waiting")) {
            service.updateFriendshipRequest(selectedRequest, "unsent");
            service.removeFriendshipRequestByIdsUsers(selectedRequest.getFriendship().getFriendOneId(), selectedRequest.getFriendship().getFriendTwoId());
            requestsFromUser.setAll(service.getRequestsFromUser(user.getId()));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Request already accepted");
            alert.show();
        }
    }

    public void setService(Service service) {
        this.service = service;
        requestsFromUser.setAll(service.getRequestsFromUser(user.getId()));
    }
}
