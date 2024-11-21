package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PopUp {

    @FXML
    private Button closebtn;

    public void initialize(){
        closebtn.setOnAction(actionEvent -> {
            closePopup();
        });
    }
    private void closePopup() {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }
}
