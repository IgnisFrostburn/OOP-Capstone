package com.example.Login_SignUp;

import com.example.Dashboard.StudentDashboard;
import com.example.Dashboard.TeacherDashboard;
import com.example.Database.InstructorDatabase;
import com.example.Database.LearnerDatabase;
import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;


public class LoginPageController {
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private Button loginBtn;
    @FXML
    private Button loginLearnerBtn;
    @FXML
    private Button loginInstructorBtn;
    @FXML
    private TextField loginPasswordTF;
    @FXML
    private TextField loginEmailTF;
    @FXML
    private Label loginAccountTypeWarning;
    @FXML
    private Button signUpBtn;

    Stage stage;
    private boolean isLearner = false;
    private boolean isInstructor = false;

    private String getLoginEmail() {
        return loginEmailTF.getText();
    }

    private String getLoginPassword() {
        return loginPasswordTF.getText();
    }

    protected boolean checkEmail(String email) throws SQLException {
        if (isLearner) {
            LearnerDatabase learnerDatabase = new LearnerDatabase();
            System.out.println("EMAIL IS TRUE");
            return (learnerDatabase.checkEmail(email)) && !isBlankTF(loginEmailTF);
        } else if (isInstructor) {
            InstructorDatabase instructorDatabase = new InstructorDatabase();
            return (instructorDatabase.checkEmail(email) && !isBlankTF(loginEmailTF));
        }

        return false;
    }

    protected boolean checkPassword(String password, String email) throws SQLException {
        if (isLearner) {
            LearnerDatabase learnerDatabase = new LearnerDatabase();
            System.out.println("PASSWORD IS TRUE");
            return learnerDatabase.checkPassword(password, email);
        } else if (isInstructor) {
            InstructorDatabase instructorDatabase = new InstructorDatabase();
            return instructorDatabase.checkPassword(password, email);
        }
        return false;
    }

    protected void setBorderColor(TextField textField, String color) {
        textField.setStyle("-fx-border-color: " + color + "; -fx-border-width: 2px;");
    }

    @FXML
    protected void onSignUpButtonClick(ActionEvent event) {
        Stage signUpStage = new Stage();
        SignUpPage signUpPage = new SignUpPage();
        try {
            signUpPage.start(signUpStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) loginAnchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected boolean isBlankTF(TextField textField) {
        if (textField.getText().isBlank()) {
            System.out.println("red");
            setBorderColor(textField, "red");
            return true;
        } else setBorderColor(textField, "green");
        return false;
    }


    @FXML
    protected void textFieldChecker(TextField textField) {
        if (textField.getText().isBlank()) {
            setBorderColor(textField, "red");
        } else {
            setBorderColor(textField, "green");
        }
    }

    public void loginButtonEffects(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #084581; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color:  #095197; -fx-text-fill: white;"));
        button.setOnMousePressed(e -> button.setStyle("-fx-background-color: #063562; -fx-text-fill: white;"));
        button.setOnMouseReleased(e -> button.setStyle("-fx-background-color: #084581; -fx-text-fill: white;"));
    }

    public void signupButtonEffects(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #096ff6; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;"));
        button.setOnMousePressed(e -> button.setStyle("-fx-background-color: #0d2fc7; -fx-text-fill: white;"));
        button.setOnMouseReleased(e -> button.setStyle("-fx-background-color: #096ff6; -fx-text-fill: white;"));
    }

    @FXML
    public void initialize() throws SQLException {
        loginButtonEffects(loginBtn);
        signupButtonEffects(signUpBtn);

        loginLearnerBtn.setOnAction(e -> {
            isLearner = true;
            isInstructor = false;
            loginLearnerBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;");
            loginInstructorBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        loginInstructorBtn.setOnAction(e -> {
            isInstructor = true;
            isLearner = false;
            loginInstructorBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;");
            loginLearnerBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        loginBtn.setOnAction(actionEvent -> {
            boolean validEmail = false;
            boolean validPassword = false;
            try {
                validEmail = checkEmail(getLoginEmail());
                validPassword = checkPassword(getLoginPassword(), getLoginEmail());
            } catch (SQLException e) {
                System.out.println("Invalid Email");
            }

            textFieldChecker(loginEmailTF);
            textFieldChecker(loginPasswordTF);

            if (isLearner) {
                loginAccountTypeWarning.setText("");
                try {
                    if (validEmail && validPassword) {
                        LearnerDatabase learnerDatabase = new LearnerDatabase();
                        learnerDatabase.getUserData(getLoginEmail());
                        LoggedInUser loggedInUser = LoggedInUser.getInstance();
                        if (loggedInUser != null) {
                            System.out.println(loggedInUser.getLoggedInAccount().getFirstName());
                        } else {
                            throw new RuntimeException("NO USER FOUND");
                        }

                        Stage studentStage = new Stage();
                        StudentDashboard studentDashboard = new StudentDashboard();
                        try {
                            studentDashboard.start(studentStage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage = (Stage) loginAnchorPane.getScene().getWindow();
                        stage.close();
                    } else {
                        if (!validEmail) setBorderColor(loginEmailTF, "red");
                        else setBorderColor(loginEmailTF, "green");
                        if (!validPassword) setBorderColor(loginPasswordTF, "blue");
                        else setBorderColor(loginPasswordTF, "green");
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else if (isInstructor) {
                loginAccountTypeWarning.setText("");
                try {
                    if (validEmail && validPassword) {
                        InstructorDatabase instructorDatabase = new InstructorDatabase();
                        instructorDatabase.getUserData(getLoginEmail());
                        LoggedInUser loggedInUser = LoggedInUser.getInstance();
                        if (loggedInUser != null) {
                            System.out.println(loggedInUser.getLoggedInAccount().getFirstName() + " " + loggedInUser.getLoggedInAccount().getID());
                        } else {
                            throw new RuntimeException("NO USER FOUND");
                        }

                        Stage teacherStage = new Stage();
                        TeacherDashboard teacherDashboard = new TeacherDashboard();
                        try {
                            teacherDashboard.start(teacherStage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage = (Stage) loginAnchorPane.getScene().getWindow();
                        stage.close();
                    } else {
                        if (validEmail) setBorderColor(loginEmailTF, "red");
                        else setBorderColor(loginEmailTF, "green");
                        if (validPassword) setBorderColor(loginPasswordTF, "red");
                        else setBorderColor(loginPasswordTF, "green");
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else {
                loginAccountTypeWarning.setText("Please choose an account type");
            }
        });

    }
}