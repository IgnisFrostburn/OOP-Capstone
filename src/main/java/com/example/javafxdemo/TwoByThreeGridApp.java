package com.example.javafxdemo;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class TwoByThreeGridApp extends Application {

    private int currentRow = 0; // Start from row 0
    private int currentColumn = 0; // Start from column 0

    @Override
    public void start(Stage primaryStage) {
        // Create the GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Horizontal gap between columns
        gridPane.setVgap(10); // Vertical gap between rows

        // Add a button to add new panes
        Button addCourseButton = new Button("Add Pane");
        addCourseButton.setOnAction(e -> {
            Pane coursePane = createCoursePane();

            // Add the pane to the grid at the current position
            gridPane.add(coursePane, currentColumn, currentRow);

            // Move to the next cell
            currentColumn++;
            if (currentColumn > 1) { // If two columns are filled, move to the next row
                currentColumn = 0;
                currentRow++;
            }
        });

        // Add the button to the top of the grid (not inside the scrollable area)
        GridPane mainGrid = new GridPane();
        mainGrid.add(addCourseButton, 0, 0); // Fixed position for the button
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true); // Make the scroll pane fit the window width

        // Add the scrollable gridPane to the mainGrid layout
        mainGrid.add(scrollPane, 0, 1);

        // Create the scene
        Scene scene = new Scene(mainGrid, 400, 400);
        primaryStage.setTitle("2x3 Grid Pane Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to create a custom pane for the grid
    private Pane createCoursePane() {
        Pane pane = new Pane();
        pane.setPrefSize(150, 100); // Size for grid cells
        pane.setStyle("-fx-border-color: black; -fx-background-color: lightblue;");

        // Example content: Add a rectangle inside the pane
        Rectangle rect = new Rectangle(10, 10, 30, 30);
        rect.setFill(Color.DARKBLUE);
        pane.getChildren().add(rect);

        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
