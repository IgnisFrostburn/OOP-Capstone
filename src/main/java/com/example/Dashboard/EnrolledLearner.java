package com.example.Dashboard;

import com.example.Account.Learner;

import java.time.LocalDateTime;

public class EnrolledLearner {
    private Learner learner;
    private LocalDateTime enrollmentDate;

    public EnrolledLearner(Learner l, LocalDateTime e){
        learner = l;
        enrollmentDate = e;
    }

    public Learner getLearner(){
        return learner;
    }
    public LocalDateTime getEnrollmentDate(){
        return enrollmentDate;
    }
}
