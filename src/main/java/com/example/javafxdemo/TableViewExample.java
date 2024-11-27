package com.example.javafxdemo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableViewExample extends Application {

    // Define a data model class
    public static class Person {
        private String name;
        private int age;
        private String city;

        public Person(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }

        // Getters
        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getCity() {
            return city;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a TableView
        TableView<Person> table = new TableView<>();

        // Create columns
        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(100);

        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageColumn.setMinWidth(50);

        TableColumn<Person, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityColumn.setMinWidth(100);

        // Add columns to the TableView
        table.getColumns().addAll(nameColumn, ageColumn, cityColumn);

        // Add data to the TableView
        table.setItems(getPeople());

        // Add TableView to a layout
        VBox vbox = new VBox(table);

        // Create the scene and display the stage
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX TableView Example");
        primaryStage.show();
    }

    // Sample data for the TableView
    private ObservableList<Person> getPeople() {
        return FXCollections.observableArrayList(
                new Person("Alice", 30, "New York"),
                new Person("Bob", 25, "San Francisco"),
                new Person("Charlie", 35, "Chicago")
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
