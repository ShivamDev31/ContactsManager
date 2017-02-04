package com.shivamdev.contactsmanager.utils;

import android.util.Log;
import android.widget.Toast;

import com.shivamdev.contactsmanager.common.ContactsApplication;

/**
 * Created by shivam on 1/2/17.
 */

public final class Logger {
    private static final String TAG = Logger.class.getSimpleName();

    public static void log(String tag, String message) {
        Log.d(tag, message);
    }

    public static void log(String message) {
        log(TAG, message);
    }

    public static void logE(String tag, String message, Throwable exception) {
        Log.e(tag, message, exception);
    }

    public static void logE(String tag, Throwable exception) {
        logE(tag, "Exception : ", exception);
    }

    public static void logE(Throwable exception) {
        logE(TAG, exception);
    }

    public static void toast(String message) {
        Toast.makeText(ContactsApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int message) {
        Toast.makeText(ContactsApplication.getInstance(),
                ContactsApplication.getInstance().getString(message),
                Toast.LENGTH_SHORT).show();
    }
}