package com.example.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class InstructorsInfoDatabase {
    static String url = "jdbc:mysql://192.168.1.8:3306/excelone";
    static String username = "excelOneAdmin";
    static String password = "secure123";
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

    public InstructorsInfoDatabase(String teachingExperience_1, String teachingExperience_2, String teachingExperience_3, String teachingExpertise_1, String teachingExpertise_2, String teachingExpertise_3, String linkedInURL) {
        this.teachingExperience_1 = teachingExperience_1;
        this.teachingExperience_2 = teachingExperience_2;
        this.teachingExperience_3 = teachingExperience_3;
        this.teachingExpertise_1 = teachingExpertise_1;
        this.teachingExpertise_2 = teachingExpertise_2;
        this.teachingExpertise_3 = teachingExpertise_3;
        this.linkedInURL = linkedInURL;
    }

    public void insertData(String id, File pfp) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");
            String insertQuery = "INSERT INTO instructor_info (instructor_ID, teaching_experience1, teaching_experience2, teaching_experience3, teaching_expertise1, teaching_expertise2, teaching_expertise3, linkedIn_url, teacher_pfp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                //to do: take the email parameter and insert to the instructor ID
                insertStmt.setString(1, id);
                insertStmt.setString(2, getTeachingExperience_1());
                insertStmt.setString(3, getTeachingExperience_2());
                insertStmt.setString(4, getTeachingExperience_3());
                insertStmt.setString(5, getTeachingExpertise_1());
                insertStmt.setString(6, getTeachingExpertise_2());
                insertStmt.setString(7, getTeachingExpertise_3());
                insertStmt.setString(8, getLinkedInURL());
                FileInputStream fileInputStream = new FileInputStream(pfp);
                insertStmt.setBinaryStream(9, fileInputStream, (int)pfp.length());
                insertStmt.executeUpdate();
                System.out.println("Instructor info data inserted successfully!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int numberOfCourses() throws SQLException {
        int ctr = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT ID FROM instructor_info";
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

    //checks if data exists
    public static boolean dataExists(String id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT instructor_id FROM instructor_info";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("instructor_id").equals(id)) return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //finds the instructors credentials
    //if it exists, it edits that row, if it doesn't, it creates a new one
    public void editInfo(String id, File pfp) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if(dataExists(id)) {
                String updateQuery = "UPDATE instructor_info SET teaching_experience1 = ?, teaching_experience2 = ?, teaching_experience3 = ?, teaching_expertise1 = ?, teaching_expertise2 = ?, teaching_expertise3 = ?, linkedIn_url = ?, teacher_pfp = ? WHERE instructor_id = ?";
                try(PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, getTeachingExperience_1());
                    updateStmt.setString(2, getTeachingExperience_2());
                    updateStmt.setString(3, getTeachingExperience_3());
                    updateStmt.setString(4, getTeachingExpertise_1());
                    updateStmt.setString(5, getTeachingExpertise_2());
                    updateStmt.setString(6, getTeachingExpertise_3());
                    updateStmt.setString(7, getLinkedInURL());
                    FileInputStream fileInputStream = new FileInputStream(pfp);
                    updateStmt.setBinaryStream(8, fileInputStream, (int)pfp.length());
                    updateStmt.setString(9, id);
                    updateStmt.executeUpdate();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else insertData(id, pfp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






