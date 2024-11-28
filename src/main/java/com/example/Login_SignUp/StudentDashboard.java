package com.example.Login_SignUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentDashboard extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentDashboard.class.getResource("/com/example/MainInterface/StudentDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("ExcelOne");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}