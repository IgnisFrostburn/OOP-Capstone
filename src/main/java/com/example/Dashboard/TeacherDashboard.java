package com.example.Dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TeacherDashboard extends Application{

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherDashboard.class.getResource("TeacherDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("ExcelOne");
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
