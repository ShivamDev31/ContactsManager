package com.shivamdev.contactsmanager.common.constants;

/**
 * Created by shivam on 1/2/17.
 */

public interface Constants {


    interface Urls {
        String BASE_URL = "http://gojek-contacts-app.herokuapp.com/";
        String ALL_CONTACTS = "contacts.json";
        String CONTACT_DETAILS = "contacts/{contactId}.json";
        String POST_NEW_CONTACT = "contacts";
        String UPDATE_CONTACT = "contacts/{contactId}.json";
    }

    int REQUEST_CAMERA = 1;
    int SELECT_GALLERY = 2;
}