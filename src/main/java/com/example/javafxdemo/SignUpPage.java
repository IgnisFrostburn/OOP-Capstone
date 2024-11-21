package com.example.javafxdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SignUpPage.class.getResource("SignUpPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("ExcelOne");
//        Image icon = new Image("C:\\Users\\Mcxine and Mckyla\\IdeaProjects\\Lessonn\\img\\logowhitenotext.png");
//        stage.getIcons().add(icon);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
