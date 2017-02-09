package com.shivamdev.contactsmanager.features.contacts.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;

/**
 * Created by shivam on 9/2/17.
 */

@RunWith(AndroidJUnit4.class)
public class AddContactFragmentTest {

    @Rule
    public final ActivityTestRule<ContactsActivity> activityRule =
            new ActivityTestRule<>(ContactsActivity.class);

    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset();
    }

    @Test
    public void showSnackBarWhenFirstNameIsEmpty() {
        setupFragment();
    }

    private void setupFragment() {

    }

}