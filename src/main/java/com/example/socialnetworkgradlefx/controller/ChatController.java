package com.example.socialnetworkgradlefx.controller;

import com.example.socialnetworkgradlefx.domain.Message;
import com.example.socialnetworkgradlefx.domain.StringPair;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatController {
    private User user;
    private User other;
    private Service service;
    ObservableList<User> friendsOfUser;
    ObservableList<StringPair> messagesBetweenTwoUsers =  FXCollections.observableArrayList();

    @FXML
    Button sendMessageButton;
    @FXML
    ComboBox<String> selectChatFriendComboBox = new ComboBox<String>();
    @FXML
    Label chatFriendNameLabel;
    @FXML
    TableView<StringPair> chatTable;
    @FXML
    TableColumn<StringPair, String> friendColumn;
    @FXML
    TableColumn<StringPair, String> meColumn;
    @FXML
    TextField writeMessageTextField;


    public void setUser(User user) {
        this.user = user;
    }
    public void setOther(User other) {
        this.other = other;
    }
    public void setFriendsOfUser(ObservableList<User> friendsOfUser) {
        this.friendsOfUser = friendsOfUser;
        setFriendsOfUserComboBox();
    }

    @FXML
    public void setFriendsOfUserComboBox(){
        selectChatFriendComboBox.getItems().clear();
        List<String> friendsName = new ArrayList<>();
        for(User u: friendsOfUser){
            friendsName.add(u.getFirstName() + "  " + u.getLastName());
        }
        selectChatFriendComboBox.getItems().addAll(friendsName);
    }

    @FXML
    public void initialize() {
        chatTable.getColumns().clear();
        friendColumn.setCellValueFactory(new PropertyValueFactory<StringPair, String>("primit"));
        meColumn.setCellValueFactory(new PropertyValueFactory<StringPair, String>("trimis"));
        chatTable.setItems(messagesBetweenTwoUsers);
        chatTable.getColumns().addAll(friendColumn, meColumn);
    }

    @FXML
    public void selectChatFriendComboBoxClicked() {
        String chatFriendName = selectChatFriendComboBox.getValue().toString();
        String[] firstLatName = chatFriendName.split("  ",2);
        User o = service.findUsersByName(firstLatName[0], firstLatName[1]).get(0);
        setOther(o);
        messagesBetweenTwoUsers.setAll(service.messagesInTable(user.getId(), other.getId()));
        chatFriendNameLabel.setText(chatFriendName);
    }

    @FXML
    public void sendMessageButtonClicked() {
        String messageText = writeMessageTextField.getText();
        writeMessageTextField.clear();
        try {
            Message m = service.addMessage(user.getId(), other.getId(), messageText);
            messagesBetweenTwoUsers.add(new StringPair(m.getMessageText(), ""));
        }
        catch(RepoException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setService(Service service) {
        this.service = service;
    }
}
