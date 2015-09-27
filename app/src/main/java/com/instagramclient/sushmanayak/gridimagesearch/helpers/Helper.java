package com.instagramclient.sushmanayak.gridimagesearch.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SushmaNayak on 9/22/2015.
 */
public class Helper {

    public static final String IMG_SIZE = "ImageSize";
    public static final String IMG_TYPE = "ImageType";
    public static final String IMG_COLOR = "ImageColor";
    public static final String IMG_SITE = "ImageSite";

    public static void Helper()
    {fixMediaDir();}

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String GetPreference(Context context, String key, String defValue) {
        SharedPreferences pref = context.getSharedPreferences(key, 0);
        return pref.getString(key, defValue);
    }

    public static void SetPreference(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value).commit();
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public static Uri getLocalBitmapUri(Context context, ImageView imageView) {

        Drawable mDrawable = imageView.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                mBitmap, "Image Description", null);

        Uri uri = Uri.parse(path);
        return uri;
    }

    public static void fixMediaDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        if (sdcard == null) { return; }
        File dcim = new File(sdcard, "DCIM");
        if (dcim == null) { return; }
        File camera = new File(dcim, "Camera");
        if (camera.exists()) { return; }
        camera.mkdir();
    }
}
