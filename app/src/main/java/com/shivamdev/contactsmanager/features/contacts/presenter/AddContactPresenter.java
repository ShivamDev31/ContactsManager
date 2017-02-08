package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.db.LocalDataStore;
import com.shivamdev.contactsmanager.features.contacts.screen.AddContactScreen;
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

public class AddContactPresenter extends BasePresenter<AddContactScreen> {

    private ContactsApi contactsApi;
    private LocalDataStore dataStore;

    @Inject
    AddContactPresenter(ContactsApi contactsApi, LocalDataStore dataStore) {
        this.contactsApi = contactsApi;
        this.dataStore = dataStore;
    }


    public void saveContact(ContactData contact) {
        if (StringUtils.isEmpty(contact.firstName)) {
            getView().showFirstNameEmptyError();
            return;
        }

        if (contact.firstName.length() < 3) {
            getView().showFirstNameLessThanThreeError();
            return;
        }

        if (StringUtils.isEmpty(contact.lastName)) {
            getView().showLastNameEmptyError();
            return;
        }

        if (contact.lastName.length() < 3) {
            getView().showLastNameLessThanThreeError();
            return;
        }

        if (!CommonUtils.isPhoneNumberValid(contact.phoneNumber)) {
            getView().showPhoneNumberError();
            return;
        }

        if (!CommonUtils.isEmailValid(contact.email)) {
            getView().showEmailError();
            return;
        }

        saveContactOnServer(contact);
    }

    private void saveContactOnServer(ContactData contact) {
        checkViewAttached();
        getView().showLoader();
        Subscription subs = contactsApi.newContact(contact)
                .doOnNext(aVoid -> dataStore.createContact(contact))
                .compose(RxUtils.applySchedulers())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showErrorWhileAddingContact(e);
                        getView().hideLoader();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        getView().hideLoader();
                        getView().contactSavedSuccessfully();
                    }
                });
        addSubscription(subs);
    }
}