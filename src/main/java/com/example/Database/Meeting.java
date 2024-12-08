package com.example.Database;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Meeting {
    private Timestamp startTime;
    private Timestamp endTime;
    private String instructorName;
    private String courseTitle;

    private String date;
    private String startTimeString;
    private String endTimeString;

    public Meeting(Timestamp startTime, Timestamp endTime, String courseTitle, String instructorName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseTitle = courseTitle;
        this.instructorName = instructorName;

        // Formatter for the date (e.g., "January 24, 2024")
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

        // Formatter for the time in 12-hour format (e.g., "11:30 AM")
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        // Format the date and times
        this.date = dateFormat.format(startTime);
        this.startTimeString = timeFormat.format(startTime);
        this.endTimeString = timeFormat.format(endTime);
    }

    public String getDate() {
        return date;
    }
    public LocalDateTime getTimeStart(){
        return startTime.toLocalDateTime();
    }
    public LocalDateTime getTimeEnd(){
        return endTime.toLocalDateTime();
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    @Override
    public String toString() {
        return courseTitle + " - " + instructorName + " : " + startTimeString + " - " + endTimeString;
    }
}
