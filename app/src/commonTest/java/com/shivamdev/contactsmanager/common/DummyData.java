package com.shivamdev.contactsmanager.common;

import com.shivamdev.contactsmanager.network.data.ContactData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivam on 6/2/17.
 */

public class DummyData {

    private static final List<ContactData> contactsList = new ArrayList<>();

    public static void getAllContactDetails(ContactData contactData) {
        contactData.id = 111;
        contactData.firstName = "Shivam";
        contactData.lastName = "Chopra";
        contactData.phoneNumber = "9696969696";
        contactData.email = "abc@gmail.com";
        contactData.profileUrl = "/img.png";
        contactData.isFavourite = true;
    }

    public static void setContactDetails(ContactData contactData, String fName, String lName,
                                         String mobile, String email) {
        contactData.firstName = fName;
        contactData.lastName = lName;
        contactData.phoneNumber = mobile;
        contactData.email = email;
    }

    public static List<ContactData> getContactsList(ContactData contactData) {
        contactsList.clear();
        getAllContactDetails(contactData);
        contactsList.add(contactData);
        return contactsList;
    }
}