package com.shivamdev.contactsmanager.runner;

import android.app.Application;
import android.content.Context;

import com.shivamdev.contactsmanager.TestApplication;

import io.appflate.restmock.android.RESTMockTestRunner;

/**
 * Created by shivam on 7/2/17.
 */

public class DemoTestRunner extends RESTMockTestRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //RESTMockServerStarter.startSync(new AndroidAssetsFileParser(getContext()), new AndroidLogger());
        return super.newApplication(cl, TestApplication.class.getName(), context);
    }
}