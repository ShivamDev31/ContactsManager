package com.shivamdev.contactsmanager.features.contacts.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.util.ResourceUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.shivamdev.contactsmanager.util.TestUtils.withRecyclerView;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;

/**
 * Created by shivam on 8/2/17.
 */

@RunWith(AndroidJUnit4.class)
public class ContactsFragmentTest {

    @Rule
    public final ActivityTestRule<ContactsActivity> activityRule
            = new ActivityTestRule<>(ContactsActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
    }

    @Test
    public void showContactsWhenApiCallReturnsSuccessfully() {
        RESTMockServer.whenGET(pathContains("contacts.json"))
                .thenReturnFile(200, "contacts/contacts.json");
        activityRule.launchActivity(null);
        onView(withId(R.id.rv_contacts)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfProperContactIsGettingDisplayed() {
        RESTMockServer.whenGET(pathContains("contacts.json"))
                .thenReturnFile("contacts/contacts.json");
        activityRule.launchActivity(null);
        onView(withId(R.id.rv_contacts)).check(matches(isDisplayed()));
        onView(withText("Amitabh Bachchan")).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfContactsAreSorted() {
        RESTMockServer.whenGET(pathEndsWith("contacts.json"))
                .thenReturnFile(200, "contacts/contacts.json");
        activityRule.launchActivity(null);
        onView(withId(R.id.rv_contacts)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.rv_contacts).atPosition(0))
                .check(matches(hasDescendant(withText("Amitabh Bachchan"))));
        onView(withRecyclerView(R.id.rv_contacts).atPosition(1))
                .check(matches(hasDescendant(withText("Shivam Chopra"))));

    }

    @Test
    public void showErrorMessageWhenApiCallFails() {
        RESTMockServer.whenGET(pathContains("contacts.json")).thenReturnEmpty(400);
        activityRule.launchActivity(null);
        onView(withId(R.id.tv_contacts_error)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfContactsAreEmptyThenCorrectErrorIsDisplayed() {
        RESTMockServer.whenGET(pathContains("contacts.json")).thenReturnEmpty(400);
        activityRule.launchActivity(null);
        onView(withId(R.id.tv_contacts_error)).check(matches(isDisplayed()));
        onView(withText(ResourceUtil.getString(R.string.error_loading_contacts)))
                .check(matches(isDisplayed()));
    }
}