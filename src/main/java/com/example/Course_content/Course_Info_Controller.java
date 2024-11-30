package com.example.Course_content;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;


public class Course_Info_Controller {
    @FXML
    private AnchorPane courseInfoAnchorPane;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Label instructorName;
    @FXML
    private Label schoolName;
    @FXML
    private Label teachingExperience1;
    @FXML
    private Label teachingExperience2;
    @FXML
    private Label teachingExperience3;
    @FXML
    private Label teachingExpertise1;
    @FXML
    private Label teachingExpertise2;
    @FXML
    private Label teachingExpertise3;
    @FXML
    private Label courseTitle;
    @FXML
    private Label category1;
    @FXML
    private Label category2;
    @FXML
    private Label category3;
    @FXML
    private Label shortDescription;

    private static String instructor;
    private static String title;

    public static void setNameAndTitle(String course_title, String instructor_name) {
       instructor = instructor_name;
       title = course_title;
    }

    @FXML
    public void initialize() {
        instructorName.setText(instructor);
        courseTitle.setText(title);

    }
}














