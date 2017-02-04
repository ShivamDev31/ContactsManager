package com.shivamdev.contactsmanager.features.contacts.screen;

import com.shivamdev.contactsmanager.common.mvp.MvpView;

/**
 * Created by shivam on 3/2/17.
 */

public interface AddContactScreen extends MvpView {

    void showFirstNameEmptyError();

    void showLastNameEmptyError();

    void showPhoneNumberError();

    void showEmailError();

    void showFirstNameLessThanThreeError();

    void showLastNameLessThanThreeError();

    void showErrorWhileAddingContact(Throwable e);

    void showLoader();

    void hideLoader();

    void contactSavedSuccessfully();

    void showImageError();

}