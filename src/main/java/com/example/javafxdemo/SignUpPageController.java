package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.InputMismatchException;


public class SignUpPageController {
    @FXML
    private StackPane signUpPageStackPane;
    @FXML
    private AnchorPane signUpPageOnePane;
    @FXML
    private Button learnerBtn;
    @FXML
    private Button instructorBtn;
    @FXML
    private Label accountTypeWarningLabel;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField confirmPasswordTF;
    @FXML
    private Button proceedBtn;

    @FXML
    private AnchorPane signUpStudentPane;
    @FXML
    private Button backBtnStudent;
    @FXML
    private Button proceedBtnStudent;

    @FXML
    private AnchorPane signUpInstructorPane;
    @FXML
    private Button backBtnInstructor;
    @FXML
    private Button proceedBtnInstructor;

    Stage stage;
    private boolean isLearner = false;
    private boolean isInstructor = false;
    int ctr = 0;

    private String getEmail() {
        return emailTF.getText();
    }

    private String getPassword() {
        return passwordTF.getText();
    }

    private String getConfirmPassword() {
        return confirmPasswordTF.getText();
    }

    public boolean containsNumbers(String str) {
        return str.matches(".*\\d.*");
    }

    protected boolean checkEmailValidity(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    protected void setBorderColor(TextField textField, String color) {
        textField.setStyle("-fx-border-color: " + color + "; -fx-border-width: 2px;");
    }

    protected void onMouseEntered(Button btn) {
        btn.setOnMouseEntered(e -> {
            if(!isLearner && !isInstructor) {
                if(btn == learnerBtn) learnerBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
                else instructorBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
            } else if (!isLearner) {
                learnerBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
            } else if (!isInstructor) {
                instructorBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
            }
        });
    }

    protected void onMouseExit(Button btn) {
        btn.setOnMouseExited(e -> {
            if (!isLearner) {
                learnerBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
            }
            if (!isInstructor) {
                instructorBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
            }
        });
    }

    protected void onBackButtonClick(Button btn) {
        btn.setOnAction(actionEvent -> {
            if(signUpInstructorPane.isVisible() || signUpStudentPane.isVisible()) {
                signUpPageOnePane.setVisible(true);
                signUpStudentPane.setVisible(false);
                signUpInstructorPane.setVisible(false);
            }
        });
    }

    protected void textFieldChecker(TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (textField.getText().isBlank()) {
                    setBorderColor(textField, "red");
                    ctr++;
                } else if(textField == emailTF) {
                    if(!checkEmailValidity(textField.getText())) setBorderColor(textField, "red");
                    else setBorderColor(textField, "green");
                } else if(textField == confirmPasswordTF) {
                    if(!textField.getText().equals(getPassword())) setBorderColor(textField, "red");
                    else setBorderColor(textField, "green");
                } else {
                    setBorderColor(textField, "green");
                }
            }
        });
    }

    @FXML
    public void initialize() {
        signUpPageOnePane.setVisible(true);
        signUpStudentPane.setVisible(false);
        signUpInstructorPane.setVisible(false);
        onMouseEntered(learnerBtn);
        onMouseEntered(instructorBtn);
        onMouseExit(learnerBtn);
        onMouseExit(instructorBtn);

        learnerBtn.setOnAction(e -> {
            isLearner = true;
            isInstructor = false;
            learnerBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white; -fx-border-color: black;");
            instructorBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });


        instructorBtn.setOnAction(e -> {
            isInstructor = true;
            isLearner = false;
            instructorBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white; -fx-border-color: black;");
            learnerBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        textFieldChecker(emailTF);
        textFieldChecker(passwordTF);
        textFieldChecker(confirmPasswordTF);

        proceedBtn.setOnMouseEntered(e -> proceedBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;"));
        proceedBtn.setOnMouseExited(e -> proceedBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;"));

        proceedBtn.setOnAction(actionEvent -> {
            if(getEmail().isBlank() || !checkEmailValidity(getEmail())) {
                setBorderColor(emailTF, "red");
                ctr++;
            } else setBorderColor(emailTF, "green");

            if(getPassword().isBlank()) {
                setBorderColor(passwordTF, "red");
                ctr++;
            } else setBorderColor(passwordTF, "green");

            if(getConfirmPassword().isBlank() || !getConfirmPassword().equals(getPassword()))  {
                setBorderColor(confirmPasswordTF, "red");
                ctr++;
            } else setBorderColor(confirmPasswordTF, "green");

            try {
                System.out.println(ctr);
                if (ctr > 0) {
                    ctr = 0;
                    throw new RuntimeException("Check Textfields");
                }if (!isLearner && !isInstructor) {
                    accountTypeWarningLabel.setText("Please choose an account type");
                } else {
                    accountTypeWarningLabel.setText("");
                    signUpPageOnePane.setVisible(false);
                    if(isLearner) {
                        signUpStudentPane.setVisible(true);
                    } else if(isInstructor) {
                        signUpInstructorPane.setVisible(true);
                    }
                }
            } catch (RuntimeException exc) {
                System.err.println(exc.getMessage());
            }
        });

        backBtnStudent.setOnAction(actionEvent -> onBackButtonClick(backBtnStudent));
        backBtnInstructor.setOnAction(actionEvent -> onBackButtonClick(backBtnInstructor));

        proceedBtnStudent.setOnAction(actionEvent -> {

        });

        proceedBtnInstructor.setOnAction(actionEvent -> {

        });
    }
}












