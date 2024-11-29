package com.example.Course_content;

import com.example.Dashboard.StudentDashboard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseContent extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CourseContent.class.getResource("/com/example/Login_SignUp/CourseContent.fxml"));

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