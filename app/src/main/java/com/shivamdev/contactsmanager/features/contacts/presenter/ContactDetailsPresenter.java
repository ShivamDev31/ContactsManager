package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactDetailsScreen;
import com.shivamdev.contactsmanager.network.api.ContactsApi;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.CommonUtils;
import com.shivamdev.contactsmanager.utils.RxUtils;
import com.shivamdev.contactsmanager.utils.StringUtils;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by shivam on 3/2/17.
 */

public class ContactDetailsPresenter extends BasePresenter<ContactDetailsScreen> {

    private ContactsApi contactsApi;

    @Inject
    ContactDetailsPresenter(ContactsApi contactsApi) {
        this.contactsApi = contactsApi;
    }

    @Override
    public void attachView(ContactDetailsScreen mvpView) {
        super.attachView(mvpView);
    }

    public void getContactDetailsFromServer(int contactId) {
        checkViewAttached();
        getView().showLoader();
        Subscription subs = contactsApi.getContact(contactId)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Subscriber<ContactData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoader();
                        getView().showError(e);
                    }

                    @Override
                    public void onNext(ContactData data) {
                        showContactDetailsOnUi(data);
                        getView().updateContactData(data);
                        getView().hideLoader();
                    }
                });
        addSubscription(subs);
    }

    private void showContactDetailsOnUi(ContactData contact) {
        String contactName = contact.firstName + " " + contact.lastName;
        getView().showContactName(contactName);

        if (!StringUtils.isEmpty(contact.profileUrl)) {
            getView().showProfilePic(contact.profileUrl);
        }

        if (contact.isFavourite) {
            getView().setFavourite();
        } else {
            getView().resetFavourite();
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

    public void updateFavourite(ContactData contact) {
        checkViewAttached();
        getView().showFavouriteLoader();
        contact.isFavourite = !contact.isFavourite;
        Subscription subs = contactsApi.updateContact(contact.id, contact)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Subscriber<ContactData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoader();
                        getView().errorWhileUpdatingFavourite(e);
                    }

                    @Override
                    public void onNext(ContactData data) {
                        if (data.isFavourite) {
                            getView().setFavourite();
                        } else {
                            getView().resetFavourite();
                        }
                        getView().hideLoader();
                    }
                });
        addSubscription(subs);
    }

    public void shareContact() {
        checkViewAttached();
        getView().showShareContactDialog();
    }

}