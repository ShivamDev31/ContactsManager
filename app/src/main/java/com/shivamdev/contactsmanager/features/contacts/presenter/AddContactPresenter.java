package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.db.LocalDataStore;
import com.shivamdev.contactsmanager.features.contacts.screen.AddContactScreen;
import com.shivamdev.contactsmanager.network.api.ContactsApi;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.CommonUtils;
import com.shivamdev.contactsmanager.utils.RxUtils;
import com.shivamdev.contactsmanager.utils.StringUtils;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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


    public void saveContact(File image, ContactData contact) {
        if (StringUtils.isEmpty(contact.firstName)) {
            getView().showFirstNameEmptyError();
            return;
        }

        if (contact.firstName.length() < 3) {
            getView().showFirstNameLessThanThreeError();
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

        if (image == null) {
            getView().showImageError();
            return;
        }

        saveContactOnServer(image, contact);
    }

    private void saveContactOnServer(File image, ContactData contact) {
        checkViewAttached();
        getView().showLoader();
        RequestBody profileImage = RequestBody.create(MediaType.parse("image/*"), image);
        RequestBody firstName = getRequestBody(contact.firstName);
        RequestBody lastName = getRequestBody(contact.lastName);
        RequestBody email = getRequestBody(contact.email);
        RequestBody mobile = getRequestBody(contact.phoneNumber);
        MultipartBody.Part imageBody =
                MultipartBody.Part.createFormData("profile_pic", image.getName(), profileImage);
        //Subscription subs = contactsApi.postContact(firstName, lastName, email, mobile)//, imageBody)
        Subscription subs = contactsApi.newContact(contact)
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

    private RequestBody getRequestBody(String content) {
        return RequestBody.create(
                MediaType.parse("application/json"), content);
    }
}