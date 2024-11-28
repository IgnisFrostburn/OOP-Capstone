package com.example.Database;

import com.example.Login_SignUp.LoggedInUser;

import java.sql.*;

public class InstructorDatabase extends DatabaseConnection{
    String url = "jdbc:mysql://192.168.1.8:3306/excelone";
    String username = "excelOneAdmin";
    String password = "secure123";
    public InstructorDatabase() {
        super();
    }

    public InstructorDatabase(String lastName, String firstName, String middleName, String university, String email, String password) {
        super(lastName, firstName, middleName, university, email, password);
    }

    @Override
    public void insertData() {

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
    public boolean checkEmail(String email) throws SQLException {
        String selectQuery = "SELECT Email FROM instructors WHERE Email = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(selectQuery)) {

            ps.setString(1, email);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override

    public LoggedInUser getUserData(String email) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM instructors WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LoggedInUser loggedInUser = LoggedInUser.getInstance();
                loggedInUser.setEmail(resultSet.getString("Email"));
                loggedInUser.setFirstName(resultSet.getString("FirstName"));
                loggedInUser.setLastName(resultSet.getString("LastName"));
                loggedInUser.setUniversity(resultSet.getString("University"));
                loggedInUser.setRole("Learner");
                return loggedInUser; // Return the singleton instance
            } else {
                throw new RuntimeException("No user found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public boolean checkPassword(String userPassword, String email) throws SQLException {
        String selectQuery = "SELECT Password FROM instructors WHERE Email = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(selectQuery)) {

            ps.setString(1, email);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("Password");
                    if (storedPassword.equals(userPassword)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public int numberOfInstructors() throws SQLException {
        int ctr = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT LastName FROM instructors";
            try (Statement selectStmt = connection.createStatement();
                    ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                    while (resultSet.next()) {
                        ctr++;
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ctr;
    }

    //gets the id of the instructor using their last name
    public int getInstructorID(String instructorName) throws SQLException {
        int ctr = 1;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT LastName FROM instructors";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("LastName").equals(instructorName)) return ctr;
                    ctr++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ctr;
    }
}
