package com.example.Login_SignUp;

import com.example.Database.Meeting;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class MeetingCell {

    @FXML
    private Label courseTitle;

    @FXML
    private Label date;

    @FXML
    private Label endTime;

    @FXML
    private Label instructorName;

    @FXML
    private Label startTime;
    public void setMeeting(Meeting meeting) {
        courseTitle.setText(meeting.getCourseTitle());
        instructorName.setText(meeting.getInstructorName());
        date.setText(meeting.getDate());
        startTime.setText(meeting.getStartTimeString());
        endTime.setText(meeting.getEndTimeString());
    }




}
