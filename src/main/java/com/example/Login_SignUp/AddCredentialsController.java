package com.example.Login_SignUp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
}
