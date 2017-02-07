package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.common.DummyData;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactDetailsScreen;
import com.shivamdev.contactsmanager.network.api.ContactsApi;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.util.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shivam on 7/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactDetailsPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();


    private ContactDetailsPresenter presenter;

    @Mock
    private ContactsApi contactsApi;

    @Mock
    private ContactDetailsScreen screen;

    @Mock
    private ContactData contactData;

    @Before
    public void setUp() throws Exception {
        presenter = new ContactDetailsPresenter(contactsApi);
        presenter.attachView(screen);
        DummyData.getAllContactDetails(contactData);
    }

    @Test
    public void getContactDetailsFromServerAndDisplayOnUi() {
        when(contactsApi.getContact(anyInt())).thenReturn(Observable.just(contactData));
        presenter.getContactDetailsFromServer(contactData.id);
        verify(screen).showLoader();
        verify(screen).showContactName(anyString());
        verify(screen).showProfilePic(anyString());
        verify(screen).setFavourite();
        verify(screen).showPhoneNumber(anyString());
        verify(screen).showEmailAddress(anyString());
        verify(screen).updateContactData(any());
        verify(screen).hideLoader();
    }

    @Test
    public void shouldShowErrorWhileFetchingContactDetailsFromServer() {
        RuntimeException exception = new NullPointerException("Error fetching contact details");
        when(contactsApi.getContact(anyInt())).thenReturn(Observable.error(exception));
        presenter.getContactDetailsFromServer(contactData.id);
        verify(screen).showLoader();
        verify(screen).hideLoader();
        verify(screen).showError(exception);
    }

    @Test
    public void setFavouriteWhenItsTrue() {
        contactData.isFavourite = true;
        when(contactsApi.getContact(anyInt())).thenReturn(Observable.just(contactData));
        presenter.getContactDetailsFromServer(contactData.id);
        verify(screen).setFavourite();
    }

    @Test
    public void hidePhoneNumberWhenItsEmpty() {
        contactData.phoneNumber = "";
        when(contactsApi.getContact(anyInt())).thenReturn(Observable.just(contactData));
        presenter.getContactDetailsFromServer(contactData.id);
        verify(screen).hidePhoneNumber();
    }

    @Test
    public void hideEmailWhenItsEmpty() {
        contactData.email = "";
        when(contactsApi.getContact(anyInt())).thenReturn(Observable.just(contactData));
        presenter.getContactDetailsFromServer(contactData.id);
        verify(screen).hideEmailAddress();
    }

    @Test
    public void shouldCallPhoneWhenNumberIsValid() {
        presenter.callNumber(contactData.phoneNumber);
        verify(screen).dialPhoneNumber(contactData.phoneNumber);
    }

    @Test
    public void showErrorWhenUserTriesToCallAndNumberIsNotValid() {
        contactData.phoneNumber = "98586";
        presenter.callNumber(contactData.phoneNumber);
        verify(screen).showInvalidNumberError(contactData.phoneNumber);
    }

    @Test
    public void shouldSendEmailWhenEmailIsValid() {
        presenter.emailContact(contactData.email);
        verify(screen).composeEmail(contactData.email);
    }

    @Test
    public void showErrorWhenUserTriesToSendEmailAndEmailIsNotValid() {
        contactData.email = "abc";
        presenter.emailContact(contactData.email);
        verify(screen).showInvalidEmailError(contactData.email);
    }

    @Test
    public void shouldSendSmsWhenPhoneNumberIsValid() {
        presenter.sendMessage(contactData.phoneNumber);
        verify(screen).composeSms(contactData.phoneNumber);
    }

    @Test
    public void shouldShowErrorWhenNumberIsInvalidWhileSendingSms() {
        contactData.phoneNumber = "96869";
        presenter.sendMessage(contactData.phoneNumber);
        verify(screen).showInvalidNumberError(contactData.phoneNumber);
    }

    @Test
    public void showShareContactDialogWhenShareContactIsClicked() {
        presenter.shareContact();
        verify(screen).showShareContactDialog();
    }

    @Test
    public void resetContactAsFavouriteOnServer() {
        contactData.isFavourite = true;
        when(contactsApi.updateContact(contactData.id, contactData))
                .thenReturn(Observable.just(contactData));
        presenter.updateFavourite(contactData);
        verify(screen).showFavouriteLoader();
        verify(screen).resetFavourite();
        verify(screen).hideLoader();
    }

    @Test
    public void setContactAsFavouriteOnServer() {
        contactData.isFavourite = false;
        when(contactsApi.updateContact(contactData.id, contactData))
                .thenReturn(Observable.just(contactData));
        presenter.updateFavourite(contactData);
        verify(screen).showFavouriteLoader();
        verify(screen).setFavourite();
        verify(screen).hideLoader();
    }

    @Test
    public void shouldShowErrorWhileUpdatingFavouriteStatus() {
        RuntimeException exception = new NullPointerException("Error status update");
        when(contactsApi.updateContact(contactData.id, contactData))
                .thenReturn(Observable.error(exception));
        presenter.updateFavourite(contactData);
        verify(screen).showFavouriteLoader();
        verify(screen).hideLoader();
        verify(screen).errorWhileUpdatingFavourite(exception);
    }
}