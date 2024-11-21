module com.example.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.mail;

    opens com.example.javafxdemo to javafx.fxml;
    exports com.example.javafxdemo;
}