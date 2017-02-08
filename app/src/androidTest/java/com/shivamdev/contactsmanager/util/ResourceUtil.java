package com.shivamdev.contactsmanager.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

/**
 * Created by shivam on 7/2/17.
 */

public class ResourceUtil {


    public static String getString(int stringId) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(stringId);
    }

}