package com.shivamdev.contactsmanager.di.module;

import android.app.Application;

import dagger.Module;

/**
 * Created by shivam on 7/2/17.
 */

@Module
public class TestAppModule extends AppModule {

    public TestAppModule(Application application) {
        super(application);
    }
}
