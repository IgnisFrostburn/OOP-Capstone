package com.example.Database;
import com.example.Login_SignUp.LoggedInUser;

import java.sql.*;

public abstract class DatabaseConnection {
    protected String url = "jdbc:mysql://192.168.1.2:3306/excelone";
    protected String username = "excelOneAdmin";
    protected String password = "secure123";
    protected static Connection connection;

    public DatabaseConnection()  {
        try{
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error on creating DB Connection: " + e.getMessage());
        }
    }



}
