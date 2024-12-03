package com.example.Dashboard;

import com.example.Database.*;
import com.example.Course_content.Course_Info;
import com.example.Course_content.Course_Info_Controller;
import com.example.Login_SignUp.LoggedInUser;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
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
    private Button browseBtn;
    @FXML
    private Button myCoursesBtn;
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
    private AnchorPane interfacePanel;
    @FXML
    private AnchorPane dashboardPanel;
    @FXML
    private AnchorPane myCoursesPanel;
    @FXML
    private AnchorPane browseCoursesPanel;
    @FXML
    private Text dashboardUniversity;
    @FXML
    private Text meetingsTodayCTR;
    @FXML
    private ScrollPane myCourseScrollPane;
    @FXML
    private Pane myCourseWrapperPane;
    @FXML
    private GridPane myCoursesGridPane;

    private int gridCtr = 0;
    private int row = 0;
    private final double rowHeight = 220.0;
    private final double columnWidth = 390.0;

    private void createBrowseCourses(int[] courses) {
        //i is course ID
        for(int i : courses) {
            String id = new CoursesDatabase().getCourseInstructorID(Integer.toString(i));
            Pane outerPane = new Pane();
            outerPane.setPrefSize(columnWidth, rowHeight);

            browseCourseGridPane.add(outerPane, gridCtr++, row);

            //creates the rounded box pane
            Pane innerPane = new Pane();
            innerPane.setPrefSize(370, 192);
            innerPane.setStyle("-fx-background-radius: 10; -fx-background-color: blue;");
            innerPane.layoutXProperty().bind(outerPane.widthProperty().subtract(innerPane.prefWidthProperty()).divide(2));
            innerPane.layoutYProperty().bind(outerPane.heightProperty().subtract(innerPane.prefHeightProperty()).divide(2));
            innerPane.setCursor(Cursor.HAND);
            outerPane.getChildren().add(innerPane);

            //creates and displays the course image
            Image courseImage = CoursesDatabase.getImage(Integer.toString(i));
            ImageView courseProfile = new ImageView();
            courseProfile.setFitWidth(370);
            courseProfile.setFitHeight(192);
            courseProfile.setPreserveRatio(true);
            courseProfile.setSmooth(true);
            courseProfile.setImage(courseImage);
            double scale = Math.max(370 / courseImage.getWidth(), 192 / courseImage.getHeight());
            double viewportWidth = 370 / scale;
            double viewportHeight = 192 / scale;
            double viewportX = (courseImage.getWidth() - viewportWidth) / 2;
            double viewportY = (courseImage.getHeight() - viewportHeight) / 2;
            courseProfile.setViewport(new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight));
            Rectangle clip = new Rectangle(370, 192);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            courseProfile.setClip(clip);
            innerPane.getChildren().add(courseProfile);

            //creates the bottom pane which contains the course title and instructor name
            Pane innerDesignPane = new Pane();
            innerPane.setId("innerPane"+i);
            innerDesignPane.setPrefSize(370, 73);
            innerDesignPane.setStyle("-fx-background-radius: 0 0 10 10; -fx-background-color: white;");
            innerDesignPane.layoutYProperty().bind(innerPane.heightProperty().subtract(innerDesignPane.prefHeightProperty()));
            innerPane.getChildren().add(innerDesignPane);

            //displays the course title
            Text courseTitle = new Text();
            courseTitle.setWrappingWidth(345);
            courseTitle.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.wrappingWidthProperty()).divide(2));
            courseTitle.layoutYProperty().bind(innerDesignPane.heightProperty().subtract(courseTitle.getBoundsInLocal().getHeight()).divide(2));
            courseTitle.setText(CoursesDatabase.getCourseTitle( Integer.toString(i)));
            courseTitle.setTextAlignment(TextAlignment.CENTER);
            courseTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            innerDesignPane.getChildren().add(courseTitle);

            //displays the course instructor
            Text instructorName = new Text();
            instructorName.setWrappingWidth(345);
            instructorName.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.wrappingWidthProperty()).divide(2));
            instructorName.layoutYProperty().bind(courseTitle.layoutYProperty().add(courseTitle.getBoundsInLocal().getHeight()).add(5));
            instructorName.setText(CoursesDatabase.getInstructorName( Integer.toString(i)));
            instructorName.setTextAlignment(TextAlignment.CENTER);
            instructorName.setStyle("-fx-font-size: 14px;");
            innerDesignPane.getChildren().add(instructorName);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(220.0);
            rowConstraints.setPrefHeight(220.0);
            rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);

            double newHeight = (row + 1) * rowHeight;
            browseCourseWrapperPane.setPrefHeight(newHeight);

            //pane is clicked
            innerPane.setOnMouseClicked(event -> {
//                String id = new CoursesDatabase().getCourseInstructorID(Integer.toString(i));
                String courseName = CoursesDatabase.getCourseTitle(Integer.toString(i));
                System.out.println("i is " + i);
                String courseInstructorName = CoursesDatabase.getInstructorName(Integer.toString(i));
                Course_Info_Controller.setNameAndTitle(courseName, courseInstructorName, id, Integer.toString(i));
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
    private void createMyCourses(int[] courses) {
        for(int i : courses) {
            Pane outerPane = new Pane();
            outerPane.setPrefSize(columnWidth, rowHeight);
            int finalI = i;

            myCoursesGridPane.add(outerPane, gridCtr++, row);

            Pane innerPane = new Pane();
            innerPane.setPrefSize(370, 192);
            Image courseImage = CoursesDatabase.getImage(Integer.toString(finalI));
            innerPane.setStyle("-fx-background-radius: 10; -fx-background-color: blue;");
            innerPane.layoutXProperty().bind(outerPane.widthProperty().subtract(innerPane.prefWidthProperty()).divide(2));
            innerPane.layoutYProperty().bind(outerPane.heightProperty().subtract(innerPane.prefHeightProperty()).divide(2));
            innerPane.setCursor(Cursor.HAND);
            outerPane.getChildren().add(innerPane);

            ImageView courseProfile = new ImageView();
            courseProfile.setFitWidth(370);
            courseProfile.setFitHeight(192);
            courseProfile.setPreserveRatio(true);
            courseProfile.setSmooth(true);
            courseProfile.setImage(courseImage);
            double scale = Math.max(370 / courseImage.getWidth(), 192 / courseImage.getHeight());
            double viewportWidth = 370 / scale;
            double viewportHeight = 192 / scale;
            double viewportX = (courseImage.getWidth() - viewportWidth) / 2;
            double viewportY = (courseImage.getHeight() - viewportHeight) / 2;
            courseProfile.setViewport(new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight));
            Rectangle clip = new Rectangle(370, 192);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            courseProfile.setClip(clip);
            innerPane.getChildren().add(courseProfile);

            Pane innerDesignPane = new Pane();
            innerPane.setId("innerPane"+i);
            innerDesignPane.setPrefSize(370, 73);
            innerDesignPane.setStyle("-fx-background-radius: 0 0 10 10; -fx-background-color: white;");
            innerDesignPane.layoutYProperty().bind(innerPane.heightProperty().subtract(innerDesignPane.prefHeightProperty()));
            innerPane.getChildren().add(innerDesignPane);

            Text courseTitle = new Text();
            courseTitle.setWrappingWidth(345);
            courseTitle.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.wrappingWidthProperty()).divide(2));
            courseTitle.layoutYProperty().bind(innerDesignPane.heightProperty().subtract(courseTitle.getBoundsInLocal().getHeight()).divide(2));
            courseTitle.setText(CoursesDatabase.getCourseTitle( Integer.toString(i)));
            courseTitle.setTextAlignment(TextAlignment.CENTER);
            courseTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            innerDesignPane.getChildren().add(courseTitle);

            Text instructorName = new Text();
            instructorName.setWrappingWidth(345);
            instructorName.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.wrappingWidthProperty()).divide(2));
            instructorName.layoutYProperty().bind(courseTitle.layoutYProperty().add(courseTitle.getBoundsInLocal().getHeight()).add(5));
            instructorName.setText(CoursesDatabase.getInstructorName( Integer.toString(i)));
            instructorName.setTextAlignment(TextAlignment.CENTER);
            instructorName.setStyle("-fx-font-size: 14px;");
            innerDesignPane.getChildren().add(instructorName);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(220.0);
            rowConstraints.setPrefHeight(220.0);
            rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);

            double newHeight = (row + 1) * rowHeight;
            myCourseWrapperPane.setPrefHeight(newHeight);

            //pane is clicked
            innerPane.setOnMouseClicked(event -> {
                String id;
                try {
                    id = new InstructorDatabase().getInstructorID(Integer.toString(finalI));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("final I is " + finalI);

                String courseName = CoursesDatabase.getCourseTitle(Integer.toString(i));
                String courseInstructorName = CoursesDatabase.getInstructorName(Integer.toString(i));
                Course_Info_Controller.setNameAndTitle(courseName, courseInstructorName, id, Integer.toString(finalI));
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

            myCoursesGridPane.getRowConstraints().add(rowConstraints);
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
    public void setDashboardPanelVisible(){
        dashboardPanel.setVisible(true);
        myCoursesPanel.setVisible(false);
        browseCoursesPanel.setVisible(false);
    }
    public void setbrowseCoursesPanelVisible(){
        dashboardPanel.setVisible(false);
        myCoursesPanel.setVisible(false);
        browseCoursesPanel.setVisible(true);
    }
    public void setMyCoursesPanelVisible(){
        dashboardPanel.setVisible(false);
        myCoursesPanel.setVisible(true);
        browseCoursesPanel.setVisible(false);
    }


    public void initialize() throws SQLException {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        EnrollmentDatabase enrollmentDB = new EnrollmentDatabase();

        int[] courses = CoursesDatabase.numberOfCourses();
        int[] coursesEnrolled = enrollmentDB.getCourses(loggedInUser.getID());
        System.out.println("IDs of courses: ");
        for(int s: courses) {
            System.out.println(s);
        }
        System.out.println("ENROLLED");
        for(int s: coursesEnrolled) {
            System.out.println(s);
        }


        interfacePanel.setVisible(true);
        setDashboardPanelVisible();
        setUserInfo(loggedInUser, enrollmentDB);

        dashboardBtn.setOnAction(actionEvent -> setDashboardPanelVisible());
        myCoursesBtn.setOnAction(actionEvent -> setMyCoursesPanelVisible());
        browseBtn.setOnAction(actionEvent -> setbrowseCoursesPanelVisible());



        createBrowseCourses(courses);
        createMyCourses(coursesEnrolled);
    }
}





