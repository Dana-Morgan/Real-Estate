package com.example.realestate.validation;

import com.example.realestate.utils.HibernateUtil;
import com.mysql.cj.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.example.realestate.utils.HibernateUtil.getSessionFactory;


public class ValiditionAgentAccount {

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    // Validate if the name is empty
    public static boolean isNameValid(String name) {
        // Check if the name is not null, not empty, and starts with a letter (case insensitive)
        return name != null && !name.trim().isEmpty() && Character.isLetter(name.charAt(0));
    }


    public static boolean isEmailValid(String email) {
        return email != null && email.matches("^(?=.*\\d)[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }


    // Validate phone number (10 digits)
    public static boolean isPhoneValid(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Validate license number (example: not empty)
    public static boolean isLicenseValid(String license) {
        return license != null && !license.trim().isEmpty() && license.matches("^[A-Za-z0-9-]+$");
    }

    // Validate password length
    public static boolean isPasswordValid(String password) {
        return password != null && !password.trim().isEmpty() &&
                password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    // Method to validate all input fields
    public static String validateAllInputs(String name, String email, String phone, String password, String license) {
        if (!isNameValid(name)) {
            return "Name must start with a letter!";
        }
        if (!isEmailValid(email)) {
            return "Email must end with '@' and '.com'! and numbers!.";
        }
        if (!isPhoneValid(phone)) {
            return "Phone number must be 10 digits.";
        }
        if (!isLicenseValid(license)) {
            return "License number must be valid (can contain letters, numbers, and hyphens).";
        }
        if (!isPasswordValid(password)) {
            return "Password must contain at least one uppercase letter, one number, and be at least 8 characters long!";
        }
        return null;
    }

}
