package com.shivamdev.contactsmanager.features.contacts.view;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by shivam on 6/2/17.
 */
@RunWith(AndroidJUnit4.class)
public class AddContactFragmentTest {

    private Resources resources;

    @Rule
    public ActivityTestRule<ContactsActivity> activityTestRule =
            new ActivityTestRule<>(ContactsActivity.class, false, false);


    @Before
    public void setUp() throws Exception {

        resources = InstrumentationRegistry.getContext().getResources();
    }

    @Test
    public void showErrorIfFirstNameIsEmpty() {
        startAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText(""));
        onView(withId(R.id.b_save_contact)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(resources.getString(R.string.error_first_name_empty))))
                .check(matches(isDisplayed()));
    }

    private void startAddContactFragment() {
        activityTestRule.launchActivity(null);
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_contact), isDisplayed()));
        floatingActionButton.perform(click());
    }

    @After
    public void tearDown() throws Exception {

    }

}