package com.example.Database;

import java.sql.SQLException;

public abstract class UserDatabase extends DatabaseConnection{
    public UserDatabase(){super();}
    public abstract void insertData(String lastName, String firstName, String middleName,String university, String email, String password);
    public abstract void getUserData(String email);
    public abstract boolean checkEmail(String email) throws SQLException;
    public abstract boolean checkPassword(String userPassword, String email) throws SQLException;
}
