package com.example.Login_SignUp;

public class LoggedInUser {
    private static LoggedInUser instance;

    private String email;
    private String firstName;
    private String lastName;
    private String university;
    private String role;
    private int ID;


    private LoggedInUser() {}


    public static LoggedInUser getInstance() {
        if (instance == null) {
            instance = new LoggedInUser();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void clear() {
        email = null;
        firstName = null;
        lastName = null;
        university = null;
        role = null;
    }
}