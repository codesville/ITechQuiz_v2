package com.justbytes.itechquiz;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by basuso on 4/27/17.
 */

public class ITechQuizApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(getApplicationContext(), getString(R.string.adMobAppId));
    }
}
