package com.shivamdev.contactsmanager.features.contacts.presenter;

import android.os.Environment;

import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactDetailsScreen;
import com.shivamdev.contactsmanager.network.api.ContactsApi;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.CommonUtils;
import com.shivamdev.contactsmanager.utils.RxUtils;
import com.shivamdev.contactsmanager.utils.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void updateFavorite(ContactData contact) {
        checkViewAttached();
        getView().showFavoriteLoader();
        contact.isFavorite = !contact.isFavorite;
        Subscription subs = contactsApi.updateContact(contact.id, contact)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Subscriber<ContactData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().errorWhileUpdatingFavorite(e);
                    }

                    @Override
                    public void onNext(ContactData data) {
                        if (data.isFavorite) {
                            getView().setFavorite();
                        } else {
                            getView().resetFavorite();
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

    public File generateVCF(ContactData contact) {

        //Create a vcf file
        String filename = Environment.getExternalStorageDirectory() + "/generated.vcf";

        File vcfFile = new File(filename);
        FileWriter fw;
        try {
            fw = new FileWriter(vcfFile);
            fw.write("BEGIN:VCARD\r\n");
            fw.write("VERSION:3.0\r\n");
            fw.write("N:" + contact.lastName + ";" + contact.firstName + "\r\n");
            fw.write("FN:" + contact.firstName + " " + contact.lastName + "\r\n");
            fw.write("TEL;TYPE=HOME,VOICE:" + contact.phoneNumber + "\r\n");
            fw.write("EMAIL;TYPE=PREF,INTERNET:" + contact.email + "\r\n");
            fw.write("END:VCARD\r\n");
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return vcfFile;
    }
}