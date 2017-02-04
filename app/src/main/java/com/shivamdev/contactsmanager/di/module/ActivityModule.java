package com.shivamdev.contactsmanager.di.module;

import android.app.Activity;
import android.content.Context;

import com.shivamdev.contactsmanager.di.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shivam on 2/2/17.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
