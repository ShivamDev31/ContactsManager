package com.shivamdev.contactsmanager.rules;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.shivamdev.contactsmanager.common.ContactsApplication;
import com.shivamdev.contactsmanager.di.component.TestAppComponent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * Created by shivam on 7/2/17.
 */

public class OkHttpIdlingResourceRule implements TestRule {

    @Inject
    OkHttpClient okHttpClient;


    public OkHttpIdlingResourceRule() {
        ContactsApplication contactsApplication = ContactsApplication.getInstance();
        TestAppComponent testComponent = (TestAppComponent) contactsApplication.getComponent();
        testComponent.inject(this);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                IdlingResource idlingResource = OkHttp3IdlingResource
                        .create("okhttp", okHttpClient);
                Espresso.registerIdlingResources(idlingResource);

                base.evaluate();

                Espresso.unregisterIdlingResources(idlingResource);
            }
        };
    }
}
