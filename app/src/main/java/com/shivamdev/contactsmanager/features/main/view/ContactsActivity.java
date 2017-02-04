package com.shivamdev.contactsmanager.features.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.common.base.BaseActivity;
import com.shivamdev.contactsmanager.features.contacts.view.ContactsFragment;
import com.shivamdev.contactsmanager.features.main.presenter.ContactsActivityPresenter;
import com.shivamdev.contactsmanager.features.main.screen.ContactsScreen;

import javax.inject.Inject;

import butterknife.BindView;

public class ContactsActivity extends BaseActivity implements ContactsScreen {

    private static final String TAG = ContactsActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ContactsActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachView(this);
        setToolbar(toolbar, getString(R.string.app_name), false);
        presenter.addContactsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void injectComponent() {
        activityComponent().inject(this);
    }


    @Override
    public void addContactsFragment() {
        ContactsFragment contactsFragment = ContactsFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_fragment, contactsFragment, TAG);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment contactsFragment = fragmentManager.findFragmentByTag(TAG);
        if (contactsFragment != null) {
            fragmentTransaction.hide(contactsFragment);
        }
        fragmentTransaction.add(R.id.ll_fragment, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void popBackStackImmediate() {
        getSupportFragmentManager().popBackStackImmediate();
    }
}
