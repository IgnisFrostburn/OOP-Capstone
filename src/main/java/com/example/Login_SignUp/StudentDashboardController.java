package com.example.Login_SignUp;

import com.example.Database.InstructorDatabase;
import com.example.Database.InstructorsInfoDatabase;
import com.example.Database.LearnerDatabase;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.SQLException;


public class StudentDashboardController {
    @FXML
    private StackPane studentDashBoardStackPane;
    @FXML
    private Text coursesEnrolledCTR;

    @FXML
    private ScrollPane browseScrollPane;
    @FXML
    private Button dashboardBtn;

    @FXML
    private Pane browseCourseWrapperPane;
    @FXML
    private Text dashboardEmail;
    @FXML
    private GridPane browseCourseGridPane;
    @FXML
    private Text dashboardName;
    @FXML
    private Pane browseCourseInnerGridPane;

    @FXML
    private Text dashboardUniversity;

    @FXML
    private Text meetingsTodayCTR;

    private int coursesCtr = 0;
    private int gridCtr = 0;
    private int row = 0;
    private final double rowHeight = 220.0;
    private final double columnWidth = 390.0;
    private static final double innerPaneSize = 50.0;

    public void initialize() throws SQLException {
        InstructorsInfoDatabase instructorsInfoDatabase = new InstructorsInfoDatabase();
        coursesCtr = instructorsInfoDatabase.numberOfCourses();
        LearnerDatabase learnerDB = new LearnerDatabase();
        coursesEnrolledCTR.setText("1");
        for(int i = 0; i < coursesCtr; i++) {
            System.out.println("addRow pressed and row is " + row);
            Pane outerPane = new Pane();
            outerPane.setPrefSize(columnWidth, rowHeight);

            Pane innerPane = new Pane();
            innerPane.setPrefSize(370, 192);
            innerPane.setStyle("-fx-background-radius: 10; -fx-background-color: blue;");
            innerPane.layoutXProperty().bind(outerPane.widthProperty().subtract(innerPane.prefWidthProperty()).divide(2));
            innerPane.layoutYProperty().bind(outerPane.heightProperty().subtract(innerPane.prefHeightProperty()).divide(2));
            innerPane.setCursor(Cursor.HAND);
            outerPane.getChildren().add(innerPane);

            Pane innerDesignPane = new Pane();
            innerDesignPane.setPrefSize(370, 73);
            innerDesignPane.setStyle("-fx-background-radius: 0 0 10 10; -fx-background-color: white;");
            innerDesignPane.layoutYProperty().bind(innerPane.heightProperty().subtract(innerDesignPane.prefHeightProperty()));
            innerPane.getChildren().add(innerDesignPane);

            Label courseTitle = new Label();
            courseTitle.setPrefSize(345, 58);
            courseTitle.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.prefWidthProperty()).divide(2));
            courseTitle.layoutYProperty().bind(innerDesignPane.heightProperty().subtract(courseTitle.prefHeightProperty()).divide(2));
            courseTitle.setText("Course Title");
            courseTitle.setAlignment(Pos.CENTER);
            innerDesignPane.getChildren().add(courseTitle);

            browseCourseGridPane.add(outerPane, gridCtr++, row);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(220.0);
            rowConstraints.setPrefHeight(220.0);
            rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);

            double newHeight = (row + 1) * rowHeight;
            browseCourseWrapperPane.setPrefHeight(newHeight);

            browseCourseGridPane.getRowConstraints().add(rowConstraints);
            if (gridCtr == 2) {
                gridCtr = 0;
                row++;
            }
        }
    }
}





