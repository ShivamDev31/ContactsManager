package com.shivamdev.contactsmanager.network.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.shivamdev.contactsmanager.common.constants.DbConstants.ContactsTable;
import com.shivamdev.contactsmanager.db.Db;

import org.parceler.Parcel;

import rx.functions.Func1;

/**
 * Created by shivam on 1/2/17.
 */

@Parcel
public class ContactData implements Comparable<ContactData> {

    @SerializedName("id")
    public int id;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("email")
    public String email;

    @SerializedName("phone_number")
    public String phoneNumber;

    @SerializedName("profile_pic")
    public String profileUrl;

    @SerializedName("favorite")
    public boolean isFavorite;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;

    public static final Func1<Cursor, ContactData> MAPPER = cursor -> {
        ContactData contact = new ContactData();
        contact.id = Db.getInt(cursor, ContactsTable.CONTACT_ID);
        contact.firstName = Db.getString(cursor, ContactsTable.FIRST_NAME);
        contact.lastName = Db.getString(cursor, ContactsTable.LAST_NAME);
        contact.email = Db.getString(cursor, ContactsTable.EMAIL);
        contact.phoneNumber = Db.getString(cursor, ContactsTable.PHONE_NUMBER);
        contact.profileUrl = Db.getString(cursor, ContactsTable.PROFILE_URL);
        contact.isFavorite = Db.getBoolean(cursor, ContactsTable.IS_FAVORITE);
        contact.createdAt = Db.getString(cursor, ContactsTable.CREATED_AT);
        contact.updatedAt = Db.getString(cursor, ContactsTable.UPDATED_AT);
        return contact;
    };

    public static final class Builder {

        private final ContentValues values = new ContentValues();

        public Builder(ContactData contact) {
            values.put(ContactsTable.CONTACT_ID, contact.id);
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
                "contactId=" + id +
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

    @Override
    public int compareTo(@NonNull ContactData data) {
        return this.firstName.compareTo(data.firstName);
    }
}