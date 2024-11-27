package com.example.Login_SignUp;

import com.example.MainUserInterface.MainStudentInterface;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;


public class SignUpPageController {
    @FXML
    private StackPane signUpPageStackPane;
    @FXML
    private AnchorPane signUpPageOnePane;
    @FXML
    private AnchorPane verifyEmailPane;
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
    private Label emailAddressWarning;
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
    @FXML
    private TextField otptextfield;
    @FXML
    private Button submitbtn;
    @FXML
    private Button generateOTP;
    @FXML
    private TextField studentFirstNameTF;
    @FXML
    private TextField studentMiddleNameTF;
    @FXML
    private TextField studentLastNameTF;
    @FXML
    private TextField studentUniversityTF;
    @FXML
    private TextField instructorFirstNameTF;
    @FXML
    private TextField instructorMiddleNameTF;
    @FXML
    private TextField instructorLastNameTF;
    @FXML
    private TextField instructorUniversityTF;
    @FXML
    private Button backLogin;

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

    protected int isBlankTF(TextField textField) {
        if(textField.getText().isBlank()) {
            setBorderColor(textField, "red");
            return 1;
        } else setBorderColor(textField, "green");
        return 0;
    }

    public String getOTP(){
        return otptextfield.getText();
    }

    public boolean containsLetters(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public void delayGenerate(){
        int cooldown = 15;
        int[] timeRemaining = {cooldown};
        PauseTransition pause = new PauseTransition(Duration.seconds(cooldown));
        generateOTP.setDisable(true);
        Timeline countdown = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            generateOTP.setText(timeRemaining[0] + "s");
            timeRemaining[0]--;
        }));
        countdown.setCycleCount(cooldown);
        countdown.setOnFinished(actionEvent -> {
            generateOTP.setDisable(false);
            generateOTP.setText("Generate OTP");
        });
        pause.play();
        countdown.play();
    }

    public void showInvalid() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Login_SignUp/popUp.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Invalid Input");
            popupStage.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void generateOTP(AtomicReference<String> OTP, AtomicReference<String> emailAdd) {
        generateOTP.setOnAction(actionEvent -> {
            delayGenerate();
            try{
                Email mail = new Email();
                mail.setupServer();
                mail.draftEmail(getEmail());
                mail.sendEmail();
                OTP.set(mail.getOTP());
                System.out.println("OTP sent successfully!");
                submitbtn.setDisable(false);
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("Error in generating OTP");
            }
        });
    }

    public void submitButtonClicked(AtomicReference<String> OTP) {
        submitbtn.setOnAction(actionEvent -> {
            try{
                String userOTP = getOTP();
                if(userOTP.matches(String.valueOf(OTP))){
                    System.out.println("Correct");
                    if(isLearner) {
                        LearnerDatabase learnerDatabase = new LearnerDatabase(studentLastNameTF.getText(), studentFirstNameTF.getText(), studentMiddleNameTF.getText(), studentUniversityTF.getText(), getEmail(), getPassword());
                        learnerDatabase.insertData();
                        Stage mainStudentUIStage = new Stage();
                        MainStudentInterface mainStudentInterface = new MainStudentInterface();
                        try {
                            mainStudentInterface.start(mainStudentUIStage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage = (Stage) signUpPageStackPane.getScene().getWindow();
                        stage.close();
                    } else if(isInstructor) {
                        InstructorDatabase instructorDatabase = new InstructorDatabase(instructorLastNameTF.getText(), instructorFirstNameTF.getText(), instructorMiddleNameTF.getText(), instructorUniversityTF.getText(), getEmail(), getPassword());
                        instructorDatabase.insertData();
                    }
                }else{
                    throw new RuntimeException("Invalid OTP");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                showInvalid();
            }
        });
    }

    protected boolean emailIsAvailable(String email) throws SQLException {
        LearnerDatabase learnerDatabase = new LearnerDatabase();
        InstructorDatabase instructorDatabase = new InstructorDatabase();
        return (!learnerDatabase.checkEmail(email) && !instructorDatabase.checkEmail(email));
    }

    protected void textMask(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText("•".repeat(newValue.length()));
            textField.positionCaret(newValue.length());
        });
    }

    @FXML
    public void initialize() {
        signUpPageOnePane.setVisible(true);
        signUpStudentPane.setVisible(false);
        signUpInstructorPane.setVisible(false);
        verifyEmailPane.setVisible(false);
        onMouseEntered(learnerBtn);
        onMouseEntered(instructorBtn);
        onMouseExit(learnerBtn);
        onMouseExit(instructorBtn);
        submitbtn.setDisable(true);
        AtomicReference<String> OTP = new AtomicReference<>();
        AtomicReference<String> emailAdd = new AtomicReference<>();
        textMask(passwordTF);
        textMask(confirmPasswordTF);

        learnerBtn.setOnAction(e -> {
            isLearner = true;
            isInstructor = false;
            learnerBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;");
            instructorBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        instructorBtn.setOnAction(e -> {
            isInstructor = true;
            isLearner = false;
            instructorBtn.setStyle("-fx-background-color: #77FFDF; -fx-text-fill: white;");
            learnerBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        textFieldChecker(emailTF);
        textFieldChecker(passwordTF);
        textFieldChecker(confirmPasswordTF);

        proceedBtn.setOnMouseEntered(e -> proceedBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;"));
        proceedBtn.setOnMouseExited(e -> proceedBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;"));

        proceedBtn.setOnAction(actionEvent -> {
            try {
                if(getEmail().isBlank() || !checkEmailValidity(getEmail())) {
                    setBorderColor(emailTF, "red");
                    ctr++;
                } else if(!emailIsAvailable(getEmail())) {
                    emailAddressWarning.setText("Email is already used");
                    setBorderColor(emailTF, "red");
                    ctr++;
                } else {
                    emailAddressWarning.setText("");
                    setBorderColor(emailTF, "green");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            ctr += isBlankTF(passwordTF);
            if(getConfirmPassword().isBlank() || !getConfirmPassword().equals(getPassword()))  {
                setBorderColor(confirmPasswordTF, "red");
                ctr++;
            } else setBorderColor(confirmPasswordTF, "green");

            try {
                System.out.println(ctr);
                if (ctr > 0) {
                    ctr = 0;
                    throw new RuntimeException("Check Textfields");
                }
                if (!isLearner && !isInstructor) {
                    accountTypeWarningLabel.setText("Please choose an account type");
                } else {
                    emailAdd.set(getEmail());
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

        textFieldChecker(studentFirstNameTF);
        textFieldChecker(studentLastNameTF);
        textFieldChecker(studentMiddleNameTF);
        textFieldChecker(studentUniversityTF);

        backBtnStudent.setOnAction(actionEvent -> onBackButtonClick(backBtnStudent));
        backBtnInstructor.setOnAction(actionEvent -> onBackButtonClick(backBtnInstructor));

        proceedBtnStudent.setOnAction(actionEvent -> {
            ctr = 0;
            try {
                ctr += isBlankTF(studentFirstNameTF);
                ctr += isBlankTF(studentMiddleNameTF);
                ctr += isBlankTF(studentLastNameTF);
                ctr += isBlankTF(studentUniversityTF);
                if (ctr > 0) {
                    ctr = 0;
                    throw new RuntimeException("Check Textfields");
                } else {
                    verifyEmailPane.setVisible(true);
                    signUpStudentPane.setVisible(false);
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        });

        proceedBtnInstructor.setOnAction(actionEvent -> {
            ctr = 0;
            try {
                ctr += isBlankTF(instructorLastNameTF);
                ctr += isBlankTF(instructorFirstNameTF);
                ctr += isBlankTF(instructorMiddleNameTF);
                ctr += isBlankTF(instructorUniversityTF);
                if (ctr > 0) {
                    ctr = 0;
                    throw new RuntimeException("Check Textfields");
                } else {
                    verifyEmailPane.setVisible(true);
                    signUpInstructorPane.setVisible(false);
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        });

        backLogin.setOnAction(actionEvent -> {
            Stage loginStage = new Stage();
            LoginPageApplication loginPageApplication = new LoginPageApplication();
            try {
                loginPageApplication.start(loginStage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) signUpPageStackPane.getScene().getWindow();
            stage.close();
        });


        generateOTP(OTP, emailAdd);
        submitButtonClicked(OTP);
    }
}












