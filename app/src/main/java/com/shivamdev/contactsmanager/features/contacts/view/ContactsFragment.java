package com.shivamdev.contactsmanager.features.contacts.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.common.ContactsApplication;
import com.shivamdev.contactsmanager.common.base.BaseFragment;
import com.shivamdev.contactsmanager.features.contacts.presenter.ContactsFragmentPresenter;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactsFragmentScreen;
import com.shivamdev.contactsmanager.features.contacts.view.adapter.ContactsAdapter;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.network.data.ContactData;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by shivam on 1/2/17.
 */

public class ContactsFragment extends BaseFragment implements ContactsFragmentScreen {

    private static final String TAG = ContactsFragment.class.getSimpleName();

    private static final String ADD_TAG = "add_tag";

    @Inject
    ContactsFragmentPresenter presenter;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    @BindView(R.id.rv_contacts)
    RecyclerView rvContacts;

    @BindView(R.id.tv_contacts_error)
    TextView tvContactsError;

    private ContactsAdapter adapter;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void injectComponent() {
        //fragmentComponent().inject(this);
        ContactsApplication.getInstance().getComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        setupRecyclerView();
        setupSwipeRefresh();
        presenter.loadContactsFromApi();
        setupContactClicked();
    }

    private void setupSwipeRefresh() {
        srlRefresh.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        srlRefresh.setColorSchemeResources(R.color.white);
        srlRefresh.setOnRefreshListener(() -> {
            srlRefresh.setRefreshing(true);
            presenter.loadContactsFromApi();
            srlRefresh.setRefreshing(false);
        });
    }

    private void setupRecyclerView() {
        rvContacts.setHasFixedSize(true);
        rvContacts.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ContactsAdapter();
        rvContacts.setAdapter(adapter);
    }

    private void setupContactClicked() {
        presenter.contactClicked(adapter.contactClicked());
    }

    @OnClick(R.id.fab_add_contact)
    void addContact() {
        AddContactFragment addContactFragment = AddContactFragment.newInstance();
        ContactsActivity activity = (ContactsActivity) getActivity();
        activity.replaceFragment(addContactFragment, ADD_TAG);
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable, "Error while loading contacts : ");
        showSnack(getString(R.string.error_loading_contacts_msg));
        showErrorHideList(true);
    }

    @Override
    public void showLoader() {
        showProgressDialog(getString(R.string.loading_contacts));
    }

    @Override
    public void hideLoader() {
        hideProgressDialog();
    }

    @Override
    public void noContactsFound() {
        showErrorHideList(true);
    }

    @Override
    public void updateContacts(List<ContactData> contactsList) {
        showErrorHideList(false);
        adapter.updateContacts(contactsList);
    }

    private void showErrorHideList(boolean showError) {
        if (showError) {
            rvContacts.setVisibility(View.GONE);
            tvContactsError.setVisibility(View.VISIBLE);
        } else {
            rvContacts.setVisibility(View.VISIBLE);
            tvContactsError.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadContactDetailsFragment(ContactData contactData) {
        ContactsActivity activity = (ContactsActivity) getActivity();
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment
                .newInstance(contactData);
        activity.replaceFragment(contactDetailsFragment, TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}