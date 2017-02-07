package com.shivamdev.contactsmanager.db;


import android.database.sqlite.SQLiteDatabase;

import com.shivamdev.contactsmanager.common.constants.DbConstants;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

/**
 * Created by shivam on 10/12/16.
 */

public class LocalDataStore {

    private final BriteDatabase db;

    public LocalDataStore(BriteDatabase db) {
        this.db = db;
    }

    public void insertAllContactsIntoDb(List<ContactData> contactsList) {
        db.delete(DbConstants.CONTACTS_TABLE, null);
        for (ContactData contact : contactsList) {
            createContact(contact);
        }
    }

    public void createContact(ContactData contact) {
        if (db.insert(DbConstants.CONTACTS_TABLE, new ContactData.Builder(contact).build(),
                SQLiteDatabase.CONFLICT_REPLACE) > 0) {
            return;
        }

        throw new IllegalStateException("Cannot insert data : " + contact.toString());
    }

    /*public Observable<Contact> getVenue(String venueId) {
        return db.createQuery(DbConstants.VENUE_TABLE, DbHelper.GET_COMMENTS_BY_VENUE_ID_QUERY, venueId)
                .mapToOne(Venue.MAPPER);
    }*/

    public Observable<List<ContactData>> getAllContacts() {
        return db.createQuery(DbConstants.CONTACTS_TABLE,
                DbHelper.GET_ALL_CONTACTS)
                .mapToList(ContactData.MAPPER);
    }

    public void updateContact(ContactData contact) {
        db.update(DbConstants.CONTACTS_TABLE, new ContactData.Builder(contact).build(),
                DbConstants.ContactsTable.CONTACT_ID + " = " + contact.id);
    }
}