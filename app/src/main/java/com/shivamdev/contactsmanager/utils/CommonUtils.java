package com.shivamdev.contactsmanager.utils;

/**
 * Created by shivam on 4/2/17.
 */

public class CommonUtils {

    public static boolean isPhoneNumberValid(String phoneNumber) {
        String phoneRegex = "^(\\+91|0)?[0-9]{12}$";
        return !StringUtils.isEmpty(phoneNumber) && phoneNumber.matches(phoneRegex);
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
        return !StringUtils.isEmpty(email) && email.matches(emailRegex);
    }

}