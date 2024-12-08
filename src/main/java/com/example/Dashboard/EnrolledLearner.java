package com.example.Dashboard;

import com.example.Account.Learner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnrolledLearner {
    private Learner learner;
    private String enrollmentDate;

    public EnrolledLearner(Learner l, LocalDateTime e) {
        learner = l;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        this.enrollmentDate = e.format(dateFormat);
    }

    public Learner getLearner() {
        return learner;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }
}
