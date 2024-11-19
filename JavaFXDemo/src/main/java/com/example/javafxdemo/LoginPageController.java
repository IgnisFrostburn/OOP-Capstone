package com.example.javafxdemo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
//    @FXML
//    private Label welcomeText;
//    @FXML
//    private Label clickMeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }

    @FXML
    private AnchorPane loginPane;

    Stage stage;

    @FXML
    protected void onStudentLoginButtonClick(ActionEvent event) {
        Stage studentLoginStage = new Stage();
        StudentLoginPage studentLoginPage = new StudentLoginPage();
        try {
            studentLoginPage.start(studentLoginStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) loginPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onInstructorLoginButtonClick(ActionEvent event) {
        Stage instructorLoginStage = new Stage();
        InstructorLoginPage instructorLoginPage = new InstructorLoginPage();
        try {
            instructorLoginPage.start(instructorLoginStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) loginPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSignUpButtonClick(ActionEvent event) {
        Stage signUpStage = new Stage();
        SignUpPage signUpPage = new SignUpPage();
        try {
            signUpPage.start(signUpStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) loginPane.getScene().getWindow();
        stage.close();
    }
}