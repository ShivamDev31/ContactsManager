package com.shivamdev.contactsmanager.utils;

/**
 * Created by shivam on 4/2/17.
 */

public class CommonUtils {

    public static boolean isPhoneNumberValid(String phoneNumber) {
        String phoneRegex = "\\+?\\d{10,12}";
        return !StringUtils.isEmpty(phoneNumber) && phoneNumber.matches(phoneRegex);
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "\\A([^@\\s]+)@((?:[-a-z0-9]+\\.)+[a-z]{2,})\\Z";
        return !StringUtils.isEmpty(email) && email.matches(emailRegex);
    }

}