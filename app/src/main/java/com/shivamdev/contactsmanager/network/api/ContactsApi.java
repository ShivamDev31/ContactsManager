package com.shivamdev.contactsmanager.network.api;


import com.shivamdev.contactsmanager.common.constants.Constants;
import com.shivamdev.contactsmanager.network.data.ContactData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by shivam on 1/2/17.
 */

public interface ContactsApi {

    @GET(Constants.Urls.ALL_CONTACTS)
    Observable<List<ContactData>> getContacts();

    @GET(Constants.Urls.CONTACT_DETAILS)
    Observable<ContactData> getContact(@Path("contactId") int contactId);

    @Multipart
    @POST(Constants.Urls.POST_NEW_CONTACT)
    Observable<Void> postContact(@Part("first_name") RequestBody firstName,
                                 @Part("last_name") RequestBody lastName,
                                 @Part("email") RequestBody email,
                                 @Part("phone_number") RequestBody phoneNumber,
                                 @Part MultipartBody.Part file);

    @POST(Constants.Urls.POST_NEW_CONTACT)
    Observable<Void> newContact(@Body ContactData contact);

    @PUT(Constants.Urls.UPDATE_CONTACT)
    Observable<ContactData> updateContact(@Path("contactId") int contactId, @Body ContactData contact);

}