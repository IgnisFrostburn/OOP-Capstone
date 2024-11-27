package com.example.javafxdemo;

import java.sql.*;

public class InstructorDatabase extends DatabaseConnection{
    public InstructorDatabase() {
        super();
    }

    public InstructorDatabase(String lastName, String firstName, String middleName, String university, String email, String password) {
        super(lastName, firstName, middleName, university, email, password);
    }

    @Override
    public void insertData() {
        String url = "jdbc:mysql://localhost:3306/excelone";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");
            String insertQuery = "INSERT INTO instructors (LastName, FirstName, MiddleName, University, Email, Password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, getLastName());
                insertStmt.setString(2, getFirstName());
                insertStmt.setString(3, getMiddleName());
                insertStmt.setString(4, getUniversity());
                insertStmt.setString(5, getEmail());
                insertStmt.setString(6, getPass());
                insertStmt.executeUpdate();
                System.out.println("Instructor data inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkEmail(String email) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/excelone";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT LastName, FirstName, MiddleName, University, Email, Password FROM instructors";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("Email").equals(email)) return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}