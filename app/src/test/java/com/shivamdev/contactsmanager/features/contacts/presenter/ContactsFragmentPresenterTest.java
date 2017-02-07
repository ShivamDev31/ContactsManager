package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.common.DummyData;
import com.shivamdev.contactsmanager.db.LocalDataStore;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactsFragmentScreen;
import com.shivamdev.contactsmanager.network.api.ContactsApi;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shivam on 7/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactsFragmentPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Mock
    private ContactsApi contactsApi;

    @Mock
    private LocalDataStore dataStore;

    @Mock
    private ContactsFragmentScreen screen;

    private ContactsFragmentPresenter presenter;

    @Mock
    private ContactData contactData;

    @Before
    public void setUp() throws Exception {
        presenter = new ContactsFragmentPresenter(contactsApi, dataStore);
        presenter.attachView(screen);
    }

    @Test
    public void loadContactsFromServerItAndShowOnUi() {
        when(contactsApi.getContacts())
                .thenReturn(Observable.just(DummyData.getContactsList(contactData)));

        presenter.loadContactsFromApi();
        verify(screen).showLoader();
        verify(screen).updateContacts(DummyData.getContactsList(contactData));
        verify(screen).hideLoader();
    }

    @Test
    public void showErrorWhenFetchingContactDetailFails() {
        RuntimeException exception = new NullPointerException("Fetching contacts from api failed");
        when(contactsApi.getContacts())
                .thenReturn(Observable.error(exception));
        presenter.loadContactsFromApi();
        verify(screen).showLoader();
        verify(screen).hideLoader();
        verify(screen).showError(exception);
    }

    @Test
    public void showNoContactsFoundMessageWhenApiReturnsNoRecords() {
        when(contactsApi.getContacts())
                .thenReturn(Observable.just(new ArrayList<>()));
        presenter.loadContactsFromApi();
        verify(screen).showLoader();
        verify(screen).noContactsFound();
        verify(screen).hideLoader();
    }

    @Test
    public void loadContactsFromDbIfInternetIsNotThere() {
        when(dataStore.getAllContacts())
                .thenReturn(Observable.just(DummyData.getContactsList(contactData)));
        presenter.loadContactsFromDatabase();
        verify(screen).showLoader();
        verify(screen).updateContacts(DummyData.getContactsList(contactData));
        verify(screen).hideLoader();
    }

    @Test
    public void showErrorIfIssuesWithFetchingRecordsFromDatabase() {
        RuntimeException exception = new NullPointerException("Fetching contact from db failed");
        when(dataStore.getAllContacts())
                .thenReturn(Observable.error(exception));
        presenter.loadContactsFromDatabase();
        verify(screen).showLoader();
        verify(screen).hideLoader();
        verify(screen).showError(exception);
    }

    @Test
    public void showNoContactsFoundMessageWhenDatabaseReturnsNoRecords() {
        when(dataStore.getAllContacts())
                .thenReturn(Observable.just(new ArrayList<>()));
        presenter.loadContactsFromDatabase();
        verify(screen).showLoader();
        verify(screen).noContactsFound();
        verify(screen).hideLoader();
    }

    @Test
    public void showErrorWhenContactClickGivesError() {
        RuntimeException exception = new NullPointerException("Fetching contact details failed");
        presenter.contactClicked(Observable.error(exception));
        verify(screen).showError(exception);
    }

    @Test
    public void showContactDetailsScreenWhenAContactIsClicked() {
        presenter.contactClicked(Observable.just(contactData));
        verify(screen).loadContactDetailsFragment(contactData);
    }

    @After
    public void tearDown() throws Exception {
        presenter.detachView();
    }
}