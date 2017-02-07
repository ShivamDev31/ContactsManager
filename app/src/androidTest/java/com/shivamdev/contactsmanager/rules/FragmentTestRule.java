package com.shivamdev.contactsmanager.rules;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;

import junit.framework.Assert;

/**
 * Created by shivam on 7/2/17.
 */

public class FragmentTestRule<F extends Fragment> extends ActivityTestRule<ContactsActivity> {

    private final Class<F> mFragmentClass;
    private F mFragment;

    public FragmentTestRule(final Class<F> fragmentClass) {
        super(ContactsActivity.class, true, false);
        mFragmentClass = fragmentClass;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();

        getActivity().runOnUiThread(() -> {
            try {
                //Instantiate and insert the fragment into the container layout
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                mFragment = mFragmentClass.newInstance();
                transaction.replace(R.id.ll_fragment, mFragment);
                transaction.commit();
            } catch (InstantiationException | IllegalAccessException e) {
                Assert.fail(String.format("%s: Could not insert %s into TestActivity: %s",
                        getClass().getSimpleName(),
                        mFragmentClass.getSimpleName(),
                        e.getMessage()));
            }
        });
    }

    public F getFragment() {
        return mFragment;
    }
}