package com.shivamdev.contactsmanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shivamdev.contactsmanager.R;
import com.shivamdev.contactsmanager.network.data.ContactData;
import com.shivamdev.contactsmanager.view.CircleTransform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public static File generateVCF(ContactData contact) {

        //Create a vcf file
        String filename = Environment.getExternalStorageDirectory() + "/generated.vcf";

        File vcfFile = new File(filename, "ContactsManager");
        FileWriter fw;
        try {
            fw = new FileWriter(vcfFile);
            fw.write("BEGIN:VCARD\r\n");
            fw.write("VERSION:3.0\r\n");
            fw.write("N:" + contact.lastName + ";" + contact.firstName + "\r\n");
            fw.write("FN:" + contact.firstName + " " + contact.lastName + "\r\n");
            fw.write("TEL;TYPE=HOME,VOICE:" + contact.phoneNumber + "\r\n");
            fw.write("EMAIL;TYPE=PREF,INTERNET:" + contact.email + "\r\n");
            fw.write("END:VCARD\r\n");
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return vcfFile;
    }

    public static File generateProfilePic() {
        File dir = new File(Environment.getExternalStorageDirectory(), "ContactsManager");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return new File(dir, "profile_pic.jpg");
    }

}