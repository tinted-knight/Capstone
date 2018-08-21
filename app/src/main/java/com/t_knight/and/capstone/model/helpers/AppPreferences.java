package com.t_knight.and.capstone.model.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    private SharedPreferences sp;
    private final String key_first_start = "first_start";
    private final String key_version = "version";
    private final String key_current_card_id = "card_id";
    private final String key_max_card_id = "max_card_id";

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

    public void setCardId(int cardId) {
        sp.edit().putInt(key_current_card_id, cardId).apply();
    }

    public int getCardId() {
        return sp.getInt(key_current_card_id, 0);
    }

    public int getMaxId() {
        return sp.getInt(key_max_card_id, 0);
    }

    public void setMaxId(int id) {
        sp.edit().putInt(key_max_card_id, id).apply();
    }

}
