package com.example.Account;

public abstract class Account {
    int ID;
    String firstName;
    String lastName;
    String University;
    String email;

    public Account() {
    }

    public Account(int ID, String firstName, String lastName, String email, String university) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        University = university;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUniversity() {
        return University;
    }

    public String getEmail() {
        return email;
    }


}
