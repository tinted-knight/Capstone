package com.t_knight.and.capstone.model.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FirstStartPreference {

    private SharedPreferences sp;

    public FirstStartPreference(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getValue() {
        String key = "first_start";
        return sp.getBoolean(key, true);
    }

}
