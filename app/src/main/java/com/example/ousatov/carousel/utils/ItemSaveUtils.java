package com.example.ousatov.carousel.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ItemSaveUtils {

    public static final String KEY_PREFERENCES = "custom_demo_pref";

    private static ItemSaveUtils instance;
    private SharedPreferences sharedPreferences;

    private ItemSaveUtils(Context ctx) {
        String name = KEY_PREFERENCES;
        sharedPreferences = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static ItemSaveUtils getInstance(Context ctx) {
        if (instance == null) {
            instance = new ItemSaveUtils(ctx);
        }
        return instance;
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public void set(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
    public void set(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }
    public void set(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }


    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }


    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }


    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }
}
