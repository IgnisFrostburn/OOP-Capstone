package com.example.Dashboard;

import com.example.Account.Learner;
import com.example.Database.Meeting;
import com.example.Login_SignUp.MeetingCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;

public class EnrolledLearnerFactory extends ListCell<EnrolledLearner> {

    @Override
    protected void updateItem(EnrolledLearner item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Login_SignUp/enrolledLearnerCell.fxml"));
                AnchorPane cellLayout = loader.load();

                EnrolledLearnerCell controller = loader.getController();
                String enrollmentDate = item.getEnrollmentDate();
                controller.setInfo(item, enrollmentDate);

                setGraphic(cellLayout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
