package com.shivamdev.contactsmanager.di.module;


import com.shivamdev.contactsmanager.network.api.ContactsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by shivam on 1/2/17.
 */

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    ContactsApi contactsApi(Retrofit retrofit) {
        return retrofit.create(ContactsApi.class);
    }

}