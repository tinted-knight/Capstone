package com.t_knight.and.capstone.model.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FirstStartPreference {

    private SharedPreferences sp;
    private String key = "first_start";

    public FirstStartPreference(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isTrue() {
        return sp.getBoolean(key, true);
    }

    public void setFalse() {
        sp.edit().putBoolean(key, false).apply();
    }

}
