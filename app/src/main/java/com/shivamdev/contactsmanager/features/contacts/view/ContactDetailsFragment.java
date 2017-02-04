package com.shivamdev.contactsmanager.features.contacts.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.common.base.BaseFragment;
import com.shivamdev.contactsmanager.features.contacts.presenter.ContactDetailsPresenter;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactDetailsScreen;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.AndroidUtils;
import com.shivamdev.contactsmanager.utils.Logger;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by shivam on 2/2/17.
 */

public class ContactDetailsFragment extends BaseFragment implements ContactDetailsScreen {

    private static final String CONTACT_DATA_KEY = "contact_data_key";

    @Inject
    ContactDetailsPresenter presenter;

    @BindView(R.id.iv_profile_pic)
    ImageView ivContactPic;

    @BindView(R.id.ll_name)
    LinearLayout llName;

    @BindView(R.id.ll_phone_number)
    LinearLayout llPhoneNumber;

    @BindView(R.id.ll_email_address)
    LinearLayout llEmailAddress;

    @BindView(R.id.iv_favorite)
    ImageView ivFavorite;

    @BindView(R.id.tv_contact_name)
    TextView tvContactName;

    @BindView(R.id.iv_call)
    ImageView ivCall;

    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;

    @BindView(R.id.iv_email)
    ImageView ivEmail;

    @BindView(R.id.tv_email_address)
    TextView tvEmail;

    @BindView(R.id.b_send_message)
    Button bSendMessage;

    @BindView(R.id.b_share_contact)
    Button bShareContact;

    private ContactData contactData;

    public static ContactDetailsFragment newInstance(ContactData contactData) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(CONTACT_DATA_KEY, Parcels.wrap(contactData));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_contact_details;
    }

    @Override
    protected void injectComponent() {
        fragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        contactData = Parcels.unwrap(getArguments().getParcelable(CONTACT_DATA_KEY));
        presenter.showContactDetailsOnUi(contactData);
    }

    @OnClick(R.id.iv_favorite)
    void favoriteClick() {
        Logger.toast("Clicked on favorite");
    }

    @OnClick(R.id.iv_call)
    void callNumber() {
        presenter.callNumber(contactData.phoneNumber);
    }

    @OnClick(R.id.iv_email)
    void emailContact() {
        presenter.emailContact(contactData.email);
    }

    @OnClick(R.id.b_send_message)
    void sendMessage() {
        presenter.sendMessage(contactData.phoneNumber);
        Logger.toast("Clicked on send message");
    }

    @OnClick(R.id.b_share_contact)
    void shareContact() {
        Logger.toast("Clicked on share contact");
    }

    @Override
    public void showProfilePic(String profileUrl) {
        new AndroidUtils(context)
                .loadProfileImageWithGlide(profileUrl, ivContactPic);
    }

    @Override
    public void setFavorite() {
        ivFavorite.setImageResource(R.drawable.ic_favorite_checked);
    }

    @Override
    public void resetFavorite() {
        ivFavorite.setImageResource(R.drawable.ic_favorite_unchecked);
    }

    @Override
    public void showContactName(String contactName) {
        llName.setVisibility(View.VISIBLE);
        tvContactName.setText(contactName);
    }

    @Override
    public void showPhoneNumber(String phoneNumber) {
        llPhoneNumber.setVisibility(View.VISIBLE);
        tvPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void hidePhoneNumber() {
        llPhoneNumber.setVisibility(View.GONE);
    }

    @Override
    public void showEmailAddress(String email) {
        llEmailAddress.setVisibility(View.VISIBLE);
        tvEmail.setText(email);
    }

    @Override
    public void hideEmailAddress() {
        llEmailAddress.setVisibility(View.GONE);
    }

    @Override
    public void dialPhoneNumber(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    @Override
    public void showInvalidNumberError(String phoneNumber) {
        showSnack(getString(R.string.error_invalid_phone_number, phoneNumber));
    }

    @Override
    public void composeEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        startActivity(emailIntent);
    }

    @Override
    public void showInvalidEmailError(String email) {
        showSnack(getString(R.string.error_invalid_email, email));
    }

    @Override
    public void composeSms(String phoneNumber) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.putExtra("address"  , phoneNumber);
        smsIntent.setType("vnd.android-dir/mms-sms");
        startActivity(smsIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}