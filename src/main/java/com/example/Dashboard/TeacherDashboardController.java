package com.example.Dashboard;

import com.example.Account.Account;
import com.example.Course_content.Course_Info;
import com.example.Course_content.Course_Info_Controller;
import com.example.Database.CoursesDatabase;
import com.example.Database.EnrollmentDatabase;
import com.example.Database.InstructorDatabase;
import com.example.Database.MeetingDatabase;
import com.example.Login_SignUp.LoggedInUser;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.SQLException;


public class TeacherDashboardController {
    @FXML
    private StackPane instructorDashboardStackPane;
    @FXML
    private Button addCoursesBtn;
    @FXML
    private Button addCredentialsBtn;
    @FXML
    private Text coursesOfferedCTR;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Text dashboardEmail;
    @FXML
    private Text dashboardFirstName;
    @FXML
    private Text dashboardLastName;
    @FXML
    private AnchorPane dashboardPanel;
    @FXML
    private ImageView dashboardProfilePicture;
    @FXML
    private Text dashboardUniversity;
    @FXML
    private AnchorPane interfacePane;
    @FXML
    private Text meetingsCTR;
    @FXML
    private ScrollPane browseScrollPane;
    @FXML
    private Button meetingsBtn;
    @FXML
    private Pane myCourseWrapperPane;
    @FXML
    private Button myCoursesBtn;
    @FXML
    private GridPane myCoursesGridPane;
    @FXML
    private AnchorPane myCoursesPanel;


    //mga variables
    Account loggedInUser = LoggedInUser.getInstance().getLoggedInAccount();
    CoursesDatabase coursesDatabase = new CoursesDatabase();
    MeetingDatabase meetingDatabase = new MeetingDatabase();
    EnrollmentDatabase enrollmentDatabase = new EnrollmentDatabase();

    //set VISIBLE sa mga panel
    public void setDashboardPanelVisible(){
        interfacePane.setVisible(true);
        dashboardPanel.setVisible(true);
        myCoursesPanel.setVisible(false);
    }
    public void setMyCoursesPanelVisible(){
        dashboardPanel.setVisible(false);
        myCoursesPanel.setVisible(true);
    }

    private void createMyCourses(int[] courses) {
        int gridCtr = 0;
        int row = 0;
        final double rowHeight = 220.0;
        final double columnWidth = 390.0;
        for(int i : courses) {
            Pane outerPane = new Pane();
            outerPane.setPrefSize(columnWidth, rowHeight);

            myCoursesGridPane.add(outerPane, gridCtr++, row);

            Pane innerPane = new Pane();
            innerPane.setPrefSize(370, 192);
            Image courseImage = CoursesDatabase.getImage(Integer.toString(i));
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
                    id = new InstructorDatabase().getInstructorID(Integer.toString(i));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("final I is " + i);

                String courseName = CoursesDatabase.getCourseTitle(Integer.toString(i));
                String courseInstructorName = CoursesDatabase.getInstructorName(Integer.toString(i));
                Course_Info_Controller.setNameAndTitle(courseName, courseInstructorName, id, Integer.toString(i));
                Stage courseInfoStage = new Stage();
                Course_Info courseInfo = new Course_Info();
                try {
                    courseInfo.start(courseInfoStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                courseInfoStage = (Stage) instructorDashboardStackPane.getScene().getWindow();
                courseInfoStage.close();
            });

            myCoursesGridPane.getRowConstraints().add(rowConstraints);
            if (gridCtr == 2) {
                gridCtr = 0;
                row++;
            }
        }
    }
    private void setUserInfo(){
        dashboardLastName.setText(loggedInUser.getLastName());
        dashboardFirstName.setText(loggedInUser.getFirstName());
        dashboardEmail.setText(loggedInUser.getEmail());
        dashboardUniversity.setText(loggedInUser.getUniversity());
        System.out.println(loggedInUser.getID());
        coursesOfferedCTR.setText(coursesDatabase.getCourseCTR(loggedInUser.getID()));
        meetingsCTR.setText(meetingDatabase.countMeetings(loggedInUser.getID()));
    }

    public void initialize() {
        setUserInfo();
        setDashboardPanelVisible();


        //actionEvents
        myCoursesBtn.setOnAction(actionEvent -> {
            setMyCoursesPanelVisible();
            int[] coursesEnrolled = coursesDatabase.getCoursesInstructor(loggedInUser.getID());
            createMyCourses(coursesEnrolled);
        });
        addCredentialsBtn.setOnAction(actionEvent -> {
            Stage addCredentialsStage = new Stage();
            AddCredentials addCredentials = new AddCredentials();
            try {
                addCredentials.start(addCredentialsStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            addCredentialsStage = (Stage) instructorDashboardStackPane.getScene().getWindow();
            addCredentialsStage.close();
        });
        addCoursesBtn.setOnAction(actionEvent -> {
            Stage addCoursesStage = new Stage();
            AddCourse addCourse = new AddCourse();
            try {
                addCourse.start(addCoursesStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            addCoursesStage = (Stage) instructorDashboardStackPane.getScene().getWindow();
            addCoursesStage.close();
        });
    }
}
