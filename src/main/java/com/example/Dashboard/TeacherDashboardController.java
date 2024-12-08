package com.example.Dashboard;

import com.example.Account.Account;
import com.example.Account.Learner;
import com.example.Database.*;
import com.example.Login_SignUp.Email;
import com.example.Course_content.Course_Info;
import com.example.Course_content.Course_Info_Controller;
import com.example.Database.*;
import com.example.Login_SignUp.LoggedInUser;
import com.example.Login_SignUp.LoginPageApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.List;


public class TeacherDashboardController {
    @FXML
    private Button addCoursesBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button addCredentialsBtn;
    @FXML
    private Text coursesOfferedCTR;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Text dashboardEmail;
    @FXML
    private Text dashboardFirstName;
    @FXML
    private Text dashboardLastName;
    @FXML
    private AnchorPane dashboardPanel;
    @FXML
    private ImageView dashboardProfilePicture;
    @FXML
    private Text dashboardUniversity;
    @FXML
    private StackPane instructorDashboardStackPane;
    @FXML
    private AnchorPane interfacePane;
    @FXML
    private Button meetingsBtn;
    @FXML
    private Text meetingsCTR;
    @FXML
    private ListView<Meeting> meetingsListView;
    @FXML
    private AnchorPane meetingsPanel;
    @FXML
    private Pane myCourseWrapperPane;

    @FXML
    private Button myCoursesBtn;

    @FXML
    private GridPane myCoursesGridPane;

    @FXML
    private AnchorPane myCoursesPanel;
    @FXML
    private Button videoCallBtn;
    @FXML
    private Label cat1Label;
    @FXML
    private Label cat2Label;
    @FXML
    private Label cat3Label;
    @FXML
    private AnchorPane courseInfoAnchorPane;
    @FXML
    private Label courseTitle;
    @FXML
    private Label emailLabel;
    @FXML
    private ListView<EnrolledLearner> enrolledLearnersListView;
    @FXML
    private Label instructorName;
    @FXML
    private Text shortDesc;
    @FXML
    private Button backBtn;
    @FXML
    private Button setMeetingBtn;
    @FXML
    private Spinner<Integer> startMinuteSpinner;
    @FXML
    private Spinner<Integer> startHourSpinner;
    @FXML
    private Spinner<Integer> endHourSpinner;
    @FXML
    private Spinner<Integer> endMinuteSpinner;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button submitScheduleBtn;
    @FXML
    private AnchorPane setScheduleModal;
    @FXML
    private Button setScheduleBackbtn;

    //mga variables
    Account loggedInUser = LoggedInUser.getInstance().getLoggedInAccount();
    CoursesDatabase coursesDatabase = new CoursesDatabase();
    MeetingDatabase meetingDatabase = new MeetingDatabase();
    EnrollmentDatabase enrollmentDatabase = new EnrollmentDatabase();
    Course openedCourse = null;
    private String ip = "https://192.168.1.14:8181/";

