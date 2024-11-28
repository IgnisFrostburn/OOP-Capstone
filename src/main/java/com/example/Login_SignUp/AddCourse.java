package com.example.Login_SignUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AddCourse extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("ExcelOne");
        FXMLLoader loader = new FXMLLoader(AddCourse.class.getResource("AddCourse.fxml"));
        StackPane root = loader.load();
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
