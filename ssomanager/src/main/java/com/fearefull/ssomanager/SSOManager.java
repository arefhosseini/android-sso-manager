package com.fearefull.ssomanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import static com.fearefull.ssomanager.Constants.SSO_PREFS_FILE_NAME;
import static com.fearefull.ssomanager.Constants.SSO_PREFS_NAME;

public class SSOManager {
    @SuppressLint("StaticFieldLeak")
    private static SSOManager INSTANCE;
    private final Context context;
    private String token;

    private SSOManager(Builder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("Builder cannot be null");
        }
        if (builder.context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        context = builder.context;
        SharedPreferences pref = context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        token = pref.getString(SSO_PREFS_NAME, null);
    }

    public static class Builder {
        private Context context;

        public Builder(){}

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public void build() throws IllegalArgumentException {
            INSTANCE = new SSOManager(this);
        }
    }


    public static SSOManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalArgumentException("SSO Manager is not created!");
        }
        return INSTANCE;
    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public boolean checkLogin() {
        return token != null && !token.isEmpty();
    }

    public void login(String phoneNumber) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                Context.MODE_PRIVATE).edit();
        editor.putString(SSO_PREFS_NAME, phoneNumber);
        editor.apply();
        token = phoneNumber;
        Log.d("SSO Manager", "User logged in");
    }

    public void logout() {
        SharedPreferences.Editor editor = context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                Context.MODE_PRIVATE).edit();
        editor.remove(SSO_PREFS_NAME);
        editor.apply();
        token = null;
        Log.d("SSO Manager", "User logged out");
    }

    public String getToken() {
        return token;
    }
}
