package com.example.Dashboard;

import com.example.Login_SignUp.LoggedInUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Meetings extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("ExcelOne");
        FXMLLoader loader = new FXMLLoader(Meetings.class.getResource("/com/example/Dashboard/Meetings.fxml"));
        StackPane root = loader.load();

        MeetingsController controller = loader.getController();
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        controller.setStudentID(loggedInUser.getID());

        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
