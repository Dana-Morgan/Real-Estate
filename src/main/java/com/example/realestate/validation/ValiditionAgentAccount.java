package com.example.realestate.validation;

import com.example.realestate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class ValiditionAgentAccount {

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    // Validate if the name is empty
    public static boolean isNameValid(String name) {
        // Check if the name is not null and not empty after trimming
        return name != null && !name.trim().isEmpty();
    }

    //Validate if Email contain @ and .com
    public static boolean isEmailValid(String email) {
        return email != null && email.matches("^[A-Za-z+_.-]+@[A-Za-z.-]+\\.[A-Za-z]{2,}$");
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

    //Validate if role not empty
    public static boolean isRoleValid(String role) {
        return role != null && !role.trim().isEmpty();
    }

    // Method to validate all input fields
    public static String validateAllInputs(String name, String email, String phone, String password, String role) {
        if (!isNameValid(name)) {
            return "Name is required example: jana";
        }
        if (!isEmailValid(email)) {
            return "Email must end with '@' and '.com'! example: jana@gmail.com).";
        }
        if (!isPhoneValid(phone)) {
            return "Phone number must be 10 digits example: 0123456789.";
        }
        if (!isPasswordValid(password)) {
            return "Password must contain at least one uppercase letter, one number, and be at least 8 characters long! example: Jana0123456";
        }
        if (!isRoleValid(role)){
            return "Please Select One -Agent- Or -Admin-";
        }
        return null;
    }

}
