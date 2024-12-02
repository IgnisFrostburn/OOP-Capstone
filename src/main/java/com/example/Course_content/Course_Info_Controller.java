package com.example.Course_content;

import com.example.Dashboard.StudentDashboard;
import com.example.Database.CoursesDatabase;
import com.example.Database.InstructorDatabase;
import com.example.Database.InstructorsInfoDatabase;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.sql.SQLException;


public class Course_Info_Controller {
    @FXML
    private AnchorPane courseInfoAnchorPane;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Text instructorName;
    @FXML
    private Text schoolName;
    @FXML
    private Text teachingExperience1;
    @FXML
    private Text teachingExperience2;
    @FXML
    private Text teachingExperience3;
    @FXML
    private Text teachingExpertise1;
    @FXML
    private Text teachingExpertise2;
    @FXML
    private Text teachingExpertise3;
    @FXML
    private ImageView linkedInImage;
    @FXML
    private Label courseTitle;
    @FXML
    private Text category1;
    @FXML
    private Text category2;
    @FXML
    private Text category3;
    @FXML
    private Text shortDescription;
    @FXML
    private Button backBtn;

    private static String instructor;
    private static String title;
    private static String ID;
    private static String courseID;
    private File pfp;
    private String url;

    public static void setNameAndTitle(String course_title, String instructor_name, String id, String course_id) {
       instructor = instructor_name;
       title = course_title;
       ID = id;
       courseID = course_id;
    }

    public void initializeInstructorInfo(InstructorsInfoDatabase instructorsInfoDatabase) throws SQLException {
        teachingExperience1.setText(instructorsInfoDatabase.getTeachingExperience_1());
        teachingExperience2.setText(instructorsInfoDatabase.getTeachingExperience_2());
        teachingExperience3.setText(instructorsInfoDatabase.getTeachingExperience_3());
        teachingExpertise1.setText(instructorsInfoDatabase.getTeachingExpertise_1());
        teachingExpertise2.setText(instructorsInfoDatabase.getTeachingExpertise_2());
        teachingExpertise3.setText(instructorsInfoDatabase.getTeachingExpertise_3());
        url = instructorsInfoDatabase.getLinkedInURL();
        schoolName.setText(InstructorDatabase.getUniversity(ID));
    }

    public void initializeCourseInfo() {
        CoursesDatabase coursesDatabase;
        coursesDatabase = CoursesDatabase.getCourseData(courseID);
        category1.setText(coursesDatabase.getCategory1());
        category2.setText(coursesDatabase.getCategory2());
        category3.setText(coursesDatabase.getCategory3());
        shortDescription.setText(coursesDatabase.getShortDescription());
    }

    public void openLink(String url) {
        try {
            String[] str = url.split(":");
            if(str[0].equals("https")) Desktop.getDesktop().browse(new URI(url));
            else Desktop.getDesktop().browse(new URI("https://"+url));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void setCourseImage(File file) {
        Image image = new Image(file.toURI().toString());
        double scale = Math.max(150 / image.getWidth(), 150 / image.getHeight());
        double viewportWidth = 150 / scale;
        double viewportHeight = 150 / scale;
        double viewportX = (image.getWidth() - viewportWidth) / 2;
        double viewportY = (image.getHeight() - viewportHeight) / 2;
        profilePicture.setViewport(new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight));
        profilePicture.setImage(image);
    }

    @FXML
    public void initialize() throws SQLException {
        instructorName.setText(instructor);
        courseTitle.setText(title);
        InstructorsInfoDatabase instructorsInfoDatabase;
        instructorsInfoDatabase = InstructorsInfoDatabase.instructorDetails(ID);
        initializeInstructorInfo(instructorsInfoDatabase);
        linkedInImage.setOnMouseClicked(event -> openLink(url));
        File profileImage = instructorsInfoDatabase.getProfileImage(courseID);

        if(profileImage != null && profileImage.exists()) setCourseImage(profileImage);
        else System.out.println("file does not exist");

        initializeCourseInfo();

        profilePicture.setPreserveRatio(true);
        profilePicture.setSmooth(true);

        backBtn.setOnAction(actionEvent -> {
            Stage courseInfoStage = new Stage();
            StudentDashboard studentDashboard = new StudentDashboard();
            try {
                studentDashboard.start(courseInfoStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            courseInfoStage = (Stage) courseInfoAnchorPane.getScene().getWindow();
            courseInfoStage.close();
        });
    }
}














