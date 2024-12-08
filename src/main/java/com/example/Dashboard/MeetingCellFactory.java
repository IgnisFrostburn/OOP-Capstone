package com.example.Dashboard;

import com.example.Database.Meeting;
import com.example.Login_SignUp.MeetingCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MeetingCellFactory extends ListCell<Meeting> {

    @Override
    protected void updateItem(Meeting item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                // Load the FXML layout for the cell
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Login_SignUp/MeetingCell.fxml"));
                AnchorPane cellLayout = loader.load();

                // Set the controller to populate cell data
                MeetingCell controller = loader.getController();
                controller.setMeeting(item);

                // Set the loaded layout as the graphic for this cell
                setGraphic(cellLayout);

                // Add a style class to differentiate selected and unselected cells
                updateSelectionStyle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ensures that the cell updates its style dynamically when the selection changes.
     */
    private void updateSelectionStyle() {
        // Update the style dynamically based on the selected state
        if (isSelected()) {
            getStyleClass().add("selected-cell");
        } else {
            getStyleClass().remove("selected-cell");
        }

        // Listen to selection changes in the parent ListView
        if (getListView() != null) {
            getListView()
                    .getSelectionModel()
                    .selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (getItem() != null && getItem().equals(newValue)) {
                            getStyleClass().add("selected-cell");
                        } else {
                            getStyleClass().remove("selected-cell");
                        }
                    });
        }
    }
}
