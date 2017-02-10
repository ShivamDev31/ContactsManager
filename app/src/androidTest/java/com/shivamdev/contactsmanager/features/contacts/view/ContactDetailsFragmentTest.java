package com.shivamdev.contactsmanager.features.contacts.view;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.util.ViewDisplayUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.shivamdev.contactsmanager.util.TestUtils.withDrawable;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by shivam on 8/2/17.
 */

@RunWith(AndroidJUnit4.class)
public class ContactDetailsFragmentTest {

    private static final String VALID_PHONE_NUMBER = "9696969696";

    private static final Uri DIAL_INTENT_DATA_PHONE_NUMBER = Uri.parse("tel:" + VALID_PHONE_NUMBER);

    private static final Uri SMS_INTENT_DATA_PHONE_NUMBER = Uri.parse("sms:" + VALID_PHONE_NUMBER);

    private static String PACKAGE_ANDROID_DIALER = "com.android.phone";

    // Change it to other package if you are using some other messaging app
    private static String PACKAGE_ANDROID_MESSENGER = "com.android.messaging";

    private Instrumentation.ActivityResult activityResult;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Starting with Android Lollipop the dialer package has changed.
            PACKAGE_ANDROID_DIALER = "com.android.server.telecom";
        }
    }

    @Rule
    public IntentsTestRule<ContactsActivity> activityRule
            = new IntentsTestRule<>(ContactsActivity.class, false, false);

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

    // Should be tested in isolation as these open 3rd party apps and pending tests fails as the app is not in focus
    @Ignore
    @Test
    public void makeACallWhenUserClicksOnCallIcon() {
        startContactDetailsFragment("contact_details");
        onView(withId(R.id.iv_call)).perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_CALL),
                hasData(DIAL_INTENT_DATA_PHONE_NUMBER),
                toPackage(PACKAGE_ANDROID_DIALER)));
    }

    @Test
    public void showErrorIfPhoneNumberIsNotValid() {
        startContactDetailsFragment("invalid_phone_number");
        onView(withId(R.id.iv_call)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility("96969 is invalid.");
    }

    // Should be tested in isolation as these open 3rd party apps and pending tests fails as the app is not in focus
    @Ignore
    @Test
    public void sendSmsWhenUserClicksOnSendSmsButton() {
        startContactDetailsFragment("contact_details");
        onView(withId(R.id.b_send_message)).perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(SMS_INTENT_DATA_PHONE_NUMBER),
                toPackage(PACKAGE_ANDROID_MESSENGER)));
    }

    @Test
    public void showErrorWhileSendingSmsWhenPhoneNumberIsInvalid() {
        startContactDetailsFragment("invalid_phone_number");
        onView(withId(R.id.b_send_message)).perform(click());
        ViewDisplayUtils.checkSnackBarVisibility("96969 is invalid.");
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

        RESTMockServer.whenPUT(pathEndsWith("111.json"))
                .thenReturnFile(200,
                        isFavourite ? "contacts/contact_details_favourite_disabled.json" :
                                "contacts/contact_details_favourite_enabled.json");
        activityRule.launchActivity(null);

        onView(withId(R.id.rv_contacts)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));
    }

}