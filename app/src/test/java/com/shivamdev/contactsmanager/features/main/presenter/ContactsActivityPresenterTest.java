package com.shivamdev.contactsmanager.features.main.presenter;

import com.shivamdev.contactsmanager.features.main.screen.ContactsScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by shivam on 6/2/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class ContactsActivityPresenterTest {

    private ContactsScreen screen;
    private ContactsActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        screen = mock(ContactsScreen.class);
        presenter = new ContactsActivityPresenter();
        presenter.attachView(screen);
    }

    @Test
    public void checkIfContactsFragmentIsAttachedToActivity() throws Exception {
        presenter.addContactsFragment();
        verify(screen).addContactsFragment();
    }

    @After
    public void tearDown() throws Exception {
        presenter.detachView();
    }

}