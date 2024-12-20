    package com.example.Dashboard;

    import com.example.Course_content.Course_Info;
    import com.example.Database.CoursesDatabase;
    import com.example.Database.InstructorDatabase;
    import com.example.Login_SignUp.LoggedInUser;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.geometry.Rectangle2D;
    import javafx.scene.control.*;
    import javafx.scene.control.Alert.AlertType;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.StackPane;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;

    import java.io.File;
    import java.net.URISyntaxException;
    import java.sql.SQLException;

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
        @FXML
        private ImageView courseImage;

        private File selectedFile;
        private String id;


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
            setCourseImage(selectedFile);
        }

        public void buttonEffects(Button button) {
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #096ff6; -fx-text-fill: white;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;"));
            button.setOnMousePressed(e -> button.setStyle("-fx-background-color: #0d2fc7; -fx-text-fill: white;"));
            button.setOnMouseReleased(e -> button.setStyle("-fx-background-color: #096ff6; -fx-text-fill: white;"));
        }

        public void setCourseImage(File file) {
            Image image = new Image(file.toURI().toString());
            double scale = Math.max(370 / image.getWidth(), 192 / image.getHeight());
            double viewportWidth = 370 / scale;
            double viewportHeight = 192 / scale;
            double viewportX = (image.getWidth() - viewportWidth) / 2;
            double viewportY = (image.getHeight() - viewportHeight) / 2;
            courseImage.setViewport(new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight));
            courseImage.setImage(image);
        }

        public void closePane() {
            Stage teacherDashboardStage = new Stage();
            TeacherDashboard teacherDashboard = new TeacherDashboard();
            try {
                teacherDashboard.start(teacherDashboardStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            teacherDashboardStage = (Stage) addCourseStackPane.getScene().getWindow();
            teacherDashboardStage.close();
        }


        @FXML
        public void initialize() throws URISyntaxException {
            initializeArrayList();
            categoryComboBox1.setItems(categoryObservableList);
            categoryComboBox2.setItems(categoryObservableList);
            categoryComboBox3.setItems(categoryObservableList);
            buttonEffects(addCourseBtn);
            buttonEffects(uploadBtn);
            buttonEffects(cancelBtn);
            File tempImage = new File(getClass().getResource("/images/1.png").toURI());
            setCourseImage(tempImage);


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
                        id = instructorDatabase.getInstructorID(LoggedInUser.getInstance().getLoggedInAccount().getEmail());
                        CoursesDatabase coursesDatabase = new CoursesDatabase(courseTitleField.getText(), categoryComboBox1.getValue(), categoryComboBox2.getValue(), categoryComboBox3.getValue(), courseDescriptionArea.getText());
                        if(!CoursesDatabase.maxCoursesReached(id)) coursesDatabase.insertData(id, selectedFile);
                        else {
                            System.out.println("max courses reached");
                            addCourseBtn.setDisable(true);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                closePane();
            });

            cancelBtn.setOnAction(actionEvent -> {
                closePane();
            });

            uploadBtn.setOnAction(actionEvent -> {
                uploadImage();
            });
        }
    }
