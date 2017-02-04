package com.shivamdev.contactsmanager.di.component;

import com.shivamdev.contactsmanager.common.base.BaseActivity;
import com.shivamdev.contactsmanager.di.scope.PerActivity;
import com.shivamdev.contactsmanager.di.module.ActivityModule;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.ui.detail.DetailActivity;
import com.shivamdev.contactsmanager.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by shivam on 2/2/17.
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);

    void inject(ContactsActivity activity);
}
