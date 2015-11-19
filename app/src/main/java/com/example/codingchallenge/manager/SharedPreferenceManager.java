package com.example.codingchallenge.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String SHARED_PREFERENCE_KEY = "sharedPreferenceKey";
    private static final String IS_IT_FIRST_INSTALL = "isItFirstInstall";

    public static boolean isItFirstInstall(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(IS_IT_FIRST_INSTALL, true);
    }

    public static void updateInstallStatus(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putBoolean(IS_IT_FIRST_INSTALL, status);
        editor.commit();
    }

    public static void clearAllPreferences(Activity activity) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(activity);
        editor.clear().commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCE_KEY, Activity.MODE_PRIVATE).edit();
    }
}
