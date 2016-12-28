package com.example.ousatov.carousel.utils;

import android.app.Activity;
import android.graphics.Color;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ousatov.carousel.R;

public class DialogAlertUtils {


    static public void showDeleteItemAlert(final Activity activity, MaterialDialog.ButtonCallback callback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);
        builder.title("")
                .content("If you click OK then top card will be delete")
                .positiveText("OK")
                .negativeText("Cancel")
                .negativeColorRes(R.color.colorAccent)
                .positiveColorRes(R.color.colorAccent)
                .titleColor(Color.BLACK)
                .build().show();
        builder.callback(callback);
    }
}
