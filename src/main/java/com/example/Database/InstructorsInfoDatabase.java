package com.example.Database;

import java.sql.*;

public class InstructorsInfoDatabase {
    String url = "jdbc:mysql://192.168.1.8:3306/excelone";
    String username = "excelOneAdmin";
    String password = "secure123";
    private String teachingExperience_1;
    private String teachingExperience_2;
    private String teachingExperience_3;
    private String teachingExpertise_1;
    private String teachingExpertise_2;
    private String teachingExpertise_3;
    private String linkedInURL;

    public String getTeachingExperience_1() {
        return teachingExperience_1;
    }
    public String getTeachingExperience_2() {
        return teachingExperience_2;
    }
    public String getTeachingExperience_3() {
        return teachingExperience_3;
    }
    public String getTeachingExpertise_1() {
        return teachingExpertise_1;
    }
    public String getTeachingExpertise_2() {
        return teachingExpertise_2;
    }
    public String getTeachingExpertise_3() {
        return teachingExpertise_3;
    }
    public String getLinkedInURL() {
        return linkedInURL;
    }

    public void insertData() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");
            String insertQuery = "INSERT INTO instructor_info (teaching_experience1, teaching_experience2, teaching_experience3, teaching_expertise1, teaching_expertise2, teaching_expertise3, linkedIn_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(2, getTeachingExperience_1());
                insertStmt.setString(3, getTeachingExperience_2());
                insertStmt.setString(4, getTeachingExperience_3());
                insertStmt.setString(5, getTeachingExpertise_1());
                insertStmt.setString(6, getTeachingExpertise_2());
                insertStmt.setString(7, getTeachingExpertise_3());
                insertStmt.setString(8, getLinkedInURL());
                insertStmt.executeUpdate();
                System.out.println("Instructor info data inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int numberOfCourses() throws SQLException {
        int ctr = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT teaching_experience1, teaching_experience2, teaching_experience3, teaching_expertise1, teaching_expertise2, teaching_expertise3, linkedIn_url FROM instructor_info";
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

}
