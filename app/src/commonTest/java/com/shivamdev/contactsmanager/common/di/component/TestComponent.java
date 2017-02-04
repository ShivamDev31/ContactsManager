package com.shivamdev.contactsmanager.common.di.component;

import com.shivamdev.contactsmanager.common.di.module.AppTestModule;
import com.shivamdev.contactsmanager.di.component.AppComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppTestModule.class)
public interface TestComponent extends AppComponent {

}