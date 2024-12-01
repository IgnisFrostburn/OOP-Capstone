package com.example.Dashboard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class JavaFXSampleApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main Layout
        VBox mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Label
        Label label = new Label("Welcome to the JavaFX Sample Application");

        // TextField
        TextField textField = new TextField();
        textField.setPromptText("Enter your name");

        // Button to display text
        Button greetButton = new Button("Greet Me");
        Label greetingLabel = new Label();
        greetButton.setOnAction(e -> {
            String name = textField.getText();
            if (name.isEmpty()) {
                greetingLabel.setText("Hello, Stranger!");
            } else {
                greetingLabel.setText("Hello, " + name + "!");
            }
        });

        // Button to open a new window
        Button openWindowButton = new Button("Open New Window");
        openWindowButton.setOnAction(e -> openNewWindow());

        // Add components to the layout
        mainLayout.getChildren().addAll(label, textField, greetButton, greetingLabel, openWindowButton);

        // Scene and Stage setup
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setTitle("JavaFX Sample App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to open a new window
    private void openNewWindow() {
        Stage newWindow = new Stage();
        newWindow.setTitle("New Window");

        // Layout with a blue background
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center; -fx-background-color: #3498db;"); // Blue background (hex color)

        // Label
        Label label = new Label("PLaceholder!");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 16px;"); // White text

        // Button with white background and styled border
        Button closeButton = new Button("Close");
        closeButton.setStyle(
                "-fx-background-color: white; " +      // White background
                        "-fx-text-fill: #3498db; " +          // Blue text
                        "-fx-border-color: #3498db; " +       // Blue border
                        "-fx-border-width: 2px; " +           // Border thickness
                        "-fx-font-weight: bold; " +           // Bold font
                        "-fx-padding: 8px 16px;"              // Padding for button size
        );

        closeButton.setOnAction(e -> newWindow.close());

        layout.getChildren().addAll(label, closeButton);

        Scene scene = new Scene(layout, 300, 200);
        newWindow.setScene(scene);
        newWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
