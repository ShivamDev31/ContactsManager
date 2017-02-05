package com.shivamdev.contactsmanager.features.contacts.screen;

import com.shivamdev.contactsmanager.common.mvp.MvpView;
import com.shivamdev.contactsmanager.network.data.ContactData;

/**
 * Created by shivam on 3/2/17.
 */

public interface ContactDetailsScreen extends MvpView {

    void showError(Throwable e);

    void updateContactData(ContactData data);

    void showProfilePic(String profileUrl);

    void setFavourite();

    void resetFavourite();

    void showContactName(String contactName);

    void showPhoneNumber(String phoneNumber);

    void hidePhoneNumber();

    void showEmailAddress(String email);

    void hideEmailAddress();

    void dialPhoneNumber(String phoneNumber);

    void showInvalidNumberError(String phoneNumber);

    void composeEmail(String email);

    void showInvalidEmailError(String email);

    void composeSms(String phoneNumber);

    void showLoader();

    void hideLoader();

    void errorWhileUpdatingFavourite(Throwable e);

    void showFavouriteLoader();

    void showShareContactDialog();
}