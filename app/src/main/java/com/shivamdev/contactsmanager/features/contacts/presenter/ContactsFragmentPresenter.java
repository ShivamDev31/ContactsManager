package com.shivamdev.contactsmanager.features.contacts.presenter;

import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.db.LocalDataStore;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactsFragmentScreen;
import com.shivamdev.contactsmanager.network.api.ContactsApi;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.RxUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by shivam on 1/2/17.
 */

public class ContactsFragmentPresenter extends BasePresenter<ContactsFragmentScreen> {

    private ContactsApi contactsApi;
    private LocalDataStore dataStore;

    @Inject
    ContactsFragmentPresenter(ContactsApi contactsApi, LocalDataStore dataStore) {
        this.contactsApi = contactsApi;
        this.dataStore = dataStore;
    }

    @Override
    public void attachView(ContactsFragmentScreen view) {
        super.attachView(view);
    }

    public void loadContactsFromApi() {
        checkViewAttached();
        getView().showLoader();
        Subscription subs = //Observable.merge(contactsApi.getContacts(), dataStore.getAllContacts())
                contactsApi.getContacts()
                        .doOnNext(this::saveContactsInDb)
                        //.filter(contactData -> contactData.size() > 0)
                        .compose(RxUtils.applySchedulers())
                        .subscribe(new Subscriber<List<ContactData>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError(e);
                                getView().hideLoader();
                            }

                            @Override
                            public void onNext(List<ContactData> contactData) {
                                Timber.i("Contacts list size : %d", contactData.size());
                                showContactsOnUi(contactData);
                            }
                        });
        addSubscription(subs);
    }

    private void saveContactsInDb(List<ContactData> contactList) {
        dataStore.insertAllContactsIntoDb(contactList);
    }

    private void showContactsOnUi(List<ContactData> contactData) {
        checkViewAttached();
        if (contactData.size() > 0) {
            Collections.sort(contactData);
            getView().updateContacts(contactData);
        } else {
            getView().noContactsFound();
        }
        getView().hideLoader();
    }


    public void contactClicked(Observable<ContactData> contactObservable) {
        Subscription subs = contactObservable.subscribe(new Subscriber<ContactData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ContactData contactData) {
                getView().loadContactDetailsFragment(contactData);
            }
        });
        addSubscription(subs);
    }


}