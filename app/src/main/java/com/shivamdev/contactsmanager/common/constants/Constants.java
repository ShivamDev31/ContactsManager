package com.shivamdev.contactsmanager.common.constants;

/**
 * Created by shivam on 1/2/17.
 */

public interface Constants {


    interface Urls {
        String BASE_URL = "http://gojek-contacts-app.herokuapp.com/";
        String ALL_CONTACTS = "contacts.json";
        String POST_NEW_CONTACT = "contacts.json";
        String UPDATE_CONTACT = "contacts.json";
    }

    int REQUEST_CAMERA = 1;
    int SELECT_GALLERY = 2;
    CharSequence[] IMAGE_SELECTION_OPTIONS = {"Take Photo", "From Gallery", "Cancel"};
}