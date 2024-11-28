package com.example.Dashboard;

import com.example.Database.InstructorsInfoDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCredentialsController {

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

    public void initialize() {
        addCredentialsBtn.setOnMouseEntered(e -> addCredentialsBtn.setStyle("-fx-background-color: #11eece; -fx-text-fill: white;"));
        addCredentialsBtn.setOnMouseExited(e -> addCredentialsBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;"));
        addCredentialsBtn.setOnMousePressed(e -> addCredentialsBtn.setStyle("-fx-background-color: #0ebba2; -fx-text-fill: white;"));
        addCredentialsBtn.setOnMouseReleased(e -> addCredentialsBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;"));

        addCredentialsBtn.setOnAction(actionEvent -> {
            InstructorsInfoDatabase instructorsInfoDatabase = new InstructorsInfoDatabase(getTeachingExperienceField1(), getTeachingExperienceField2(), getTeachingExperienceField3(), getTeachingExpertiseField1(), getTeachingExpertiseField2(), getTeachingExpertiseField3(), getLinkedInUrlField());
            instructorsInfoDatabase.insertData();
        });
    }
}
