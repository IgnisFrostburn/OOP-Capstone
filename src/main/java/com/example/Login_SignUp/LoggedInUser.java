package com.example.Login_SignUp;

import com.example.Account.Account;

public class LoggedInUser {
    private static LoggedInUser instance;

    private Account account;



    private LoggedInUser() {}

    public static LoggedInUser getInstance() {
        if (instance == null) {
            instance = new LoggedInUser();
        }
        return instance;
    }

    public Account getLoggedInAccount() {
        return account;
    }
    public void setLoggedInAccount(Account account) {
        this.account = account;
    }
    public void clearLoggedInAccount() {
        account = null;
        instance = null;
    }
}