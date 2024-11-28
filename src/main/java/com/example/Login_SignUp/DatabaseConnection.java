package com.example.Login_SignUp;
import java.sql.*;

public abstract class DatabaseConnection {
    private String lastName;
    private String firstName;
    private String middleName;
    private String university;
    private String email;
    private String pass;

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getUniversity() {
        return university;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public DatabaseConnection() {
        this.lastName = null;
        this.firstName = null;
        this.middleName = null;
        this.university = null;
        this.email = null;
        this.pass = null;
    }

    public DatabaseConnection(String lastName, String firstName, String middleName,String university, String email, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.university = university;
        this.email = email;
        this.pass = password;
    }

    public abstract void insertData();

//    private void selectData(Connection connection) throws SQLException {
//        String selectQuery = "SELECT LastName, FirstName, MiddleName, University, Email, Password FROM learners";
//        try (Statement selectStmt = connection.createStatement();
//             ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {
//
//            while (resultSet.next()) {
//                System.out.println("LastName: " + resultSet.getString("LastName"));
//                System.out.println("FirstName: " + resultSet.getString("FirstName"));
//                System.out.println("University: " + resultSet.getString("University"));
//                System.out.println("Email: " + resultSet.getString("Email"));
//                System.out.println("Password: " + resultSet.getString("Password"));
//                System.out.println("-------------");
//            }
//        }
//    }

    public abstract boolean checkEmail(String email) throws SQLException;
    public abstract boolean checkPassword(String userPassword) throws SQLException;
}
