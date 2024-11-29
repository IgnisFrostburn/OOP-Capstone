package com.example.Dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class TeacherDashboardController {
    @FXML
    private StackPane instructorDashboardStackPane;

    @FXML
    private Text coursesOfferedCTR;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Text dashboardEmail;

    @FXML
    private Text dashboardName;

    @FXML
    private Text dashboardUniversity;

    @FXML
    private Text meetingsTodayCTR;

    @FXML
    private Button addCredentialsBtn;

    public void initialize() {
        coursesOfferedCTR.setText("2"); // Example: Set to the count of courses offered
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
    }
}
