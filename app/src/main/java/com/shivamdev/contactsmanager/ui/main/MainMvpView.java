package com.shivamdev.contactsmanager.ui.main;

import java.util.List;

import com.shivamdev.contactsmanager.common.mvp.MvpView;

public interface MainMvpView extends MvpView {

    void showPokemon(List<String> pokemon);

    void showProgress(boolean show);

    void showError(Throwable error);

}