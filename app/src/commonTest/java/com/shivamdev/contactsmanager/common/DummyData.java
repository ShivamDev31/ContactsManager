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

    public static final String SINGLE_CONTACT_LIST_JSON = "[\n" +
            "  {\n" +
            "    \"id\": 111,\n" +
            "    \"first_name\": \"kkh\",\n" +
            "    \"last_name\": \"vvh\",\n" +
            "    \"profile_pic\": \"https://contacts-app.s3-ap-southeast-1.amazonaws.com/contacts/profile_pics/000/000/111/original/missing.png?1486226912\",\n" +
            "    \"url\": \"http://gojek-contacts-app.herokuapp.com/contacts/111.json\"\n" +
            "  }";

}