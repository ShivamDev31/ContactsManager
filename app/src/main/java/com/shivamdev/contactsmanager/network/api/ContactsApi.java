package com.shivamdev.contactsmanager.network.api;


import com.shivamdev.contactsmanager.common.constants.Constants;
import com.shivamdev.contactsmanager.network.data.ContactData;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by shivam on 1/2/17.
 */

public interface ContactsApi {

    @GET(Constants.Urls.ALL_CONTACTS)
    @Headers("Accept: application/json")
    Observable<List<ContactData>> getContacts();

    @POST(Constants.Urls.POST_NEW_CONTACT)
    @Headers("Accept: application/json")
    Observable<Void> postContact(@Body ContactData data);

}