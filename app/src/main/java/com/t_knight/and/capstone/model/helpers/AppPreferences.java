package com.t_knight.and.capstone.model.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    private SharedPreferences sp;
    private final String key_first_start = "first_start";
    private final String key_version = "version";

    public AppPreferences(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirstStart() {
        return sp.getBoolean(key_first_start, true);
    }

    public void setFirstStartFalse() {
        sp.edit().putBoolean(key_first_start, false).apply();
    }

    private int getVersion() {
        return sp.getInt(key_version, 0);
    }

    public boolean isNewerVersion(int version) {
        return getVersion() < version;
    }

    public void setVersion(int value) {
        sp.edit().putInt(key_version, value).apply();
    }
}
