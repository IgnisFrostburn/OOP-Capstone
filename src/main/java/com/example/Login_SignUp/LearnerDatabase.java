package com.example.Login_SignUp;

import java.sql.*;

public class LearnerDatabase extends DatabaseConnection {
    String url = "jdbc:mysql://192.168.1.8/excelone";
    String username = "excelOneAdmin";
    String password = "secure123";

    public LearnerDatabase() {
        super();
    }

    public LearnerDatabase(String lastName, String firstName, String middleName, String university, String email, String password) {
        super(lastName, firstName, middleName, university, email, password);
    }

    @Override
    public void insertData() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");
            String insertQuery = "INSERT INTO learners (LastName, FirstName, MiddleName, University, Email, Password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, getLastName());
                insertStmt.setString(2, getFirstName());
                insertStmt.setString(3, getMiddleName());
                insertStmt.setString(4, getUniversity());
                insertStmt.setString(5, getEmail());
                insertStmt.setString(6, getPass());
                insertStmt.executeUpdate();
                System.out.println("Student data inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//TIWASA NI
    public void getUserData(){
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String query = "SELECT * FROM learners WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "bebedorkarolvincent@gmail.com");
            ResultSet resultSet = statement.executeQuery();

//            while(resultSet.next()){
//                String name = resultSet.getString(name);
//            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean checkEmail(String email) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT LastName, FirstName, MiddleName, University, Email, Password FROM learners";
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
    public boolean checkPassword(String userPassword) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT LastName, FirstName, MiddleName, University, Email, Password FROM learners";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("Password").equals(userPassword)) return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
