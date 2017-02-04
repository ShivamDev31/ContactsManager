package com.shivamdev.contactsmanager.di.component;

import android.app.Application;
import android.content.Context;

import com.shivamdev.contactsmanager.data.DataManager;
import com.shivamdev.contactsmanager.data.remote.MvpStarterService;
import com.shivamdev.contactsmanager.di.module.AppModule;
import com.shivamdev.contactsmanager.di.module.DataModule;
import com.shivamdev.contactsmanager.di.scope.ApplicationContext;
import com.shivamdev.contactsmanager.features.contacts.view.AddContactFragment;
import com.shivamdev.contactsmanager.features.contacts.view.ContactsFragment;
import com.shivamdev.contactsmanager.di.module.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shivam on 2/2/17.
 */

@Singleton
@Component(modules = {AppModule.class, ApiModule.class, DataModule.class})
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();

    MvpStarterService mvpBoilerplateService();

    void inject(ContactsFragment fragment);

    void inject(AddContactFragment fragment);
}
