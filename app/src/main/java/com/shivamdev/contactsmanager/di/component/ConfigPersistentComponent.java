package com.shivamdev.contactsmanager.di.component;

import com.shivamdev.contactsmanager.common.base.BaseActivity;
import com.shivamdev.contactsmanager.common.base.BaseFragment;
import com.shivamdev.contactsmanager.di.scope.ConfigPersistent;
import com.shivamdev.contactsmanager.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by shivam on 2/2/17.
 */

/**
 * A dagger component that will live during the lifecycle of an Activity or Fragment but it won't
 * be destroy during configuration changes. Check {@link BaseActivity} and {@link BaseFragment} to
 * see how this components survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = AppComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}
