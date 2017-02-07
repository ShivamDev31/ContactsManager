package com.shivamdev.contactsmanager.common.di.module;

import android.app.Application;
import android.content.Context;

import com.shivamdev.contactsmanager.data.DataManager;
import com.shivamdev.contactsmanager.data.remote.MvpStarterService;
import com.shivamdev.contactsmanager.di.module.NetworkModule;
import com.shivamdev.contactsmanager.di.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module(includes = {NetworkModule.class})
public class AppTestModule {
    private final Application mApplication;

    public AppTestModule(Application application) {
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

    /*************
     * MOCKS
     *************/

    @Provides
    @Singleton
    DataManager providesDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    MvpStarterService provideMvpBoilerplateService() {
        return mock(MvpStarterService.class);
    }

}
