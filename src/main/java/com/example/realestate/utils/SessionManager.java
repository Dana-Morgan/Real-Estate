package com.example.realestate.utils;

import com.example.realestate.models.User;

public class SessionManager {
    private static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static String getUserRole() {
        return loggedInUser != null ? loggedInUser.getRole() : null;
    }
}

