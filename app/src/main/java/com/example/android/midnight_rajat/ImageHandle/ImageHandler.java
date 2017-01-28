package com.example.android.midnight_rajat.ImageHandle;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static java.security.AccessController.getContext;

/**
 * Created by rajat on 28-01-2017.
 */

public class ImageHandler {
    private final Context c;

    public ImageHandler(Context c){
        this.c = c;

    }

    public int dpToPx(int dp) {
        float density = c.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public Bitmap decodeUri(Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

    // *** HERE IMAGE FILE NAME IS SAME AS FOODNAME OR MAY BE USERNAME

    public String saveImage(Bitmap bitmap,String imgFileName) {
        ContextWrapper contextWrapper = new ContextWrapper(c);
        File directory = contextWrapper.getDir("foodData", Context.MODE_PRIVATE);

        File myPath = new File(directory, imgFileName + ".jpg");
        String imgPath = myPath.getAbsolutePath();

        FileOutputStream fos = null;
        try {
            if (bitmap == null) {
                return "";
            }
            fos = new FileOutputStream(myPath);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            //Toast.makeText(getContext(), "Image Added ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
        }
        return imgPath;
    }
}
