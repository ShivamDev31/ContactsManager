package com.shivamdev.contactsmanager.features.contacts.screen;

import com.shivamdev.contactsmanager.common.mvp.MvpView;
import com.shivamdev.contactsmanager.network.data.ContactData;

import java.util.List;

/**
 * Created by shivam on 1/2/17.
 */

public interface ContactsFragmentScreen extends MvpView {

    void showError(Throwable throwable);

    void showLoader();

    void hideLoader();

    void noContactsFound();

    void updateContacts(List<ContactData> contactsList);

    void loadContactDetailsFragment(ContactData contactData);
}