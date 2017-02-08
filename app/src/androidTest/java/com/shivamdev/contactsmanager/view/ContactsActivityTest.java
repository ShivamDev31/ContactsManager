package com.shivamdev.contactsmanager.view;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.rules.OkHttpIdlingResourceRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ContactsActivityTest {

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

    @Rule
    public ActivityTestRule<ContactsActivity> activityTestRule
            = new ActivityTestRule<>(ContactsActivity.class, false, false);


    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
    }

    @Test
    public void contactsActivityTest() {
        RESTMockServer.whenGET(pathEndsWith("contacts.json")).thenReturnFile("contacts.json");
        activityTestRule.launchActivity(null);
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_contact), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.b_save_contact), withText("Save"), isDisplayed()));
        appCompatButton.perform(click());

    }

}
