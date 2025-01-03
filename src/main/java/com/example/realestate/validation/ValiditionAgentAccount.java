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

    // Validate password length
    public static boolean isPasswordValid(String password) {
        return password != null && !password.trim().isEmpty() &&
                password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    public static boolean isRoleValid(String role) {
        return role != null && !role.trim().isEmpty();
    }

    // Method to validate all input fields
    public static String validateAllInputs(String name, String email, String phone, String password, String role) {
        if (!isNameValid(name)) {
            return "Name must start with a letter!";
        }
        if (!isEmailValid(email)) {
            return "Email must end with '@' and '.com'! and numbers! jana123@gmail.com).";
        }
        if (!isPhoneValid(phone)) {
            return "Phone number must be 10 digits.";
        }
        if (!isPasswordValid(password)) {
            return "Password must contain at least one uppercase letter, one number, and be at least 8 characters long!";
        }
        if (!isRoleValid(role)){
            return "Please select a one role!";
        }
        return null;
    }

}
