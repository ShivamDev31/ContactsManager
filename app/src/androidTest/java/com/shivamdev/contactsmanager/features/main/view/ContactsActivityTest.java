package com.shivamdev.contactsmanager.features.main.view;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.shivamdev.contactsmanager.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ContactsActivityTest {


    @Rule
    public ActivityTestRule<ContactsActivity> mActivityTestRule =
            new ActivityTestRule<>(ContactsActivity.class, false, false);

    @Test
    public void contactsActivityTest() {

        mActivityTestRule.launchActivity(null);
        onView(withId(R.id.fab_activity)).perform(click());
        /*onView(withId(R.id.et_first_name)).perform(typeText(""));
        onView(withId(R.id.b_save_contact)).perform(click());
        Resources resources = InstrumentationRegistry.getContext().getResources();
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(resources.getString(R.string.error_first_name_empty))))
                .check(matches(isDisplayed()));*/


    }

}
