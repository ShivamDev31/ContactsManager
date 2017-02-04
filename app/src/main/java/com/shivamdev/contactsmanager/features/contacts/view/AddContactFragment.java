package com.shivamdev.contactsmanager.features.contacts.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.common.ContactsApplication;
import com.shivamdev.contactsmanager.common.base.BaseFragment;
import com.shivamdev.contactsmanager.common.constants.Constants;
import com.shivamdev.contactsmanager.features.contacts.presenter.AddContactPresenter;
import com.shivamdev.contactsmanager.features.contacts.screen.AddContactScreen;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.network.data.ContactData;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import timber.log.Timber;

import static com.shivamdev.contactsmanager.common.constants.Constants.REQUEST_CAMERA;
import static com.shivamdev.contactsmanager.common.constants.Constants.SELECT_GALLERY;

/**
 * Created by shivam on 3/2/17.
 */

@RuntimePermissions
public class AddContactFragment extends BaseFragment implements AddContactScreen {

    @Inject
    AddContactPresenter presenter;

    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;

    @BindView(R.id.et_first_name)
    EditText etFirstName;

    @BindView(R.id.et_last_name)
    EditText etLastName;

    @BindView(R.id.et_mobile_number)
    EditText etMobileNumber;

    @BindView(R.id.et_email_address)
    EditText etEmailAddress;


    public static AddContactFragment newInstance() {
        return new AddContactFragment();
    }

    @Override
    protected void injectComponent() {
        ContactsApplication.getInstance().getComponent().inject(this);
        //fragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_contact;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }

    @OnClick(R.id.iv_profile_pic)
    void selectImage() {
        showPictureSelectionDialog();
        //DialogFactory.showPictureSelectionDialog(getActivity(), getString(R.string.image_selection_title), Constants.IMAGE_SELECTION_OPTIONS);
    }

    @OnClick(R.id.b_save_contact)
    void saveContact() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phoneNumber = etMobileNumber.getText().toString();
        String email = etEmailAddress.getText().toString();

        ContactData contactData = new ContactData();
        contactData.firstName = firstName;
        contactData.lastName = lastName;
        contactData.phoneNumber = phoneNumber;
        contactData.email = email;

        presenter.saveContact(contactData);
    }

    @Override
    public void showFirstNameEmptyError() {
        showSnack(getString(R.string.error_first_name_empty));
        etFirstName.requestFocus();
    }

    @Override
    public void showLastNameEmptyError() {
        showSnack(getString(R.string.error_last_name_empty));
        etLastName.requestFocus();
    }

    @Override
    public void showPhoneNumberError() {
        showSnack(getString(R.string.error_phone_number_not_valid));
        etMobileNumber.requestFocus();
    }

    @Override
    public void showEmailError() {
        showSnack(getString(R.string.error_email_not_valid));
        etEmailAddress.requestFocus();
    }

    @Override
    public void showFirstNameLessThanThreeError() {
        showSnack(getString(R.string.error_first_name_less_than_three_chars));
        etFirstName.requestFocus();
    }

    @Override
    public void showErrorWhileAddingContact(Throwable e) {
        showSnack(getString(R.string.error_saving_new_contact));
        Timber.e(e, "Error while saving new contact");
    }

    @Override
    public void showLoader() {
        showProgressDialog(getString(R.string.saving_contact));
    }

    @Override
    public void hideLoader() {
        hideProgressDialog();
    }

    @Override
    public void contactSavedSuccessfully() {
        showSnack(getString(R.string.contact_posted_successfully));
        ContactsActivity activity = (ContactsActivity) getActivity();
        activity.popBackStackImmediate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    private void showPictureSelectionDialog() {
        CharSequence[] options = Constants.IMAGE_SELECTION_OPTIONS;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.image_selection_title));
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(options[0])) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            } else if (options[item].equals(options[1])) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_GALLERY);
            } else if (options[item].equals(options[2])) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    Timber.i(data.toString());
                } else {
                    showSnack(getString(R.string.error_while_capturing_image));
                }
                break;
            case SELECT_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    Timber.i(data.toString());
                } else {
                    showSnack(getString(R.string.error_while_getting_image));
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}