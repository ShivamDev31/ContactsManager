package com.shivamdev.contactsmanager.di.module;

import android.app.Application;
import android.content.Context;

import com.shivamdev.contactsmanager.di.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shivam on 7/2/17.
 */

@Module
public class TestAppModule {

    private final Application mApplication;

    public TestAppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

}
