package com.example.Dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddCourseController {

    @FXML
    private TextField courseTitleField;
    @FXML
    private TextField categoryField1;
    @FXML
    private TextField categoryField2;
    @FXML
    private TextField categoryField3;
    @FXML
    private TextArea courseDescriptionArea;
    @FXML
    private TextField durationField;
    @FXML
    private TextField scheduleField;

    @FXML
    private void initialize() {
        // Initialization code can go here
    }

    @FXML
    private void handleAddButtonAction() {
        String title = courseTitleField.getText();
        String category1 = categoryField1.getText();
        String category2 = categoryField2.getText();
        String category3 = categoryField3.getText();
        String description = courseDescriptionArea.getText();
        String duration = durationField.getText();
        String schedule = scheduleField.getText();

        if (validateInputs(title, category1, category2, category3, description, duration, schedule)) {
            // Print the inputs to the console
            System.out.println("Course Title: " + title);
            System.out.println("Category 1: " + category1);
            System.out.println("Category 2: " + category2);
            System.out.println("Category 3: " + category3);
            System.out.println("Course Description: " + description);
            System.out.println("Duration: " + duration);
            System.out.println("Schedule: " + schedule);

            // Add course logic here (e.g., saving to a database or file)
            showAlert(AlertType.INFORMATION, "Course Added", "The course has been successfully added yehey akong oten niutog.");
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
