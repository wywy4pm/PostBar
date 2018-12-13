package com.arun.bar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by WY on 2017/5/7.
 */
public class SharedPreferencesUtils {
    private static final String PATH_USER = "user";
    private static final String PATH_CONFIG = "config";
    private static final String KEY_USER_UID = "uid";
    public static final String KEY_USER_BAR = "user_bar";
    public static final String KEY_USER_NICK_NAME = "user_nick_name";
    public static final String KEY_USER_BAR_ID = "user_bar_id";

    public static void saveUid(Context context, String uid) {
        if (!TextUtils.isEmpty(uid)) {
            SharedPreferencesUtils.setUid(context, uid);
        }
    }

    public static void setUid(Context context, String uid) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            if (editor != null) {
                editor.putString(KEY_USER_UID, uid);
                editor.apply();
            }
        }
    }

    public static String getUid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE);
        String uid = "";
        if (preferences != null) {
            uid = preferences.getString(KEY_USER_UID, "");
        }
        return uid;
    }

    public static void setConfigString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_CONFIG, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            if (editor != null) {
                editor.putString(key, value);
                editor.apply();
            }
        }
    }

    public static String getConfigString(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_CONFIG, Context.MODE_PRIVATE);
        String value = "";
        if (preferences != null) {
            value = preferences.getString(key, "");
        }
        return value;
    }

    public static void setConfigLong(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_CONFIG, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            if (editor != null) {
                editor.putLong(key, value);
                editor.apply();
            }
        }
    }

    public static long getConfigLong(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_CONFIG, Context.MODE_PRIVATE);
        long value = 0;
        if (preferences != null) {
            value = preferences.getLong(key, 0);
        }
        return value;
    }

    public static void setConfigInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_CONFIG, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            if (editor != null) {
                editor.putInt(key, value);
                editor.apply();
            }
        }
    }

    public static int getConfigInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(PATH_CONFIG, Context.MODE_PRIVATE);
        int value = 0;
        if (preferences != null) {
            value = preferences.getInt(key, 0);
        }
        return value;
    }

}
