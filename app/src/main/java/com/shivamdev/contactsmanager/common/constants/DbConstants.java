package com.shivamdev.contactsmanager.common.constants;

/**
 * Created by shivam on 1/2/17.
 */

public interface DbConstants {
    int VERSION = 1;
    String DB_NAME = "contacts";
    String CONTACTS_TABLE = "contacts";

    // Table fields
    interface ContactsTable {
        String ID = "id";
        String CONTACT_ID = "contact_id";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String EMAIL = "email";
        String PHONE_NUMBER = "phone_number";
        String PROFILE_URL = "profile_pic";
        String IS_FAVORITE = "favorite";
        String CREATED_AT = "created_at";
        String UPDATED_AT = "updated_at";
    }
}