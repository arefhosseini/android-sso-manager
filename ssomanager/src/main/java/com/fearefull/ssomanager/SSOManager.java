package com.fearefull.ssomanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.fearefull.ssomanager.Constants.SSO_PREFS_FILE_NAME;
import static com.fearefull.ssomanager.Constants.SSO_PREFS_NAME;

public class SSOManager {
    @SuppressLint("StaticFieldLeak")
    private static SSOManager INSTANCE;
    private final Context context;
    private String token;
    private SSOStatus ssoStatus;
    private OkHttpClient client;

    private SSOManager(Builder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("Builder cannot be null");
        }
        if (builder.context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        context = builder.context;
        client = new OkHttpClient();
        SharedPreferences pref = context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        token = pref.getString(SSO_PREFS_NAME, null);
        if (token == null || token.isEmpty())
            ssoStatus = SSOStatus.NOT_LOGGED_IN;
        else
            ssoStatus = SSOStatus.LOGGED_IN;
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

    public SSOStatus checkLogin() {
        return ssoStatus;
    }

    public void login(String url, final String phoneNumber, final SSOCallback ssoCallback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                Log.d("SSO Manager", "User logged in failed");
                ssoCallback.onFailure(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                SharedPreferences.Editor editor = context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                        Context.MODE_PRIVATE).edit();
                editor.putString(SSO_PREFS_NAME, phoneNumber);
                editor.apply();
                token = phoneNumber;
                ssoStatus = SSOStatus.LOGGED_IN;
                Log.d("SSO Manager", "User logged in successfully");
                ssoCallback.onResponse(response);
            }
        });
    }

    public void logout() {
        SharedPreferences.Editor editor = context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                Context.MODE_PRIVATE).edit();
        editor.remove(SSO_PREFS_NAME);
        editor.apply();
        token = null;
        ssoStatus = SSOStatus.NOT_LOGGED_IN;
        Log.d("SSO Manager", "User logged out");
    }

    public String getToken() {
        return token;
    }
}
