package com.shivamdev.contactsmanager.features.contacts.view_model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.shivamdev.contactsmanager.features.contacts.view.ContactDetailsFragment;
import com.shivamdev.contactsmanager.features.main.view.ContactsActivity;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.AndroidUtils;

/**
 * Created by shivam on 9/2/17.
 */

public class ContactViewModel extends BaseObservable {
    private static final String TAG = ContactViewModel.class.getSimpleName();

    private ContactData contact;
    private int position;

    public ContactViewModel(ContactData contactData, int position) {
        this.contact = contactData;
        this.position = position;
    }

    public int getContactId() {
        return contact.id;
    }

    public String getContactName() {
        return contact.firstName + " " + contact.lastName;
    }

    public String getProfileUrl() {
        return contact.profileUrl;
    }

    public int getFavouriteVisibility() {
        if (contact.isFavourite) {
            return position == 0 ? View.VISIBLE : View.GONE;

        }
        return View.GONE;

    }

    public View.OnClickListener onContactClick() {
        return v -> {
            ContactsActivity activity = (ContactsActivity) v.getContext();
            ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment
                    .newInstance(contact);
            activity.replaceFragment(contactDetailsFragment, TAG);
        };
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView ivProfilePic, String imageUrl) {
        new AndroidUtils(ivProfilePic.getContext())
                .loadProfileImageWithGlide(imageUrl, ivProfilePic);
    }
}