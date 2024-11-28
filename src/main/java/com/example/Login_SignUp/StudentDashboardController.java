package com.example.Login_SignUp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class StudentDashboardController {
    @FXML
    private Text coursesEnrolledCTR;

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

    public void initialize(){
        LearnerDatabase learnerDB = new LearnerDatabase();
        coursesEnrolledCTR.setText("1");
    }

}
