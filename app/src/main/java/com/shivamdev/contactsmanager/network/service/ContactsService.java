package com.shivamdev.contactsmanager.network.service;

/**
 * Created by shivam on 1/2/17.
 */

public interface ContactsService {
    void getContacts();

    void getContactById(int id);

    void deleteContactById(int id);
}