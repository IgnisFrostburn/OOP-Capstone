package com.example.Login_SignUp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class TeacherDashboardController {
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

    public void initialize() {
        coursesOfferedCTR.setText("2"); // Example: Set to the count of courses offered
    }
}
