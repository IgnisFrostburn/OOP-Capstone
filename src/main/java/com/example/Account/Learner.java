package com.example.Account;

public class Learner extends Account{
    public Learner(int ID, String firstName, String lastName, String email, String university) {
        super(ID, firstName, lastName, email, university);
    }

    public Learner() {
    }

    public Learner(String fn, String ln, String email){
        this.email = email;
        firstName = fn;
        lastName = ln;
    }
}
