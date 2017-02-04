/*
package com.shivamdev.contactsmanager.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.shivamdev.contactsmanager.common.constants.DbConstants.ContactsTable;
import com.shivamdev.contactsmanager.db.Db;

import rx.functions.Func1;

*/
/**
 * Created by shivam on 3/2/17.
 *//*


public class Contact {
    public int contactId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String profileUrl;
    public boolean isFavorite;
    public String createdAt;
    public String updatedAt;

    public static final Func1<Cursor, Contact> MAPPER = cursor -> {
        Contact contact = new Contact();
        contact.contactId = Db.getInt(cursor, ContactsTable.CONTACT_ID);
        contact.firstName = Db.getString(cursor, ContactsTable.FIRST_NAME);
        contact.lastName = Db.getString(cursor, ContactsTable.LAST_NAME);
        contact.email = Db.getString(cursor, ContactsTable.EMAIL);
        contact.phoneNumber = Db.getString(cursor, ContactsTable.PHONE_NUMBER);
        contact.profileUrl = Db.getString(cursor, ContactsTable.PROFILE_URL);
        contact.isFavorite = Db.getBoolean(cursor, ContactsTable.IS_FAVORITE);
        contact.createdAt = Db.getString(cursor, ContactsTable.CREATED_AT);
        contact.updatedAt = Db.getString(cursor, ContactsTable.UPDATED_AT);
        return null;
    };

    public static final class Builder {

        private final ContentValues values = new ContentValues();

        public Builder(Contact contact) {
            values.put(ContactsTable.CONTACT_ID, contact.contactId);
            values.put(ContactsTable.FIRST_NAME, contact.firstName);
            values.put(ContactsTable.LAST_NAME, contact.lastName);
            values.put(ContactsTable.PHONE_NUMBER, contact.phoneNumber);
            values.put(ContactsTable.EMAIL, contact.email);
            values.put(ContactsTable.PROFILE_URL, contact.profileUrl);
            values.put(ContactsTable.IS_FAVORITE, contact.isFavorite);
            values.put(ContactsTable.CREATED_AT, contact.createdAt);
            values.put(ContactsTable.UPDATED_AT, contact.updatedAt);
        }

        public Builder setContactId(int contactId) {
            values.put(ContactsTable.CONTACT_ID, contactId);
            return this;
        }

        public Builder setFirstName(String firstName) {
            values.put(ContactsTable.FIRST_NAME, firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            values.put(ContactsTable.LAST_NAME, lastName);
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            values.put(ContactsTable.PHONE_NUMBER, phoneNumber);
            return this;
        }

        public Builder setEmail(String email) {
            values.put(ContactsTable.EMAIL, email);
            return this;
        }

        public Builder setProfileUrl(String profileUrl) {
            values.put(ContactsTable.PROFILE_URL, profileUrl);
            return this;
        }

        public Builder setFavorite(int isFavorite) {
            values.put(ContactsTable.IS_FAVORITE, isFavorite);
            return this;
        }

        public Builder setCreatedAt(String createdAt) {
            values.put(ContactsTable.CREATED_AT, createdAt);
            return this;
        }

        public Builder setUpdatedAt(String updatedAt) {
            values.put(ContactsTable.UPDATED_AT, updatedAt);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", isFavorite=" + isFavorite +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}*/
