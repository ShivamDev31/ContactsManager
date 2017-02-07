package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.features.contacts.screen.AddContactScreen;
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

import rx.Observable;

import static com.shivamdev.contactsmanager.common.DummyData.getAllContactDetails;
import static com.shivamdev.contactsmanager.common.DummyData.setContactDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shivam on 6/2/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class AddContactPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Mock
    private ContactsApi contactsApi;

    private AddContactPresenter presenter;
    @Mock
    private AddContactScreen screen;

    @Mock
    private ContactData contactData;

    @Before
    public void setUp() throws Exception {
        presenter = new AddContactPresenter(contactsApi);
        presenter.attachView(screen);
    }

    @Test
    public void showErrorIfFirstNameIsEmpty() {
        setContactDetails(contactData, "", null, null, null);
        presenter.saveContact(contactData);
        verify(screen).showFirstNameEmptyError();
    }

    @Test
    public void showErrorIfFirstNameIsLessThanThreeLetters() {
        setContactDetails(contactData, "sh", null, null, null);
        presenter.saveContact(contactData);
        verify(screen).showFirstNameLessThanThreeError();
    }

    @Test
    public void showErrorIfLastNameIsEmpty() {
        setContactDetails(contactData, "Shivam", "", null, null);
        presenter.saveContact(contactData);
        verify(screen).showLastNameEmptyError();
    }

    @Test
    public void showErrorIfLastNameIsLessThanThreeLetters() {
        setContactDetails(contactData, "Shivam", "Ch", null, null);
        presenter.saveContact(contactData);
        verify(screen).showLastNameLessThanThreeError();
    }

    @Test
    public void showErrorIfPhoneNumberIsNotValid() {
        setContactDetails(contactData, "Shivam", "Chopra", "365", null);
        presenter.saveContact(contactData);
        verify(screen).showPhoneNumberError();
    }

    @Test
    public void showErrorIfEmailIdIsInvalid() {
        setContactDetails(contactData, "Shivam", "Chopra", "9696969696", "abc");
        presenter.saveContact(contactData);
        verify(screen).showEmailError();
    }

    @Test
    public void createANewContactIfAllValidationsPass() {
        getAllContactDetails(contactData);
        when(contactsApi.newContact(contactData)).thenReturn(Observable.just(null));
        presenter.saveContact(contactData);
        verify(screen).showLoader();
        verify(screen).hideLoader();
        verify(screen).contactSavedSuccessfully();
    }

    @Test
    public void shouldShowErrorIfAddContactApiCallFails() {
        getAllContactDetails(contactData);
        RuntimeException exception = new NullPointerException("ApiCallFailed");
        when(contactsApi.newContact(contactData)).thenReturn(Observable.error(exception));
        presenter.saveContact(contactData);
        verify(screen).showLoader();
        verify(screen).showErrorWhileAddingContact(exception);
        verify(screen).hideLoader();
    }

    @After
    public void tearDown() throws Exception {
        presenter.detachView();
    }
}