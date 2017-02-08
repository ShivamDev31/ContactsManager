package com.shivamdev.contactsmanager.features.contacts.view;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.rules.OkHttpIdlingResourceRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;

/**
 * Created by shivam on 6/2/17.
 */

@RunWith(AndroidJUnit4.class)
public class AddContactFragmentTest {

    private Resources resources;

    @Rule
    OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

    @Rule
    public ActivityTestRule<ContactsActivity> activityTestRule =
            new ActivityTestRule<>(ContactsActivity.class, true, false);


    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
        resources = InstrumentationRegistry.getContext().getResources();
    }

    @Test
    public void showErrorIfFirstNameIsEmpty() {

        RESTMockServer.whenGET(pathEndsWith("contacts.json"))
                .thenReturnFile("contacts.json");

        activityTestRule.launchActivity(null);

        onView(withText("Amitabh")).perform(click());

        /*ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_contact), isDisplayed()));
        floatingActionButton.perform(click());
        onView(withId(R.id.et_first_name)).perform(typeText(""));
        onView(withId(R.id.b_save_contact)).perform(click());
        onView(withId(R.id.b_save_contact)).check(matches(withText("Save")));

        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(resources.getString(R.string.error_first_name_empty))))
                .check(matches(isDisplayed()));*/
    }

    @After
    public void tearDown() throws Exception {

    }

}