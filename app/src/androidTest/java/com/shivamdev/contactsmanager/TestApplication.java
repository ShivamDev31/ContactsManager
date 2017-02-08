package com.shivamdev.contactsmanager;

import com.shivamdev.contactsmanager.common.ContactsApplication;
import com.shivamdev.contactsmanager.di.component.AppComponent;
import com.shivamdev.contactsmanager.di.component.DaggerTestAppComponent;
import com.shivamdev.contactsmanager.di.module.TestAppModule;

/**
 * Created by shivam on 7/2/17.
 */

public class TestApplication extends ContactsApplication {

    @Override
    public AppComponent getComponent() {
        return DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule(this))
                .build();
    }
}
