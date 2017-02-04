package com.shivamdev.contactsmanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.view.CircleTransform;

/**
 * Created by shivam on 1/2/17.
 */

public class AndroidUtils {

    private Context context;

    public AndroidUtils(Context context) {
        this.context = context;
    }

    public void loadProfileImageWithGlide(String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl)
                .bitmapTransform(new CircleTransform(context))
                .placeholder(R.drawable.ic_person).into(imageView);
    }

    public void loadLocalProfileImageWithGlide(Uri imageUri, ImageView imageView) {
        Glide.with(context).load(imageUri)
                .bitmapTransform(new CircleTransform(context))
                .placeholder(R.drawable.ic_person).into(imageView);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}