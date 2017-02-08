package com.shivamdev.contactsmanager.util;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

/**
 * Created by shivam on 7/2/17.
 */

public class ResourceUtil {

    private static Resources resources = InstrumentationRegistry.getContext().getResources();

    public static String getString(int string) {
        return resources.getString(string);
    }

}