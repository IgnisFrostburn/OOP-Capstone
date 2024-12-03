package com.example.Database;

import com.example.Login_SignUp.LoggedInUser;

import java.sql.*;

public class LearnerDatabase extends UserDatabase {
    public LearnerDatabase() {
        super();
    }


    @Override
    public void insertData(String lastName, String firstName, String middleName,String university, String email, String password) {
        try{
            if(connection != null)
            System.out.println("Connected to the database!");
            String insertQuery = "INSERT INTO learners (LastName, FirstName, MiddleName, University, Email, Password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, lastName);
                insertStmt.setString(2, firstName);
                insertStmt.setString(3, middleName);
                insertStmt.setString(4, university);
                insertStmt.setString(5, email);
                insertStmt.setString(6, password);
                insertStmt.executeUpdate();
                System.out.println("Student data inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//TIWASA NI
public void getUserData(String email) {
    try{
        String query = "SELECT * FROM learners WHERE Email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            LoggedInUser loggedInUser = LoggedInUser.getInstance();
            loggedInUser.setEmail(resultSet.getString("Email"));
            loggedInUser.setFirstName(resultSet.getString("FirstName"));
            loggedInUser.setLastName(resultSet.getString("LastName"));
            loggedInUser.setUniversity(resultSet.getString("University"));
            loggedInUser.setID(resultSet.getInt("ID"));
            loggedInUser.setRole("Learner");
        } else {
            throw new RuntimeException("No user found");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    @Override
    public boolean checkEmail(String email) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT Email FROM learners";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("Email").equals(email)) return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkPassword(String userPassword, String email) throws SQLException {
        String selectQuery = "SELECT Password FROM learners WHERE Email = ?";

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


}
