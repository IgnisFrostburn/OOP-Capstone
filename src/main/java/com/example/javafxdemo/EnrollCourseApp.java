package com.example.javafxdemo;

import javafx.application.Application;
import javafx.stage.Stage;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;


public class EnrollCourseApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main Window Layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        Label message = new Label("Click the button to enroll in a course.");
        Button enrollButton = new Button("Enroll Course");

        // Action for Enroll Button
        enrollButton.setOnAction(e -> openEnrollPopup(primaryStage));

        mainLayout.getChildren().addAll(message, enrollButton);

        // Set up the Scene and Stage
        Scene mainScene = new Scene(mainLayout, 300, 200);
        primaryStage.setTitle("Course Enrollment");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void openEnrollPopup(Stage parentStage) {
        // Create a new Stage (Popup Window)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Enroll in a Course");

        // Layout for Popup Window
        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(15));
        popupLayout.setAlignment(Pos.CENTER);

        Label instruction = new Label("Enter the Course Code:");
        TextField courseCodeField = new TextField();
        courseCodeField.setPromptText("e.g., CS101");
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Submit Button Action
        submitButton.setOnAction(e -> {
            String courseCode = courseCodeField.getText();
            if (!courseCode.isEmpty()) {
                System.out.println("Enrolled in Course: " + courseCode);
                popupStage.close();
            } else {
                System.out.println("Course Code cannot be empty!");
            }
        });

        // Cancel Button Action
        cancelButton.setOnAction(e -> popupStage.close());

        // Layout for Buttons
        HBox buttonLayout = new HBox(10, submitButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Add components to Popup Layout
        popupLayout.getChildren().addAll(instruction, courseCodeField, buttonLayout);

        // Set up the Scene and Stage for Popup
        Scene popupScene = new Scene(popupLayout, 300, 150);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
