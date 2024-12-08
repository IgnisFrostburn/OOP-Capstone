package com.example.Database;

import com.example.Account.Account;
import com.example.Account.Instructor;
import com.example.Account.Learner;
import com.example.Login_SignUp.LoggedInUser;

import java.sql.*;

public class InstructorDatabase extends UserDatabase    {
    public InstructorDatabase() {
        super();
    }

    @Override
    public void insertData(String lastName, String firstName, String middleName,String university, String email, String password) {
        try{
            if(connection != null) System.out.println("Connected to the database!");
            String insertQuery = "INSERT INTO instructors (LastName, FirstName, MiddleName, University, Email, Password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, lastName);
                insertStmt.setString(2, firstName);
                insertStmt.setString(3, middleName);
                insertStmt.setString(4, university);
                insertStmt.setString(5, email);
                insertStmt.setString(6, password);
                insertStmt.executeUpdate();
                System.out.println("Instructor data inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkEmail(String email) throws SQLException {
        String selectQuery = "SELECT Email FROM instructors WHERE Email = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectQuery)) {

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
    public void getUserData(String email) {
        try {
            if(connection == null)throw new SQLException("Error with getting User Data");
            String query = "SELECT * FROM instructors WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LoggedInUser loggedInUser = LoggedInUser.getInstance();
                String mail = resultSet.getString("Email");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String university = resultSet.getString("University");
                int ID = resultSet.getInt("ID");
                Account account = new Instructor(ID, firstName, lastName, mail, university);
                loggedInUser.setLoggedInAccount(account);
            } else {
                throw new RuntimeException("No user found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean checkPassword(String userPassword, String email) throws SQLException {
        String selectQuery = "SELECT Password FROM instructors WHERE Email = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectQuery)) {

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

    public static String getInstructorID(String email) throws SQLException {
        String selectQuery = "SELECT ID FROM instructors WHERE Email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return String.valueOf(resultSet.getInt("ID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "1";
    }

    public static String getUniversity(String id) throws SQLException {
        try {
            String selectQuery = "SELECT ID, University FROM instructors";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("ID").equals(id)) return resultSet.getString("University");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return "1";
    }
}
