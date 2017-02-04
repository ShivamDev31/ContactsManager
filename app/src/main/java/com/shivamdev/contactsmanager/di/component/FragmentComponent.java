package com.shivamdev.contactsmanager.di.component;

import com.shivamdev.contactsmanager.di.module.FragmentModule;
import com.shivamdev.contactsmanager.di.scope.PerFragment;

import dagger.Subcomponent;

/**
 * Created by shivam on 2/2/17.
 */

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    //void inject(ContactsFragment fragment);

    //void inject(ContactDetailsFragment fragment);

    //void inject(AddContactFragment fragment);
}