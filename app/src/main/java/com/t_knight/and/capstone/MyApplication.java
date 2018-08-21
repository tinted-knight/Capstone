package com.t_knight.and.capstone;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import timber.log.Timber;

public class MyApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}
