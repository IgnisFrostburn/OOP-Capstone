package com.example.Login_SignUp;

import com.example.Database.InstructorDatabase;
import com.example.Database.LearnerDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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

    Stage stage;
    private boolean isLearner = false;
    private boolean isInstructor = false;

    private String getLoginEmail() {
        return loginEmailTF.getText();
    }

    private String getLoginPassword() {
        return loginPasswordTF.getText();
    }

    //returns true if email matches with email database
    protected boolean checkEmail(String email) throws SQLException {
        if(isLearner) {
            LearnerDatabase learnerDatabase = new LearnerDatabase();
            return (learnerDatabase.checkEmail(email)) && !isBlankTF(loginEmailTF);
        } else if(isInstructor) {
            InstructorDatabase instructorDatabase = new InstructorDatabase();
            return (instructorDatabase.checkEmail(email) && !isBlankTF(loginEmailTF));
        }
        return false;
    }

    //returns true if password matches with password database
    protected boolean checkPassword(String password) throws SQLException {
        if(isLearner) {
            LearnerDatabase learnerDatabase = new LearnerDatabase();
            return learnerDatabase.checkPassword(password);
        } else if(isInstructor) {
            InstructorDatabase instructorDatabase = new InstructorDatabase();
            return instructorDatabase.checkPassword(password);
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
        if(textField.getText().isBlank()) {
            System.out.println("red");
            setBorderColor(textField, "red");
            return true;
        } else setBorderColor(textField, "green");
        return false;
    }



    @FXML
    protected void textFieldChecker(TextField textField) {
        if (textField.getText().isBlank()) {setBorderColor(textField, "red");}
        else {setBorderColor(textField, "green");}
    }

    @FXML
    public void initialize() throws SQLException {
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
            textFieldChecker(loginEmailTF);
            textFieldChecker(loginPasswordTF);
            if(isLearner) {
                loginAccountTypeWarning.setText("");
                try {
                    if(checkEmail(getLoginEmail()) && checkPassword(getLoginPassword())) {

                    } else {
                        if(!checkEmail(getLoginEmail())) setBorderColor(loginEmailTF, "red");
                        else setBorderColor(loginEmailTF, "green");
                        if(!checkPassword(getLoginPassword())) setBorderColor(loginPasswordTF, "red");
                        else setBorderColor(loginPasswordTF, "green");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if(isInstructor) {
                loginAccountTypeWarning.setText("");

            } else {
                loginAccountTypeWarning.setText("Please choose an account type");
            }
        });
    }
}