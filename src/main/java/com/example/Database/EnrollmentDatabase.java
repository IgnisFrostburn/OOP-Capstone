package com.example.Database;

import com.example.Account.Learner;
import com.example.Dashboard.EnrolledLearner;
import com.example.Login_SignUp.LoggedInUser;

import java.sql.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

public  class EnrollmentDatabase extends UtilityDatabase{

    public EnrollmentDatabase(){

        super();

    }

    public int[] getCourses(int LearnerID) {
        List<Integer> courses = new ArrayList<Integer>();
        try {
            if(connection == null)throw new SQLException();
            String query = "SELECT CourseID FROM enrollment WHERE LearnerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(LearnerID));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(Integer.parseInt(resultSet.getString("CourseID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses.stream().mapToInt(Integer::intValue).toArray();
    }


    public String getCourseCTR(int studentID){
        try{
            String query = "SELECT COUNT(*) AS courseCTR FROM enrollment WHERE LearnerID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(studentID));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return String.valueOf(resultSet.getInt("courseCTR"));
            }
        } catch (SQLException e) {
            System.out.println("smthing wrong with sql on method");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean checkIfEnrolled(int userID, int courseID){

        String query = "SELECT COUNT(*) AS count FROM enrollment WHERE LearnerID = ? AND CourseID = ?;";

        try{

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, String.valueOf(userID));

            statement.setString(2, String.valueOf(courseID));

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                return resultSet.getInt("count") > 0;

            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

        return false;

    }

    public boolean enrollLearner(int learnerID, int courseID){

        String query = "INSERT INTO Enrollment (LearnerID, CourseID) VALUES (?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, learnerID);

            preparedStatement.setInt(2, courseID);

            int change = preparedStatement.executeUpdate();

            System.out.println(change);

            return change > 0;

        } catch (SQLException e) {

            System.out.println("SQL Error sa enroll: " + e.getMessage());

        }

        return false;

    }

    public List<EnrolledLearner> getEnrolledLearners(int courseId) {
        List<EnrolledLearner> enrolledLearners = new ArrayList<>();
        String query = "SELECT learners.FirstName, learners.LastName, learners.Email, enrollment.EnrollmentDate " +
                "FROM enrollment " +
                "JOIN learners ON enrollment.LearnerID = learners.LearnerID " +
                "WHERE enrollment.CourseID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email = resultSet.getString("Email");
                LocalDateTime enrollmentDate = resultSet.getTimestamp("EnrollmentDate").toLocalDateTime();
                Learner learner = new Learner(firstName, lastName, email);

                enrolledLearners.add(new EnrolledLearner(learner, enrollmentDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrolledLearners;
    }

    public static void main(String[] args) {
        EnrollmentDatabase enrollmentDatabase = new EnrollmentDatabase();
        enrollmentDatabase.getEnrolledLearners(1);
    }

}

