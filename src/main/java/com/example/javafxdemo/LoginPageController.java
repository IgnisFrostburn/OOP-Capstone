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
    @FXML
    private AnchorPane loginAnchorPane;

    Stage stage;

    @FXML
    protected void onSignUpButtonClick(ActionEvent event) {
        Stage signUpStage = new Stage();
        SignUpPage signUpPage = new SignUpPage();
        try {
            signUpPage.start(signUpStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) loginAnchorPane.getScene().getWindow();
        stage.hide();
    }
}