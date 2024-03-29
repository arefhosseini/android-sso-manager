package com.example.ssomanager;

import android.app.Application;

import com.fearefull.ssomanager.SSOManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new SSOManager.Builder()
                .setContext(getApplicationContext())
                .setClient(AppConstants.CLIENT_ID, AppConstants.CLIENT_SECRET)
                .build();
    }
}
