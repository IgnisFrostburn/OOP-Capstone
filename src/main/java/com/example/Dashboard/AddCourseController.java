    package com.example.Dashboard;

    import com.example.Database.CoursesDatabase;
    import com.example.Database.InstructorDatabase;
    import com.example.Login_SignUp.LoggedInUser;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.collections.transformation.FilteredList;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.control.Alert.AlertType;
    import javafx.scene.layout.StackPane;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;

    import java.io.File;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.Set;

    public class AddCourseController {
        @FXML
        private StackPane addCourseStackPane;
        @FXML
        private TextField courseTitleField;
        @FXML
        private TextArea courseDescriptionArea;
        @FXML
        private Button cancelBtn;
        @FXML
        private Button addCourseBtn;
        @FXML
        private ComboBox<String> categoryComboBox1;
        @FXML
        private ComboBox<String> categoryComboBox2;
        @FXML
        private ComboBox<String> categoryComboBox3;
        @FXML
        private Label courseDescriptionWarningLabel;
        @FXML
        private Label imageWarningLabel;
        @FXML
        private Button uploadBtn;

        private File selectedFile;
        private int id;


        private ObservableList<String> categoryObservableList;
        private void initializeArrayList() {
            categoryObservableList = FXCollections.observableArrayList(
                    "Aeronautical Engineering",
                    "Aerospace Engineering",
                    "Biosystems Engineering",
                    "Civil Engineering",
                    "Computer Science",
                    "Electrical Engineering",
                    "Electronics Communications Engineering",
                    "Geodetic Engineering",
                    "Industrial Engineering",
                    "Information Technology",
                    "Marine Engineering",
                    "Mechanical Engineering",
                    "Mining Engineering",
                    "Naval Engineering"
            );
        }
        protected void setBorderColor(TextField textField, String color) {
            textField.setStyle("-fx-border-color: " + color + "; -fx-border-width: 2px;");
        }
        protected int checkComboBox(ComboBox<String> comboBox, String value) {
            if(value == null) {
                comboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: white;");
                return 0;
            }
            else comboBox.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-background-color: white;");
            return 1;
        }

        private void uploadImage() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*  .jpg", "*.png", "*.jpeg"));
            selectedFile = fileChooser.showOpenDialog(uploadBtn.getScene().getWindow());
        }

        public void buttonEffects(Button button) {
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #096ff6; -fx-text-fill: white;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;"));
            button.setOnMousePressed(e -> button.setStyle("-fx-background-color: #0d2fc7; -fx-text-fill: white;"));
            button.setOnMouseReleased(e -> button.setStyle("-fx-background-color: #096ff6; -fx-text-fill: white;"));
        }


        @FXML
        public void initialize() {
            initializeArrayList();
            categoryComboBox1.setItems(categoryObservableList);
            categoryComboBox2.setItems(categoryObservableList);
            categoryComboBox3.setItems(categoryObservableList);
            buttonEffects(addCourseBtn);
            buttonEffects(uploadBtn);
            buttonEffects(cancelBtn);



            addCourseBtn.setOnAction(actionEvent -> {
                int ctr = 6;
                if(courseTitleField.getText().isBlank()) setBorderColor(courseTitleField, "red");
                else {
                    setBorderColor(courseTitleField, "green");
                    ctr--;
                }

                ctr -= checkComboBox(categoryComboBox1, categoryComboBox1.getValue());
                ctr -= checkComboBox(categoryComboBox2, categoryComboBox2.getValue());
                ctr -= checkComboBox(categoryComboBox3, categoryComboBox3.getValue());

                if(courseDescriptionArea.getText().length() < 150) {
                    courseDescriptionArea.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    courseDescriptionWarningLabel.setText("Course description is too short. Please write at least 150 characters");
                } else {
                    courseDescriptionArea.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                    courseDescriptionWarningLabel.setText("");
                    ctr--;
                }

                if(selectedFile == null) {
                    imageWarningLabel.setText("Please upload an image");
                } else {
                    imageWarningLabel.setText("");
                    ctr--;
                }

                if(ctr == 0) {
                    InstructorDatabase instructorDatabase = new InstructorDatabase();
                    try {
                        id = instructorDatabase.getInstructorID(LoggedInUser.getInstance().getEmail());
                        CoursesDatabase coursesDatabase = new CoursesDatabase(courseTitleField.getText(), categoryComboBox1.getValue(), categoryComboBox2.getValue(), categoryComboBox3.getValue(), courseDescriptionArea.getText());
                        if(!CoursesDatabase.maxCoursesReached(Integer.toString(id))) coursesDatabase.insertData(Integer.toString(id), selectedFile);
                        else {
                            System.out.println("max courses reached");
                            addCourseBtn.setDisable(true);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            });

            cancelBtn.setOnAction(actionEvent -> {
                Stage teacherDashboardStage = new Stage();
                TeacherDashboard teacherDashboard = new TeacherDashboard();
                try {
                    teacherDashboard.start(teacherDashboardStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                teacherDashboardStage = (Stage) addCourseStackPane.getScene().getWindow();
                teacherDashboardStage.close();
            });

            uploadBtn.setOnAction(actionEvent -> {
                uploadImage();
            });
        }
    }
