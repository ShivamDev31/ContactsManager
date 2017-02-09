package com.shivamdev.contactsmanager.features.contacts.view;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.shivamdev.contactsmanager.util.TestUtils.withDrawable;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;

/**
 * Created by shivam on 8/2/17.
 */

@RunWith(AndroidJUnit4.class)
public class ContactDetailsFragmentTest {

    @Rule
    public ActivityTestRule<ContactsActivity> activityRule
            = new ActivityTestRule<>(ContactsActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
    }

    @Test
    public void fetchContactDetailsBasedOnContactIdAndShowOnUi() {
        startContactDetailsFragment("contact_details");

        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.ll_phone_number)).check(matches(isDisplayed()));
        onView(withText("9696969696")).check(matches(isDisplayed()));
        onView(withId(R.id.ll_email_address)).check(matches(isDisplayed()));
        onView(withText("test@test.com")).check(matches(isDisplayed()));
        onView(withId(R.id.iv_favourite))
                .check(matches(withDrawable(R.drawable.ic_favourite_checked)));
    }

    @Test
    public void showOnlyNameIfPhoneAndEmailAreNotPresent() {
        startContactDetailsFragment("contact_detail_name");
        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.ll_phone_number))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.ll_email_address))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.iv_favourite))
                .check(matches(withDrawable(R.drawable.ic_favourite_unchecked)));
    }

    @Test
    public void showOnlyNameAndPhoneIfEmailIsNotPresent() {
        startContactDetailsFragment("contact_details_phone_no");
        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.ll_phone_number)).check(matches(isDisplayed()));
        onView(withText("1212322112")).check(matches(isDisplayed()));
        onView(withId(R.id.ll_email_address))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void showOnlyNameAndEmailIfPhoneNoIsNotPresent() {
        startContactDetailsFragment("contact_details_email");
        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.ll_email_address)).check(matches(isDisplayed()));
        onView(withText("test@test.com")).check(matches(isDisplayed()));
        onView(withId(R.id.ll_phone_number))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void checkIfFavouriteIsEnabled() {
        startContactDetailsFragment("contact_details_favourite_enabled");
        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.iv_favourite))
                .check(matches(withDrawable(R.drawable.ic_favourite_checked)));
    }

    @Test
    public void checkIfFavouriteIsDisabled() {
        startContactDetailsFragment("contact_details_favourite_disabled");
        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.iv_favourite))
                .check(matches(withDrawable(R.drawable.ic_favourite_unchecked)));
    }

    @Test
    public void changeFavouriteStatusOnClickFromDisabledToEnabled() {
        updateContact("contact_details_favourite_enabled", false);
        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.iv_favourite)).perform(click());
        onView(withId(R.id.iv_favourite))
                .check(matches(withDrawable(R.drawable.ic_favourite_checked)));
    }

    @Test
    public void changeFavouriteStatusOnClickFromEnabledToDisabled() {
        updateContact("contact_details_favourite_disabled", true);
        onView(withId(R.id.ll_name)).check(matches(isDisplayed()));
        onView(withText("Shivam Chopr")).check(matches(isDisplayed()));
        onView(withId(R.id.iv_favourite)).perform(click());
        onView(withId(R.id.iv_favourite))
                .check(matches(withDrawable(R.drawable.ic_favourite_unchecked)));
    }

    private void startContactDetailsFragment(String jsonFile) {
        RESTMockServer.whenGET(pathEndsWith("contacts.json"))
                .thenReturnFile(200, "contacts/contacts.json");
        RESTMockServer.whenGET(pathEndsWith("111.json"))
                .thenReturnFile(200, "contacts/" + jsonFile + ".json");
        RESTMockServer.whenPOST(pathEndsWith("111.json"))
                .thenReturnFile(200, "contacts/" + jsonFile + ".json");
        activityRule.launchActivity(null);

        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));
    }

    private void updateContact(String jsonFile, boolean isFavourite) {
        RESTMockServer.whenGET(pathEndsWith("contacts.json"))
                .thenReturnFile(200, "contacts/contacts.json");
        RESTMockServer.whenGET(pathEndsWith("111.json"))
                .thenReturnFile(200, "contacts/" + jsonFile + ".json");

        RESTMockServer.whenPOST(pathEndsWith("111.json"))
                .thenReturnFile(200,
                        isFavourite ? "contacts/contact_details_favourite_disabled.json" :
                                "contacts/contact_details_favourite_enabled.json");
        activityRule.launchActivity(null);

        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));
    }

}