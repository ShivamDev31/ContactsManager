package com.shivamdev.contactsmanager.features.main.presenter;

import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.features.main.screen.ContactsScreen;

import javax.inject.Inject;

/**
 * Created by shivam on 2/2/17.
 */

public class ContactsActivityPresenter extends BasePresenter<ContactsScreen> {

    @Inject
    public ContactsActivityPresenter() {
    }

    @Override
    public void attachView(ContactsScreen view) {
        super.attachView(view);
    }

    public void addContactsFragment() {
        getView().addContactsFragment();
    }
}