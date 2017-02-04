package com.shivamdev.contactsmanager.common.mvp;

/**
 * Created by shivam on 1/2/17.
 */

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
