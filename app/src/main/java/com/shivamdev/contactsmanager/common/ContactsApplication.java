package com.shivamdev.contactsmanager.common;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.shivamdev.contactsmanager.BuildConfig;
import com.shivamdev.contactsmanager.di.component.AppComponent;
import com.shivamdev.contactsmanager.di.component.DaggerAppComponent;
import com.shivamdev.contactsmanager.di.module.AppModule;

import timber.log.Timber;

/**
 * Created by shivam on 1/2/17.
 */

public class ContactsApplication extends Application {

    private static ContactsApplication instance;
    private AppComponent appComponent;

    public static ContactsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupComponent();
        if (BuildConfig.DEBUG) {
            setupTimber();
            setupStetho();
        }
    }

    private void setupComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
    }

    private void setupStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void setupTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    public AppComponent getComponent() {
        return appComponent;
    }
}