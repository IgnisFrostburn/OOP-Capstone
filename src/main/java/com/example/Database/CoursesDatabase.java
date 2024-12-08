package com.example.Database;

import javafx.scene.image.Image;

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

    public CoursesDatabase() {
        super();
    }

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

    //fetches course title
    public static String getCourseTitle(String id) {
        try {
            if(connection == null) throw new SQLException("Exception in getting course title");
            String selectQuery = "SELECT course_title FROM courses WHERE course_ID = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, id);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("course_title");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("null");
        return null;
    };

    public static String getInstructorName(String id) {
        try {
            if (connection == null) throw new SQLException("Exception in getting instructor name");
            String query = """
            SELECT i.LastName, i.FirstName, i.MiddleName
            FROM instructors i
            INNER JOIN courses c ON i.ID = c.instructor_id
            WHERE c.course_ID = ?
        """;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, id);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("FirstName") + " " + resultSet.getString("MiddleName") + " " + resultSet.getString("LastName");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Not found";
    }

    public String getCourseCTR(int InstructorID){
        try{
            String query = "SELECT COUNT(*) AS courseCTR FROM courses WHERE instructor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, InstructorID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                System.out.println(resultSet.getInt("courseCTR") + " " + InstructorID);
                return String.valueOf(resultSet.getInt("courseCTR"));
            }
        } catch (SQLException e) {
            System.out.println("smthing wrong with sql on method");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static int[] numberOfCourses(String filter) {
        List<Integer> courses = new ArrayList<Integer>();
        try {
            if(connection == null)throw new SQLException();
            String selectQuery = "SELECT course_ID, category_1, category_2, category_3 FROM courses ORDER BY course_ID ASC";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectQuery)) {

                System.out.println("filter passed is " + filter);
                if(filter != null) {
                    while (resultSet.next()) {
                        String category1 = resultSet.getString("category_1");
                        String category2 = resultSet.getString("category_2");
                        String category3 = resultSet.getString("category_3");
                        if(filter.equals(category1) || filter.equals(category2) || filter.equals(category3))
                            courses.add(Integer.parseInt(resultSet.getString("course_ID")));
                    }
                } else {
                    while (resultSet.next()) {
                        courses.add(Integer.parseInt(resultSet.getString("course_ID")));
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses.stream().mapToInt(Integer::intValue).toArray();
    }

    public static CoursesDatabase getCourseData(String id) {
        CoursesDatabase courseData = null;
        try {
            if(connection == null) throw new SQLException("Exception in getting course data");
            String selectQuery = "SELECT course_title, category_1, category_2, category_3, short_description " +
                    "FROM courses WHERE course_ID = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, id);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next()) {
                        String courseTitle = resultSet.getString("course_title");
                        String category1 = resultSet.getString("category_1");
                        String category2 = resultSet.getString("category_2");
                        String category3 = resultSet.getString("category_3");
                        String shortDescription = resultSet.getString("short_description");

                        courseData = new CoursesDatabase(courseTitle, category1, category2, category3, shortDescription);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseData;
    }

    public static Image getImage(String id) {
        try {
            if(connection == null) throw new SQLException("Exception in getting course image");
            String selectQuery = "SELECT course_image FROM courses WHERE course_ID = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, id);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next()) {
                        InputStream inputStream = resultSet.getBinaryStream("course_image");
                        return new Image(inputStream);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCID(String title) {
        try {
            if(connection == null) throw new SQLException("Exception in getting course image");
            String selectQuery = "SELECT course_ID FROM courses WHERE course_title = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, title);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("course_ID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getCourseInstructorID(String id) {
        try {
            if(connection == null) throw new SQLException("Exception in getting course image");
            String selectQuery = "SELECT instructor_id FROM courses WHERE course_ID = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, id);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("instructor_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public int[] getCoursesInstructor(int InstructorID) {
        List<Integer> courses = new ArrayList<Integer>();
        try {
            if(connection == null)throw new SQLException();
            String query = "SELECT course_ID FROM courses WHERE instructor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, InstructorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(Integer.parseInt(resultSet.getString("course_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses.stream().mapToInt(Integer::intValue).toArray();
    }

//    public int
}






















