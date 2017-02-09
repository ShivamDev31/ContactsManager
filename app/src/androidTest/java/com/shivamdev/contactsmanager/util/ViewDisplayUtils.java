package com.shivamdev.contactsmanager.util;

import android.support.annotation.StringRes;
import android.support.test.espresso.matcher.ViewMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by shivam on 9/2/17.
 */

public class ViewDisplayUtils {

    public static void checkSnackBarVisibility(@StringRes int stringId) {
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(stringId)))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )));
    }

    public static void checkSnackBarVisibility(String message) {
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(message)))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )));
    }
}