package com.shivamdev.contactsmanager.di.module;

import dagger.Module;
import io.appflate.restmock.RESTMockServer;

/**
 * Created by shivam on 8/2/17.
 */

@Module
public class TestNetworkModule extends NetworkModule {

    @Override
    public String getBaseUrl() {
        return RESTMockServer.getUrl();
    }
}