    //set VISIBLE sa mga panel
    public void setScheduleModalVisible(boolean visible) {
        setScheduleModal.setVisible(visible);
    }
    public void setMyMeetingsVisible(){
        interfacePane.setVisible(true);
        dashboardPanel.setVisible(false);
        myCoursesPanel.setVisible(false);
        courseInfoAnchorPane.setVisible(false);
        meetingsPanel.setVisible(true);
    }
    public void setCourseInfoVisible(){
        setScheduleModalVisible(false);
        interfacePane.setVisible(false);
        meetingsPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        myCoursesPanel.setVisible(false);
        courseInfoAnchorPane.setVisible(true);
    }
    public void setDashboardPanelVisible(){
        interfacePane.setVisible(true);
        dashboardPanel.setVisible(true);
        myCoursesPanel.setVisible(false);
        meetingsPanel.setVisible(false);
        courseInfoAnchorPane.setVisible(false);
    }
    public void setMyCoursesPanelVisible(){
        dashboardPanel.setVisible(false);
        myCoursesPanel.setVisible(true);
        courseInfoAnchorPane.setVisible(false);
        meetingsPanel.setVisible(false);
    }
    public void openCourseInfo(Course course){
        setCourseInfoVisible();
        setEnroLledLearners(course.getID());
        courseTitle.setText(course.getCourseTitle());
        instructorName.setText(course.getInstructorName());
        cat1Label.setText(course.getCat1());
        cat2Label.setText(course.getCat2());
        cat3Label.setText(course.getCat3());
        shortDesc.setText(course.getShortDesc());
        openedCourse = course;
        System.out.println("yes");
        System.out.println(course.getID() + "" + course.getInstructorID());
    }
    private void setUserInfo(){
        dashboardLastName.setText(loggedInUser.getLastName());
        dashboardFirstName.setText(loggedInUser.getFirstName());
        dashboardEmail.setText(loggedInUser.getEmail());
        dashboardUniversity.setText(loggedInUser.getUniversity());
        coursesOfferedCTR.setText(coursesDatabase.getCourseCTR(loggedInUser.getID()));
        meetingsCTR.setText(meetingDatabase.countMeetings(loggedInUser.getID()));
    }
    private void activateBackBtn(){
        interfacePane.setVisible(true);
        meetingsPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        courseInfoAnchorPane.setVisible(false);
        setMyCoursesPanelVisible();
    }
    //for da meeting
    public void setMeetingsPanelVisible(){
        meetingsPanel.setVisible(true);
        dashboardPanel.setVisible(false);
        myCoursesPanel.setVisible(false);
    }
    private void checkMeetingTime(Meeting meeting) {
        LocalDateTime currentTime = LocalDateTime.now();

        LocalDateTime meetingStartTime = meeting.getTimeStart();
        LocalDateTime meetingEndTime = meeting.getTimeEnd();


        if (currentTime.isAfter(meetingStartTime) && currentTime.isBefore(meetingEndTime)) {
            videoCallBtn.setDisable(false);
            videoCallBtn.setText("Wait for Schedule");
        } else {
            videoCallBtn.setDisable(true);
            videoCallBtn.setText("Start Video Call");
        }
    }
    private void toggleVideoCallButton() {
        videoCallBtn.setDisable(meetingsListView.getItems().isEmpty());
    }
    private void createMyCourses(int[] courses) {
        int gridCtr = 0;
        int row = 0;
        final double rowHeight = 220.0;
        final double columnWidth = 390.0;
        for(int i : courses) {
            Pane outerPane = new Pane();
            outerPane.setPrefSize(columnWidth, rowHeight);

            myCoursesGridPane.add(outerPane, gridCtr++, row);

            Pane innerPane = new Pane();
            innerPane.setPrefSize(370, 192);
            Image courseImage = CoursesDatabase.getImage(Integer.toString(i));
            innerPane.setStyle("-fx-background-radius: 10; -fx-background-color: blue;");
            innerPane.layoutXProperty().bind(outerPane.widthProperty().subtract(innerPane.prefWidthProperty()).divide(2));
            innerPane.layoutYProperty().bind(outerPane.heightProperty().subtract(innerPane.prefHeightProperty()).divide(2));
            innerPane.setCursor(Cursor.HAND);
            outerPane.getChildren().add(innerPane);

            ImageView courseProfile = new ImageView();
            courseProfile.setFitWidth(370);
            courseProfile.setFitHeight(192);
            courseProfile.setPreserveRatio(true);
            courseProfile.setSmooth(true);
            courseProfile.setImage(courseImage);
            double scale = Math.max(370 / courseImage.getWidth(), 192 / courseImage.getHeight());
            double viewportWidth = 370 / scale;
            double viewportHeight = 192 / scale;
            double viewportX = (courseImage.getWidth() - viewportWidth) / 2;
            double viewportY = (courseImage.getHeight() - viewportHeight) / 2;
            courseProfile.setViewport(new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight));
            Rectangle clip = new Rectangle(370, 192);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            courseProfile.setClip(clip);
            innerPane.getChildren().add(courseProfile);

            Pane innerDesignPane = new Pane();
            innerPane.setId("innerPane"+i);
            innerDesignPane.setPrefSize(370, 73);
            innerDesignPane.setStyle("-fx-background-radius: 0 0 10 10; -fx-background-color: white;");
            innerDesignPane.layoutYProperty().bind(innerPane.heightProperty().subtract(innerDesignPane.prefHeightProperty()));
            innerPane.getChildren().add(innerDesignPane);

            Text courseTitle = new Text();
            courseTitle.setWrappingWidth(345);
            courseTitle.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.wrappingWidthProperty()).divide(2));
            courseTitle.layoutYProperty().bind(innerDesignPane.heightProperty().subtract(courseTitle.getBoundsInLocal().getHeight()).divide(2));
            courseTitle.setText(CoursesDatabase.getCourseTitle( Integer.toString(i)));
            courseTitle.setTextAlignment(TextAlignment.CENTER);
            courseTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            innerDesignPane.getChildren().add(courseTitle);

            Text instructorName = new Text();
            instructorName.setWrappingWidth(345);
            instructorName.layoutXProperty().bind(innerDesignPane.widthProperty().subtract(courseTitle.wrappingWidthProperty()).divide(2));
            instructorName.layoutYProperty().bind(courseTitle.layoutYProperty().add(courseTitle.getBoundsInLocal().getHeight()).add(5));
            instructorName.setText(CoursesDatabase.getInstructorName( Integer.toString(i)));
            instructorName.setTextAlignment(TextAlignment.CENTER);
            instructorName.setStyle("-fx-font-size: 14px;");
            innerDesignPane.getChildren().add(instructorName);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(220.0);
            rowConstraints.setPrefHeight(220.0);
            rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);

            double newHeight = (row + 1) * rowHeight;
            myCourseWrapperPane.setPrefHeight(newHeight);

            //pane is clicked
            innerPane.setOnMouseClicked(event -> {
                String id;
                try {
                    id = new InstructorDatabase().getInstructorID(Integer.toString(i));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String courseName = CoursesDatabase.getCourseTitle(Integer.toString(i));
                String courseInstructorName = CoursesDatabase.getInstructorName(Integer.toString(i));
                coursesDatabase = CoursesDatabase.getCourseData(String.valueOf(i));
                Course course = new Course(i, courseInstructorName, courseName, coursesDatabase.getCategory1(), coursesDatabase.getCategory2(), coursesDatabase.getCategory3(), coursesDatabase.getShortDescription());
                openCourseInfo(course);
            });

            myCoursesGridPane.getRowConstraints().add(rowConstraints);
            if (gridCtr == 2) {
                gridCtr = 0;
                row++;
            }
        }
    }
    public void setEnroLledLearners(int courseID) {
        setMeetingBtn.setDisable(true);
        List<EnrolledLearner> learners = enrollmentDatabase.getEnrolledLearners(courseID);
        ObservableList<EnrolledLearner> todayMeetings = FXCollections.observableArrayList(learners);
        enrolledLearnersListView.setStyle("-fx-padding: 0; -fx-border-width: 0;");
        enrolledLearnersListView.setCellFactory(param -> new EnrolledLearnerFactory());
        enrolledLearnersListView.setFixedCellSize(-1);
        enrolledLearnersListView.setItems(todayMeetings);
    }
    protected boolean startMeeting() {
        if (videoCallBtn != null) {
            videoCallBtn.setDisable(false);
            videoCallBtn.setText("Start Video Call");
            return true;
        }
        return false;
    }
    private String getIP(){
        return this.ip;
    }
    private void openWebPage() {
        try {
            Desktop.getDesktop().browse(new URI(getIP()));
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void setMeetings(int teacherID) {
        MeetingDatabase meetingDatabase = new MeetingDatabase();
        List<Meeting> meetings = meetingDatabase.getUpcomingMeetings(teacherID);
        ObservableList<Meeting> todayMeetings = FXCollections.observableArrayList(meetings);

        meetingsListView.setCellFactory(param -> new MeetingCellFactory());

        meetingsListView.setItems(todayMeetings);

        toggleVideoCallButton();
    }

    public void setupSpinners(){
        SpinnerValueFactory<Integer> startHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 23);
        startHourValueFactory.setWrapAround(true);
        startHourValueFactory.setValue(1);
        startHourSpinner.setValueFactory(startHourValueFactory);
        startHourSpinner.setPromptText("Hour");
        startHourSpinner.getValueFactory().setValue(1);
        SpinnerValueFactory<Integer> endHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 23);
        endHourValueFactory.setWrapAround(true);
        endHourValueFactory.setValue(1);
        endHourSpinner.setValueFactory(endHourValueFactory);
        endHourSpinner.setPromptText("Hour");

        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        minuteValueFactory.setWrapAround(true);
        minuteValueFactory.setValue(0);
        startMinuteSpinner.setValueFactory(minuteValueFactory);
        startMinuteSpinner.setPromptText("Minutes");

        SpinnerValueFactory<Integer> endMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        endMinuteValueFactory.setWrapAround(true);
        endMinuteValueFactory.setValue(0);
        endMinuteSpinner.setValueFactory(endMinuteValueFactory);
        endMinuteSpinner.setPromptText("Minutes");

    }
    public LocalDateTime[] getStartAndEndDateTime() {
        LocalDateTime selectedDate = datePicker.getValue().atStartOfDay(); // Default to start of the day

        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();

        LocalDateTime startDateTime = selectedDate.withHour(startHour).withMinute(startMinute);

        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();


        LocalDateTime endDateTime = selectedDate.withHour(endHour).withMinute(endMinute);
        System.out.println(startDateTime + "\n" + endDateTime);
        return new LocalDateTime[] {startDateTime, endDateTime};
    }
    public void refreshFields(){
        datePicker.setValue(LocalDate.from(LocalDateTime.now()));
        startHourSpinner.getValueFactory().setValue(1);
        startMinuteSpinner.getValueFactory().setValue(1);
        endMinuteSpinner.getValueFactory().setValue(0);
        endHourSpinner.getValueFactory().setValue(0);
    }
    public void connectDBandSetSchedule(EnrolledLearner learner, Course course, LocalDateTime[] localDateTime) {
        System.out.println(learner.getLearner().getID() + " " +  course.getInstructorID());
        meetingDatabase.scheduleMeeting(learner.getLearner().getID(), loggedInUser.getID(), course.getID(), localDateTime[0], localDateTime[1]);
    }
    public boolean validateInputs() {

        if (datePicker.getValue() == null) {
            showAlert("Invalid Input", "Please select a date.");
            return false;
        }

        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();

        if (startHour < 0 || startHour > 23) {
            showAlert("Invalid Input", "Start hour must be between 0 and 23.");
            return false;
        }
        if (startMinute < 0 || startMinute > 59) {
            showAlert("Invalid Input", "Start minute must be between 0 and 59.");
            return false;
        }
        if (endHour < 0 || endHour > 23) {
            showAlert("Invalid Input", "End hour must be between 0 and 23.");
            return false;
        }
        if (endMinute < 0 || endMinute > 59) {
            showAlert("Invalid Input", "End minute must be between 0 and 59.");
            return false;
        }

        if (endHour < startHour || (endHour == startHour && endMinute <= startMinute)) {
            showAlert("Invalid Input", "End time must be after start time.");
            return false;
        }
        return true;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void goToLogin() {
        Stage addCredentialsStage = new Stage();
        LoginPageApplication loginPageApplication = new LoginPageApplication();
        try {
            loginPageApplication.start(addCredentialsStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        addCredentialsStage = (Stage) instructorDashboardStackPane.getScene().getWindow();
        addCredentialsStage.close();
    }




    public void initialize() {
        setUserInfo();
        setDashboardPanelVisible();
        setMeetings(loggedInUser.getID());


        //actionEvents
        enrolledLearnersListView.setOnMouseClicked(event -> {
            EnrolledLearner selectedLearner = enrolledLearnersListView.getSelectionModel().getSelectedItem();
            setupSpinners();
            if (selectedLearner != null) {
                setMeetingBtn.setDisable(false);
                System.out.println("Selected Learner: " + selectedLearner.getLearner().getFirstName() + " " + selectedLearner.getLearner().getLastName());
                setMeetingBtn.setOnAction(actionEvent -> {
                    setScheduleModalVisible(true);
                });

                setScheduleBackbtn.setOnAction(actionEvent -> {
                    setScheduleModalVisible(false);
                    refreshFields();
                });
                submitScheduleBtn.setOnAction(actionEvent -> {
                    if(validateInputs()){
                        LocalDateTime[] localDateTime =  getStartAndEndDateTime();
                        if(openedCourse != null){
                            connectDBandSetSchedule(selectedLearner, openedCourse, localDateTime);
                            Email mail = new Email();
                            mail.setupServer();
                            try {
                                mail.sendScheduleNotification(selectedLearner, openedCourse, localDateTime);
                                mail.sendEmail();
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }else{
                            System.out.println("Course not opened");
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Meeting set successfully");
                        alert.showAndWait();

                        setScheduleModalVisible(false);
                        refreshFields();
                    }else{
                        System.out.println("sayop sa date");
                    }
                });
            }else{
                setMeetingBtn.setDisable(true);
            }
        });
        backBtn.setOnAction(actionEvent -> {
            activateBackBtn();
            refreshFields();
        });

        meetingsBtn.setOnAction(actionEvent -> {
            setMyMeetingsVisible();
            setMeetings(loggedInUser.getID());
            videoCallBtn.setDisable(true);
            meetingDatabase.deleteExpiredMeetings();
        });
        myCoursesBtn.setOnAction(actionEvent -> {
            setMyCoursesPanelVisible();
            int[] coursesEnrolled = coursesDatabase.getCoursesInstructor(loggedInUser.getID());
            createMyCourses(coursesEnrolled);
        });
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
        addCoursesBtn.setOnAction(actionEvent -> {
            Stage addCoursesStage = new Stage();
            AddCourse addCourse = new AddCourse();
            try {
                addCourse.start(addCoursesStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            addCoursesStage = (Stage) instructorDashboardStackPane.getScene().getWindow();
            addCoursesStage.close();
        });
        logoutBtn.setOnAction(actionEvent -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                LoggedInUser l = LoggedInUser.getInstance();
                l.clearLoggedInAccount();
                goToLogin();
            }

        });
        meetingsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                checkMeetingTime(newValue);
            }
        });
        meetingsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                startMeeting();
            }
        });
        videoCallBtn.setOnAction(event -> openWebPage());

        meetingsBtn.setOnAction(actionEvent -> {
            setMeetingsPanelVisible();
            videoCallBtn.setDisable(true);
            meetingDatabase.deleteExpiredMeetings();
        });
    }
}
