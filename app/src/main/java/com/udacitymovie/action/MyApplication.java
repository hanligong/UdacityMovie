package com.udacitymovie.action;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by hanyuezi on 17/10/15.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
