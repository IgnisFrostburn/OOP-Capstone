package com.example.Database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDatabase extends UtilityDatabase {

    private String courseTitle;
    private String category1;
    private String category2;
    private String category3;
    private String shortDescription;

    public String getCourseTitle() {
        return courseTitle;
    }
    public String getCategory1() {
        return category1;
    }
    public String getCategory2() {
        return category2;
    }
    public String getCategory3() {
        return category3;
    }
    public String getShortDescription() {
        return shortDescription;
    }

    public CoursesDatabase(String courseTitle, String category1, String category2, String category3, String shortDescription) {
        this.courseTitle = courseTitle;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.shortDescription = shortDescription;
    }

    public void insertData(String id, File courseImage) {
        try {
            System.out.println("Connected to the database!");
            String insertQuery = "INSERT INTO courses (instructor_id, course_title, category_1, category_2, category_3, short_description, course_image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, id);
                insertStmt.setString(2, courseTitle);
                insertStmt.setString(3, category1);
                insertStmt.setString(4, category2);
                insertStmt.setString(5, category3);
                insertStmt.setString(6, shortDescription);
                FileInputStream fileInputStream = new FileInputStream(courseImage);
                insertStmt.setBinaryStream(7, fileInputStream, (int)courseImage.length());
                insertStmt.executeUpdate();
                System.out.println("Course info data inserted successfully!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean maxCoursesReached(String id) throws SQLException {
        int ctr = 0;
        try {
            String selectQuery = "SELECT instructor_id FROM courses";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    if(resultSet.getString("instructor_id").equals(id)) ctr++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(ctr == 3) return true;
        return false;
    }

    //fetches course info
    public static String getCourseTitle(int ctr) {
        String courseTitle = "";
        String instructorName = "";
        int instructor_id;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT course_title, instructor_id FROM courses";
            String selectQuery2 = "SELECT LastName, FirstName, MiddleName FROM instructors";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {
                    for(int i  = 0; i <= ctr; i++) {
                        resultSet.next();
                    }
                    courseTitle = resultSet.getString("course_title");
                    instructor_id = Integer.parseInt(resultSet.getString("instructor_id"));
                }
            try(Statement selectStmt = connection.createStatement();
                ResultSet resultSet2 = selectStmt.executeQuery(selectQuery2)) {
                    for(int i = 1; i <= instructor_id; i++) {
                        resultSet2.next();
                    }
                    instructorName += resultSet2.getString("FirstName") + " ";
                    instructorName += resultSet2.getString("MiddleName") + " ";
                    instructorName += resultSet2.getString("LastName") + " ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseTitle + " - " + instructorName;
    };

    public static int[] numberOfCourses() {
        List<Integer> courses = new ArrayList<Integer>();
        try {
            if(connection == null)throw new SQLException();
            String selectQuery = "SELECT course_ID FROM courses";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    courses.add(Integer.parseInt(resultSet.getString("course_ID")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses.stream().mapToInt(Integer::intValue).toArray();
    }

//    public static CoursesDatabase getCourseData(String id) {
//        CoursesDatabase courseData = null;
//        File imageFile = null;
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String selectQuery = "SELECT course_title, category_1, category_2, category_3, short_description, course_image " +
//                    "FROM courses WHERE instructor_id = ?";
//            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
//                selectStmt.setString(1, id);
//
//                try (ResultSet resultSet = selectStmt.executeQuery()) {
//                    if (resultSet.next()) {
//                        String courseTitle = resultSet.getString("course_title");
//                        String category1 = resultSet.getString("category_1");
//                        String category2 = resultSet.getString("category_2");
//                        String category3 = resultSet.getString("category_3");
//                        String shortDescription = resultSet.getString("short_description");
//
//                        Blob imageBlob = resultSet.getBlob("course_image");
//                        if (imageBlob != null) {
//                            byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
//
//                            imageFile = File.createTempFile("course_image_", ".png");
//                            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//                                fos.write(imageBytes);
//                            }
//                        }
//
//                        courseData = new CoursesDatabase(courseTitle, category1, category2, category3, shortDescription);
//                    }
//                }
//            }
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//
//        if (imageFile != null) {
//            System.out.println("Image saved to: " + imageFile.getAbsolutePath());
//        }
//        return courseData;
//    }

}






















