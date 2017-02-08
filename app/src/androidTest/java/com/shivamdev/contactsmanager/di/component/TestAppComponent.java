package com.shivamdev.contactsmanager.di.component;

import com.shivamdev.contactsmanager.di.module.DataModule;
import com.shivamdev.contactsmanager.di.module.TestApiModule;
import com.shivamdev.contactsmanager.di.module.TestAppModule;
import com.shivamdev.contactsmanager.rules.OkHttpIdlingResourceRule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shivam on 7/2/17.
 */


@Singleton
@Component(modules = {TestAppModule.class, TestApiModule.class, DataModule.class})
public interface TestAppComponent extends AppComponent {

    void inject(OkHttpIdlingResourceRule okHttpIdlingResourceRule);
}