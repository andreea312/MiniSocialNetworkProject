package com.example.socialnetworkgradlefx.controller;

import com.example.socialnetworkgradlefx.RunApplication;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Service service;

    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;
    @FXML
    TextField textFieldEmail;
    @FXML
    Label loginLabel;
    @FXML
    Button loginButton;
    @FXML
    Hyperlink signupHyperlink;

    @FXML
    public void loginButtonClicked() throws IOException {
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String email = textFieldEmail.getText();
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldEmail.clear();

        boolean ok = true;
        User found = null;
        try {
            User user = service.findUserByNameEmail(firstName, lastName, email);
            found = user;
        }
        catch(RepoException re){
            System.out.println(re.getMessage());
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText(re.getMessage());
            a.show();
            ok = false;
        }
        if(ok) {
            FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("userView.fxml"));
            Scene scene = new Scene(loader.load(), 563, 400);
            UserController ctrl = loader.getController();
            ctrl.setUser(found);
            ctrl.setService(service);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Hello, " + firstName + " " + lastName + "!");
            stage.show();

            Stage thisStage = (Stage) loginButton.getScene().getWindow();
            thisStage.close();
        }
    }

    @FXML
    public void signupHyperlinkClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("signupView.fxml"));
        Scene scene = new Scene(loader.load(),387,282);
        SignupController ctrl = loader.getController();
        ctrl.setService(service);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();
    }

    public void setService(Service service) {
        this.service = service;
    }
}
