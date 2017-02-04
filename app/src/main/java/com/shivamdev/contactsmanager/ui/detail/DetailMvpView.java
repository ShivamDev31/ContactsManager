package com.shivamdev.contactsmanager.ui.detail;

import com.shivamdev.contactsmanager.data.model.Pokemon;
import com.shivamdev.contactsmanager.data.model.Statistic;
import com.shivamdev.contactsmanager.common.mvp.MvpView;

public interface DetailMvpView extends MvpView {

    void showPokemon(Pokemon pokemon);

    void showStat(Statistic statistic);

    void showProgress(boolean show);

    void showError(Throwable error);

}