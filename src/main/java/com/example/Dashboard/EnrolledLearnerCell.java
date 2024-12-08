package com.example.Dashboard;

import com.example.Account.Learner;
import com.example.Database.Meeting;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class EnrolledLearnerCell {

    @FXML
    private Label dateEnrolled;

    @FXML
    private Label learnerEmail;

    @FXML
    private Label learnerName;

    public void setInfo(EnrolledLearner enrolledLearner, LocalDateTime localDateTime) {
        dateEnrolled.setText(String.valueOf(localDateTime));
        learnerEmail.setText(enrolledLearner.getLearner().getEmail());
        learnerName.setText(enrolledLearner.getLearner().getFirstName() + " " + enrolledLearner.getLearner().getLastName());
    }

}
