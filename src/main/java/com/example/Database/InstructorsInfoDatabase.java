package com.example.Database;

import java.io.*;
import java.sql.*;

public class InstructorsInfoDatabase extends UtilityDatabase {
    private String teachingExperience_1;
    private String teachingExperience_2;
    private String teachingExperience_3;
    private String teachingExpertise_1;
    private String teachingExpertise_2;
    private String teachingExpertise_3;
    private String linkedInURL;

    public InstructorsInfoDatabase() {

    }

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
        super();
        this.teachingExperience_1 = teachingExperience_1;
        this.teachingExperience_2 = teachingExperience_2;
        this.teachingExperience_3 = teachingExperience_3;
        this.teachingExpertise_1 = teachingExpertise_1;
        this.teachingExpertise_2 = teachingExpertise_2;
        this.teachingExpertise_3 = teachingExpertise_3;
        this.linkedInURL = linkedInURL;
    }

    public void insertData(String id, File pfp) {
        try {
            if(connection == null)throw new SQLException();
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

    //checks if data exists
    public static boolean dataExists(String id) throws SQLException {
        try {
            if(connection == null)throw new SQLException();
            String selectQuery = "SELECT instructor_id FROM instructor_info";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("instructor_id").equals(id)) return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("data exist exception");
            e.printStackTrace();
        }
        return false;
    }

    //finds the instructors credentials
    //if it exists, it edits that row, if it doesn't, it creates a new one
    public void editInfo(String id, File pfp) {
        try {
            if(connection == null)throw new SQLException();
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
            System.out.println("edit data exception");
            e.printStackTrace();
        }
    }

    public static InstructorsInfoDatabase instructorDetails(String id) {
        InstructorsInfoDatabase instructorsInfoDatabase = null;
        String query = "SELECT teaching_experience1, teaching_experience2, teaching_experience3, teaching_expertise1, teaching_expertise2, teaching_expertise3, linkedIn_url FROM instructor_info WHERE instructor_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if(connection == null)throw new SQLException();
            stmt.setString(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String teachingExperience1 = resultSet.getString("teaching_experience1");
                    String teachingExperience2 = resultSet.getString("teaching_experience2");
                    String teachingExperience3 = resultSet.getString("teaching_experience3");
                    String teachingExpertise1 = resultSet.getString("teaching_expertise1");
                    String teachingExpertise2 = resultSet.getString("teaching_expertise2");
                    String teachingExpertise3 = resultSet.getString("teaching_expertise3");
                    String linkedInURL = resultSet.getString("linkedIn_url");

                    instructorsInfoDatabase = new InstructorsInfoDatabase(
                            teachingExperience1,
                            teachingExperience2,
                            teachingExperience3,
                            teachingExpertise1,
                            teachingExpertise2,
                            teachingExpertise3,
                            linkedInURL);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructorsInfoDatabase;
    }

    public static File getProfileImage(String id) throws SQLException {
        System.out.println("ID passed is " + id);
        File pfp = null;
        String query = "SELECT teacher_pfp FROM instructor_info WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (connection == null) throw new SQLException();
            stmt.setString(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    try (InputStream inputStream = resultSet.getBinaryStream("teacher_pfp")) {
                        if (inputStream != null) {
                            pfp = File.createTempFile("profile_image_" + id, ".png");
                            try (OutputStream outputStream = new FileOutputStream(pfp)) {
                                byte[] buffer = new byte[1024];
                                while (inputStream.read(buffer) != -1) {
                                    outputStream.write(buffer);
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pfp;
    }
}






