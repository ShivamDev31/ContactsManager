package com.shivamdev.contactsmanager.features.contacts.view;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canelmas.let.AskPermission;
import com.canelmas.let.Let;
import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.common.ContactsApplication;
import com.shivamdev.contactsmanager.common.base.BaseFragment;
import com.shivamdev.contactsmanager.features.contacts.presenter.ContactDetailsPresenter;
import com.shivamdev.contactsmanager.features.contacts.screen.ContactDetailsScreen;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.AndroidUtils;

import org.parceler.Parcels;

import java.io.File;

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

    @BindView(R.id.iv_favourite)
    ImageView ivFavourite;

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

    private ContactData updatedContactData;

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
        ContactsApplication.getInstance().getComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        ContactData contactData = Parcels.unwrap(getArguments().getParcelable(CONTACT_DATA_KEY));
        presenter.getContactDetailsFromServer(contactData.id);
    }

    @OnClick(R.id.iv_favourite)
    void favouriteClick() {
        presenter.updateFavourite(updatedContactData);
    }

    @AskPermission(Manifest.permission.CALL_PHONE)
    @OnClick(R.id.iv_call)
    void callNumber() {
        presenter.callNumber(updatedContactData.phoneNumber);
    }

    @OnClick(R.id.iv_email)
    void emailContact() {
        presenter.emailContact(updatedContactData.email);
    }

    @OnClick(R.id.b_send_message)
    void sendMessage() {
        presenter.sendMessage(updatedContactData.phoneNumber);
    }

    @OnClick(R.id.b_share_contact)
    void shareContact() {
        presenter.shareContact();
    }

    @Override
    public void showError(Throwable e) {
        showSnack(getString(R.string.error_while_fetching_contact_details));
    }

    @Override
    public void updateContactData(ContactData data) {
        this.updatedContactData = data;
    }

    @Override
    public void showProfilePic(String profileUrl) {
        new AndroidUtils(context)
                .loadProfileImageWithGlide(profileUrl, ivContactPic);
    }

    @Override
    public void setFavourite() {
        ivFavourite.setImageResource(R.drawable.ic_favourite_checked);
    }

    @Override
    public void resetFavourite() {
        ivFavourite.setImageResource(R.drawable.ic_favourite_unchecked);
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
        emailIntent.setType("message/rfc822");
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            showSnack(getString(R.string.error_no_email_app));
        }
    }

    @Override
    public void showInvalidEmailError(String email) {
        showSnack(getString(R.string.error_invalid_email, email));
    }

    @Override
    public void composeSms(String phoneNumber) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                + phoneNumber));
        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(smsIntent);
    }

    @Override
    public void showLoader() {
        showProgressDialog(getString(R.string.fetching_contact_details));
    }

    @Override
    public void hideLoader() {
        hideProgressDialog();
    }

    @Override
    public void errorWhileUpdatingFavourite(Throwable e) {
        showSnack(getString(R.string.error_updating_favorite));
    }

    @Override
    public void showFavouriteLoader() {
        showProgressDialog(getString(R.string.updating_favorite));
    }

    @Override
    public void showShareContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.share_contact)
                .setItems(R.array.contact_share_options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            //via SMS
                            shareAsSMS();
                            break;
                        case 1:
                            shareAsVCF();
                            break;
                    }
                }).create().show();
    }

    private void shareAsSMS() {
        Intent shareSmsIntent = new Intent();
        shareSmsIntent.setAction(Intent.ACTION_SENDTO);
        shareSmsIntent.setData(Uri.parse("smsto:"));
        shareSmsIntent.putExtra("sms_body", "Name: " + updatedContactData.firstName
                + " " + updatedContactData.lastName + " \n" +
                "Phone Number: " + updatedContactData.phoneNumber + " \n" +
                "Email: " + updatedContactData.email + "\n");
        shareSmsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(shareSmsIntent);
    }

    @AskPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private void shareAsVCF() {
        File vcfFile = AndroidUtils.generateVCF(updatedContactData);
        Intent shareVcfIntent = new Intent();
        shareVcfIntent.setAction(Intent.ACTION_SEND);
        Uri contactUri = FileProvider.getUriForFile(context, context
                .getApplicationContext().getPackageName() + ".provider", vcfFile);
        shareVcfIntent.putExtra(Intent.EXTRA_STREAM, contactUri);
        shareVcfIntent.setType("text/x-vcard");
        shareVcfIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(shareVcfIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Let.handle(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}