package com.shivamdev.contactsmanager.features.contacts.view;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.util.ViewDisplayUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.shivamdev.contactsmanager.util.TestUtils.withRecyclerView;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;

/**
 * Created by shivam on 9/2/17.
 */

@RunWith(AndroidJUnit4.class)
public class AddContactFragmentTest {

    @Rule
    public final ActivityTestRule<ContactsActivity> activityRule =
            new ActivityTestRule<>(ContactsActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
    }

    @Test
    public void showSnackBarErrorWhenFirstNameIsEmpty() {
        setupAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_save_contact)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility(R.string.error_first_name_empty);
    }

    @Test
    public void showSnackBarErrorWhenFirstNameIsLessThanThreeCharacters() {
        setupAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText("Sh"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_save_contact)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility(R.string.error_first_name_less_than_three_chars);
    }

    @Test
    public void showSnackBarErrorWhenLastNameIsEmpty() {
        setupAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText("Shivam"));
        onView(withId(R.id.et_last_name)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_save_contact)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility(R.string.error_last_name_empty);
    }

    @Test
    public void showSnackBarErrorWhenLastNameIsLessThanThreeCharacters() {
        setupAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText("Shivam"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_last_name)).perform(typeText("Ch"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_save_contact)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility(R.string.error_last_name_less_than_three_chars);
    }

    @Test
    public void showErrorWhenMobileNumberIsInvalid() {
        setupAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText("Shivam"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_last_name)).perform(typeText("Chopra"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_mobile_number)).perform(typeText("961245"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_save_contact)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility(R.string.error_phone_number_not_valid);
    }

    @Test
    public void showErrorWhenEmailIsInvalid() {
        setupAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText("Shivam"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_last_name)).perform(typeText("Chopra"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_mobile_number)).perform(typeText("9612456969"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_email_address)).perform(typeText("abc@"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_save_contact)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility(R.string.error_email_not_valid);
    }

    @Test
    public void postContactOnServerIfAllValidationsPasses() {
        setupAddContactFragment();
        onView(withId(R.id.et_first_name)).perform(typeText("Shiv"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_last_name)).perform(typeText("Chopra"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_mobile_number)).perform(typeText("9612456969"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_email_address)).perform(typeText("abc@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_save_contact)).perform(click());
        onView(withRecyclerView(R.id.rv_contacts).atPosition(1))
                .check(matches(hasDescendant(withText("Shiv Chopra"))));
    }

    private void setupAddContactFragment() {
        RESTMockServer.whenGET(pathEndsWith("contacts.json"))
                .thenReturnFile(200, "contacts/contacts.json")
                .thenReturnFile(200, "contacts/contact_added.json");
        RESTMockServer.whenPOST(pathEndsWith("contacts"))
                .thenReturnFile(200, "contacts/new_contact.json");
        activityRule.launchActivity(null);

        onView(withId(R.id.fab_add_contact)).perform(click());
    }

}