package com.example.Dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.example.Database.EnrollmentDatabase;

public class MeetingsController {

    @FXML
    private ListView<String> meetingsListView;

    @FXML
    private Button videoCallBtn;

    private final EnrollmentDatabase enrollmentDatabase;

    public MeetingsController() {
        this.enrollmentDatabase = new EnrollmentDatabase();
    }

    public void setStudentID(int studentID) {
        int[] courseIDs = enrollmentDatabase.getCourses(studentID);
        List<String> meetings = new ArrayList<>();
        ObservableList<String> todayMeetings = FXCollections.observableArrayList(meetings);
        meetingsListView.setItems(todayMeetings);
        toggleVideoCallButton();
    }

    @FXML
    private void initialize() {
        videoCallBtn.setOnAction(event -> openWebPage());
    }

    private void openWebPage() {
        try {
            Desktop.getDesktop().browse(new URI("https://192.168.1.17:8181"));
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void toggleVideoCallButton() {
        videoCallBtn.setDisable(meetingsListView.getItems().isEmpty());
    }
}
