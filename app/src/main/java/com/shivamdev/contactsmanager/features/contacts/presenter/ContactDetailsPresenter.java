package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactDetailsScreen;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.CommonUtils;
import com.shivamdev.contactsmanager.utils.StringUtils;

import javax.inject.Inject;

/**
 * Created by shivam on 3/2/17.
 */

public class ContactDetailsPresenter extends BasePresenter<ContactDetailsScreen> {

    @Inject
    ContactDetailsPresenter() {

    }

    @Override
    public void attachView(ContactDetailsScreen mvpView) {
        super.attachView(mvpView);
    }

    public void showContactDetailsOnUi(ContactData contact) {
        String contactName = contact.firstName + " " + contact.lastName;
        getView().showContactName(contactName);

        if (!StringUtils.isEmpty(contact.profileUrl)) {
            getView().showProfilePic(contact.profileUrl);
        }

        if (contact.isFavorite) {
            getView().setFavorite();
        } else {
            getView().resetFavorite();
        }

        if (!StringUtils.isEmpty(contact.phoneNumber)) {
            getView().showPhoneNumber(contact.phoneNumber);
        } else {
            getView().hidePhoneNumber();
        }

        if (!StringUtils.isEmpty(contact.email)) {
            getView().showEmailAddress(contact.email);
        } else {
            getView().hideEmailAddress();
        }
    }

    public void callNumber(String phoneNumber) {
        if (CommonUtils.isPhoneNumberValid(phoneNumber)) {
            getView().dialPhoneNumber(phoneNumber);
        } else {
            getView().showInvalidNumberError(phoneNumber);
        }
    }

    public void emailContact(String email) {
        if (CommonUtils.isEmailValid(email)) {
            getView().composeEmail(email);
        } else {
            getView().showInvalidEmailError(email);
        }
    }

    public void sendMessage(String phoneNumber) {
        if (CommonUtils.isPhoneNumberValid(phoneNumber)) {
            getView().composeSms(phoneNumber);
        } else {
            getView().showInvalidNumberError(phoneNumber);
        }

    }
}