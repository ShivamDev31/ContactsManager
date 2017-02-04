/*
package com.shivamdev.contactsmanager;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.shivamdev.contactsmanager.di.component.AppComponent;
import com.shivamdev.contactsmanager.di.component.DaggerAppComponent;
import com.shivamdev.contactsmanager.di.module.AppModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class MvpStarterApplication extends Application {

    AppComponent mApplicationComponent;

    public static MvpStarterApplication get(Context context) {
        return (MvpStarterApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
        }
    }

    public AppComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(AppComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
*/
