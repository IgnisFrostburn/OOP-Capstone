module com.example.Login_SignUp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.mail;
    requires transitive mysql.connector.j;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.sql;

    opens com.example.Login_SignUp to javafx.fxml;
    exports com.example.Login_SignUp;
    exports com.example.Database;
    opens com.example.Database to javafx.fxml;
    exports com.example.Dashboard;
    opens com.example.Dashboard to javafx.fxml;
    opens com.example.Course_content to javafx.fxml;
    exports com.example.Course_content;
    exports com.example.Account;
}