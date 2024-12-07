package com.example.Database;

import com.example.Login_SignUp.LoggedInUser;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public int[] getMeetings(int LearnerID){
        try {
            String query = "SELECT MeetingID FROM meetings WHERE LearnerID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(LearnerID));
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Integer> idList = new ArrayList<>();

            while (resultSet.next()) {
                idList.add(resultSet.getInt("MeetingID"));
            }
            return idList.stream().mapToInt(Integer::intValue).toArray();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        MeetingDatabase meetingDB = new MeetingDatabase();
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 10, 14, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 12, 10, 15, 0);
        //System.out.println(meetingDB.scheduleMeeting(3, 3, 2, startTime, endTime));
        int[] meetings = meetingDB.getMeetings(3);
        for(int i : meetings){
            System.out.println(i);
        }
    }
}
