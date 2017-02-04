package com.shivamdev.contactsmanager.ui.detail;


import com.shivamdev.contactsmanager.common.mvp.BasePresenter;
import com.shivamdev.contactsmanager.data.DataManager;
import com.shivamdev.contactsmanager.data.model.Pokemon;
import com.shivamdev.contactsmanager.data.model.Statistic;
import com.shivamdev.contactsmanager.di.scope.ConfigPersistent;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ConfigPersistent
public class DetailPresenter extends BasePresenter<DetailMvpView> {

    private final DataManager mDataManager;

    @Inject
    DetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
    }


    void getPokemon(String name) {
        checkViewAttached();
        getView().showProgress(true);
        Subscription subs = mDataManager.getPokemon(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<Pokemon>() {
                    @Override
                    public void onSuccess(Pokemon pokemon) {
                        getView().showProgress(false);
                        getView().showPokemon(pokemon);
                        for (Statistic statistic : pokemon.stats) {
                            getView().showStat(statistic);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        getView().showProgress(false);
                        getView().showError(error);
                    }
                });
        addSubscription(subs);
    }


}
