package com.example.Database;

import com.example.Login_SignUp.LoggedInUser;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingDatabase extends UtilityDatabase{
    public MeetingDatabase(){
        super();
    }

    public boolean scheduleMeeting(int learnerID, int instructorID, int courseID, LocalDateTime startTime, LocalDateTime endTime){
        try{
            if(connection == null)throw new RuntimeException();
            String query = "INSERT INTO meetings (LearnerID, InstructorID, CourseID, StartTime, EndTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, learnerID);
            ps.setInt(2, instructorID);
            ps.setInt(3, courseID);
            ps.setTimestamp(4, Timestamp.valueOf(startTime));
            ps.setTimestamp(5, Timestamp.valueOf(endTime));

            int worked = ps.executeUpdate();
            return worked > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteExpiredMeetings(){
        String query = "DELETE from meetings WHERE EndTime < Now()";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String countMeetings(int ID) {
        String query = "SELECT COUNT(*) FROM meetings WHERE LearnerID = ? AND StartTime > Now()";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return String.valueOf(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<Meeting> getUpcomingMeetings(int learnerId) {
        System.out.println("learner id " + learnerId);
        List<Meeting> meetings = new ArrayList<>();
        String query = "SELECT c.course_title, CONCAT(i.FirstName, ' ', i.LastName) AS instructor_name, " +
                "m.StartTime, m.EndTime " +
                "FROM Meetings m " +
                "JOIN Courses c ON m.CourseID = c.course_ID " +
                "JOIN Instructors i ON m.InstructorID = i.ID " +
                "WHERE m.LearnerID = ? " +
                "ORDER BY m.StartTime ASC";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, learnerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                meetings.add(new Meeting(
                        resultSet.getTimestamp("StartTime"),
                        resultSet.getTimestamp("EndTime"),
                        resultSet.getString("course_title"),
                        resultSet.getString("instructor_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(meetings);
        return meetings;
    }
    public List<Meeting> getUpcomingMeetingsforInstructor(int InstructorID) {
        List<Meeting> meetings = new ArrayList<>();
        String query = "SELECT c.course_title, m.StartTime, m.EndTime, " +
                "CONCAT(l.FirstName, ' ', l.LastName) AS student_name " +  // Student name concatenated
                "FROM meetings m " +
                "JOIN courses c ON m.CourseID = c.course_ID " +
                "JOIN enrollment e ON e.CourseID = c.course_ID " +   // Assuming Enrollments table connects students to courses
                "JOIN learners l ON e.LearnerID = l.ID " +  // Get learner's full name
                "WHERE m.InstructorID = ? " +   // Filter by InstructorID
                "ORDER BY m.StartTime ASC";   // Order by meeting time

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, InstructorID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                meetings.add(new Meeting(
                        resultSet.getTimestamp("StartTime"),
                        resultSet.getTimestamp("EndTime"),
                        resultSet.getString("course_title"),
                        resultSet.getString("student_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meetings;
    }

//    public static void main(String[] args) {
//        MeetingDatabase meetingDatabase = new MeetingDatabase();
//        meetingDatabase.scheduleMeeting(3, 1, 3, LocalDateTime.now(), LocalDateTime.now());
//        meetingDatabase.scheduleMeeting(3, 1, 4, LocalDateTime.now(), LocalDateTime.now());
//        meetingDatabase.scheduleMeeting(3, 1, 4, LocalDateTime.now(), LocalDateTime.now());
//        System.out.println("succ");
//    }






}
