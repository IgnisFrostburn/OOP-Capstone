package com.example.Dashboard;

import com.example.Database.InstructorDatabase;
import com.example.Database.InstructorsInfoDatabase;
import com.example.Login_SignUp.LoggedInUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCredentialsController {
    @FXML
    private StackPane addCredentialsStackPane;
    @FXML
    private TextField teachingExperienceField1;
    @FXML
    private TextField teachingExperienceField2;
    @FXML
    private TextField teachingExperienceField3;
    @FXML
    private TextField teachingExpertiseField1;
    @FXML
    private TextField teachingExpertiseField2;
    @FXML
    private TextField teachingExpertiseField3;
    @FXML
    private TextField linkedInUrlField;
    @FXML
    private Button addCredentialsBtn;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button backBtn;

    private File selectedFile;
    private int id;

    public String getTeachingExperienceField1() {
        return teachingExperienceField1.getText();
    }
    public String getTeachingExperienceField2() {
        return teachingExperienceField2.getText();
    }
    public String getTeachingExperienceField3() {
        return teachingExperienceField3.getText();
    }
    public String getTeachingExpertiseField1() {
        return teachingExpertiseField1.getText();
    }
    public String getTeachingExpertiseField2() {
        return teachingExpertiseField2.getText();
    }
    public String getTeachingExpertiseField3() {
        return teachingExpertiseField3.getText();
    }
    public String getLinkedInUrlField() {
        return linkedInUrlField.getText();
    }

    @FXML
    private void handleAddCredentials() {
        String experience1 = teachingExperienceField1.getText();
        String experience2 = teachingExperienceField2.getText();
        String experience3 = teachingExperienceField3.getText();
        String expertise1 = teachingExpertiseField1.getText();
        String expertise2 = teachingExpertiseField2.getText();
        String expertise3 = teachingExpertiseField3.getText();
        String linkedInUrl = linkedInUrlField.getText();

        if (validateInputs(experience1, experience2, experience3, expertise1, expertise2, expertise3, linkedInUrl)) {
            // Add credentials logic here (e.g., saving to a database or file)
            showAlert(AlertType.INFORMATION, "Credentials Added", "The credentials have been successfully added.");
        } else {
            showAlert(AlertType.ERROR, "Input Error", "Please fill in all fields.");
        }
    }

    private boolean validateInputs(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*  .jpg", "*.png", "*.jpeg"));
        selectedFile = fileChooser.showOpenDialog(uploadBtn.getScene().getWindow());
//        if(selectedFile != null) {
//            Image image = new Image(selectedFile.toURI().toString());
//            pfp.setImage(image);
//        }
    }

    public void initialize() throws SQLException {
        addCredentialsBtn.setOnMouseEntered(e -> addCredentialsBtn.setStyle("-fx-background-color: #11eece; -fx-text-fill: white;"));
        addCredentialsBtn.setOnMouseExited(e -> addCredentialsBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;"));
        addCredentialsBtn.setOnMousePressed(e -> addCredentialsBtn.setStyle("-fx-background-color: #0ebba2; -fx-text-fill: white;"));
        addCredentialsBtn.setOnMouseReleased(e -> addCredentialsBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;"));

        id = new InstructorDatabase().getInstructorID(LoggedInUser.getInstance().getEmail());
        if(InstructorsInfoDatabase.dataExists(Integer.toString(id))) {
            ResultSet rs = InstructorsInfoDatabase.instructorDetails(Integer.toString(id));
            if(rs!=null) {
                teachingExperienceField1.setText(rs.getString("teaching_experience1"));
                teachingExperienceField2.setText(rs.getString("teaching_experience2"));
                teachingExperienceField3.setText(rs.getString("teaching_experience3"));
                teachingExpertiseField1.setText(rs.getString("teaching_expertise1"));
                teachingExpertiseField2.setText(rs.getString("teaching_expertise1"));
                teachingExpertiseField3.setText(rs.getString("teaching_expertise1"));
                linkedInUrlField.setText(rs.getString("linkedIN_url"));
            }
        }

        addCredentialsBtn.setOnAction(actionEvent -> {
            InstructorsInfoDatabase instructorsInfoDatabase = new InstructorsInfoDatabase(getTeachingExperienceField1(), getTeachingExperienceField2(), getTeachingExperienceField3(), getTeachingExpertiseField1(), getTeachingExpertiseField2(), getTeachingExpertiseField3(), getLinkedInUrlField());
            instructorsInfoDatabase.editInfo(Integer.toString(id), selectedFile);
        });

        uploadBtn.setOnAction(e -> uploadImage());
        backBtn.setOnAction(actionEvent -> {
            Stage teacherDashboardStage = new Stage();
            TeacherDashboard teacherDashboard = new TeacherDashboard();
            try {
                teacherDashboard.start(teacherDashboardStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            teacherDashboardStage = (Stage) addCredentialsStackPane.getScene().getWindow();
            teacherDashboardStage.close();
        });
    }
}