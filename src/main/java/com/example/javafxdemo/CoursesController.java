package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;
import java.util.InputMismatchException;

public class CoursesController {

    @FXML
    private Button addCourseButton;  // Button that adds new course box

    @FXML
    private GridPane gridPane;  // Button that adds new course box

    private int currentRow = 0; // Start from row 0
    private int currentColumn = 0; // Start from column 0



    public void addCourse(){

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
    }


    private Pane createCoursePane() {
        Pane pane = new Pane();
        pane.setId("otherBox");
        pane.setPrefSize(225, 150); // Match the size in the FXML
        //pane.setStyle("-fx-border-color: black; -fx-background-color: lightblue;");

        // Create and configure the ProgressBar
        ProgressBar progressBar = new ProgressBar(0.0); // Default progress to 0.0
        progressBar.setLayoutX(25.0);
        progressBar.setLayoutY(100.0);
        progressBar.setPrefSize(175, 18);

        // Create and configure the Label
        Label label = new Label("Description");
        label.setLayoutX(25.0);
        label.setLayoutY(67.0);

        // Create and configure the Button
        Button button = new Button("Course name");
        button.setLayoutX(25.0);
        button.setLayoutY(25.0);
        button.setMnemonicParsing(false);

        // Add all elements to the Pane
        pane.getChildren().addAll(progressBar, label, button);

        return pane;
    }




}
