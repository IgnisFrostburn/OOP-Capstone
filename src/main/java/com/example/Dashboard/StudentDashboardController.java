package com.example.Dashboard;

import com.example.Database.*;
import com.example.Course_content.Course_Info;
import com.example.Course_content.Course_Info_Controller;
import com.example.Login_SignUp.LoggedInUser;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
    private Text dashboardFirstName;
    @FXML
    private Text dashboardLastName;
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

    private void createBrowseCourses(int[] courses) {
        for(int i : courses) {
            System.out.println("addRow pressed and row is " + row);
            Pane outerPane = new Pane();
            outerPane.setPrefSize(columnWidth, rowHeight);


            browseCourseGridPane.add(outerPane, gridCtr++, row);

            Pane innerPane = new Pane();
            innerPane.setPrefSize(370, 192);
            innerPane.setStyle("-fx-background-radius: 10; -fx-background-color: blue;");
            innerPane.layoutXProperty().bind(outerPane.widthProperty().subtract(innerPane.prefWidthProperty()).divide(2));
            innerPane.layoutYProperty().bind(outerPane.heightProperty().subtract(innerPane.prefHeightProperty()).divide(2));
            innerPane.setCursor(Cursor.HAND);
            outerPane.getChildren().add(innerPane);

            Pane innerDesignPane = new Pane();
            innerPane.setId("innerPane"+i);
            innerDesignPane.setPrefSize(370, 73);
            innerDesignPane.setStyle("-fx-background-radius: 0 0 10 10; -fx-background-color: white;");
            innerDesignPane.layoutYProperty().bind(innerPane.heightProperty().subtract(innerDesignPane.prefHeightProperty()));
            innerPane.getChildren().add(innerDesignPane);

            Label courseTitle = new Label();
            courseTitle.setPrefSize(345, 58);
            courseTitle.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.prefWidthProperty()).divide(2));
            courseTitle.layoutYProperty().bind(innerDesignPane.heightProperty().subtract(courseTitle.prefHeightProperty()).divide(2));
            courseTitle.setText(CoursesDatabase.getCourseTitle(i-1));
            courseTitle.setAlignment(Pos.CENTER);
            courseTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            innerDesignPane.getChildren().add(courseTitle);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(220.0);
            rowConstraints.setPrefHeight(220.0);
            rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);

            double newHeight = (row + 1) * rowHeight;
            browseCourseWrapperPane.setPrefHeight(newHeight);
            System.out.println("i is " + i);
            int finalI = i;

            innerPane.setOnMouseClicked(event -> {
                String id;
                try {
                    id = new InstructorDatabase().getInstructorID(Integer.toString(finalI));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("final I is " + finalI);
                String[] strArr = CoursesDatabase.getCourseTitle(finalI-1).split(" - ");
                Course_Info_Controller.setNameAndTitle(strArr[0], strArr[1], id, Integer.toString(finalI));
                Stage courseInfoStage = new Stage();
                Course_Info courseInfo = new Course_Info();
                try {
                    courseInfo.start(courseInfoStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                courseInfoStage = (Stage) studentDashBoardStackPane.getScene().getWindow();
                courseInfoStage.close();
            });

            browseCourseGridPane.getRowConstraints().add(rowConstraints);
            if (gridCtr == 2) {
                gridCtr = 0;
                row++;
            }
        }
    }
    public void setUserInfo(LoggedInUser loggedInUser, EnrollmentDatabase enrollmentDB){
        coursesEnrolledCTR.setText(enrollmentDB.getCourseCTR(loggedInUser.getID()));
        dashboardFirstName.setText(loggedInUser.getFirstName());
        dashboardLastName.setText(loggedInUser.getLastName());
        dashboardEmail.setText(loggedInUser.getEmail());
        dashboardUniversity.setText(loggedInUser.getUniversity());
    }

    public void initialize() throws SQLException {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        EnrollmentDatabase enrollmentDB = new EnrollmentDatabase();

        int[] courses = CoursesDatabase.numberOfCourses();
        System.out.println("IDs of courses: ");
        for(int s: courses) {
            System.out.println(s);
        }

        setUserInfo(loggedInUser, enrollmentDB);
        createBrowseCourses(courses);
    }
}





