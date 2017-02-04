package com.shivamdev.contactsmanager.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.shivamdev.contactsmanager.di.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shivam on 2/2/17.
 */

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment providesFragment() {
        return mFragment;
    }

    @Provides
    Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mFragment.getActivity();
    }

}