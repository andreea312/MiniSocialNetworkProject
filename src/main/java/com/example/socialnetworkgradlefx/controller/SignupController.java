package com.example.socialnetworkgradlefx.controller;

import com.example.socialnetworkgradlefx.RunApplication;
import com.example.socialnetworkgradlefx.domain.User;
import com.example.socialnetworkgradlefx.domain.validators.ValidationException;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    private Service service;

    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;
    @FXML
    TextField textFieldEmail;
    @FXML
    Button signupButton;
    @FXML
    Hyperlink loginHyperlink;
    @FXML
    Label signupLabel;

    @FXML
    public void signupButtonClicked() throws IOException {
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String email = textFieldEmail.getText();
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldEmail.clear();

        try {
            User user = service.addUser(firstName, lastName, email);
            FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("userView.fxml"));
            Scene scene = new Scene(loader.load(), 563, 400);
            UserController ctrl = loader.getController();
            ctrl.setUser(user);
            ctrl.setService(service);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Hello, " + firstName + " " + lastName + "!");
            stage.show();

            Stage thisStage = (Stage) signupButton.getScene().getWindow();
            thisStage.close();
        }
        catch(ValidationException | RepoException ex){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText(ex.getMessage());
            a.show();
        }
    }

    @FXML
    public void loginHyperlinkClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(RunApplication.class.getResource("loginView.fxml"));
        Scene scene = new Scene(loader.load(),387,282);
        LoginController ctrl = loader.getController();
        ctrl.setService(service);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage thisStage = (Stage) signupButton.getScene().getWindow();
        thisStage.close();
    }

    public void setService(Service service) {
        this.service = service;
    }
}

