module com.example.Login_SignUp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.mail;
    requires java.sql;

    opens com.example.Login_SignUp to javafx.fxml;
    exports com.example.Login_SignUp;
    exports com.example.Database;
    opens com.example.Database to javafx.fxml;
    exports com.example.Dashboard;
    opens com.example.Dashboard to javafx.fxml;
//    exports com.example.MainUserInterface;
}