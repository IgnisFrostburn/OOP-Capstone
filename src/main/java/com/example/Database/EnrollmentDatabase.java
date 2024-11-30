package com.example.Database;

import java.sql.*;

public  class EnrollmentDatabase extends UtilityDatabase{

    public EnrollmentDatabase(){
        super();
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

}