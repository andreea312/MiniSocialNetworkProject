package com.example.socialnetworkgradlefx;

import com.example.socialnetworkgradlefx.controller.LoginController;
import com.example.socialnetworkgradlefx.domain.Friendship;
import com.example.socialnetworkgradlefx.domain.FriendshipRequest;
import com.example.socialnetworkgradlefx.domain.Message;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.domain.validators.FriendshipValidator;
import com.example.socialnetworkgradlefx.domain.validators.UserValidator;
import com.example.socialnetworkgradlefx.domain.validators.Validator;
import com.example.socialnetworkgradlefx.repo.Repository;
import com.example.socialnetworkgradlefx.repo.database.FriendshipDatabaseRepo;
import com.example.socialnetworkgradlefx.repo.database.FriendshipRequestDatabaseRepo;
import com.example.socialnetworkgradlefx.repo.database.MessageDatabaseRepo;
import com.example.socialnetworkgradlefx.repo.database.UserDatabaseRepo;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class RunApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("loginView.fxml"));
        Scene scene = new Scene(loader.load(),387,282);
        LoginController ctrl=loader.getController();

        Validator<User> userValidator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();

        String url = "jdbc:postgresql://localhost:5432/socialnetwork";
        Repository<User> usersRepo = new UserDatabaseRepo(url, "postgres", "postgres");
        Repository<Friendship> friendshipsRepo = new FriendshipDatabaseRepo(url, "postgres", "postgres");
        Repository<FriendshipRequest> friendshipsRequestRepo = new FriendshipRequestDatabaseRepo(url, "postgres", "postgres");
        Repository<Message> messsagesRepo = new MessageDatabaseRepo(url, "postgres", "postgres");
        Service service = new Service(userValidator, friendshipValidator, usersRepo, friendshipsRepo, friendshipsRequestRepo, messsagesRepo);

        ctrl.setService(service);
        stage.setScene(scene);
        stage.setTitle("Log in");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}