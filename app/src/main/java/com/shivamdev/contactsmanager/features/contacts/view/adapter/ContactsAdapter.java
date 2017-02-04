package com.shivamdev.contactsmanager.features.contacts.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by shivam on 1/2/17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {

    private List<ContactData> contactsList;
    private PublishSubject<ContactData> contactClickedSubject;

    public ContactsAdapter() {
        contactsList = new ArrayList<>();
        contactClickedSubject = PublishSubject.create();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent,
                false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        ContactData contact = contactsList.get(position);
        String name = contact.firstName + " " + contact.lastName;
        holder.tvName.setText(name);

        new AndroidUtils(holder.itemView.getContext())
                .loadProfileImageWithGlide(contact.profileUrl, holder.ivPicture);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public void updateContacts(List<ContactData> contactsData) {
        this.contactsList.clear();
        this.contactsList.addAll(contactsData);
        notifyDataSetChanged();
    }

    public Observable<ContactData> contactClicked() {
        return contactClickedSubject;
    }

    class ContactHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_contact_pic)
        ImageView ivPicture;

        @BindView(R.id.tv_contact_name)
        TextView tvName;

        ContactHolder(View item) {
            super(item);
            ButterKnife.bind(this, item);
            item.setOnClickListener(v -> {
                contactClickedSubject.onNext(contactsList.get(getLayoutPosition()));
            });
        }
    }

}