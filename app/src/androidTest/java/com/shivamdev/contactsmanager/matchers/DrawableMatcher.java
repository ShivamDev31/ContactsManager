package com.shivamdev.contactsmanager.matchers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.shivamdev.contactsmanager.utils.StringUtils;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by shivam on 8/2/17.
 */

public class DrawableMatcher extends TypeSafeMatcher<View> {
    private int drawableId;
    private String resourceName;

    public DrawableMatcher(int drawableId) {
        super(View.class);
        this.drawableId = drawableId;
    }

    @Override
    protected boolean matchesSafely(View item) {
        if (!(item instanceof ImageView)){
            return false;
        }
        ImageView imageView = (ImageView) item;
        if (drawableId < 0) {
            return imageView.getDrawable() == null;
        }
        Resources resources = item.getContext().getResources();
        resourceName = resources.getResourceEntryName(drawableId);
        Drawable expectedDrawable = resources.getDrawable(drawableId);
        if (expectedDrawable == null) {
            return false;
        }
        VectorDrawable bitmapDrawActual = (VectorDrawable) imageView.getDrawable();
        Bitmap bitmapActual = getBitmap(bitmapDrawActual);
        Bitmap bitmapExpected = getBitmap(item.getContext(), drawableId);
        return bitmapActual.sameAs(bitmapExpected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id : ");
        description.appendValue(drawableId);
        if (!StringUtils.isEmpty(resourceName)) {
            description.appendText("[");
            description.appendValue(resourceName);
            description.appendText("]");
        }
    }

    private Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}