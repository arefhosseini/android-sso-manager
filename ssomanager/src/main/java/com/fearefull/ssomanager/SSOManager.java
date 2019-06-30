package com.fearefull.ssomanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import static com.fearefull.ssomanager.SSOConstants.SSO_PREFS_FILE_NAME;
import static com.fearefull.ssomanager.SSOConstants.SSO_PREFS_USER;
import static com.fearefull.ssomanager.SSOConstants.SSO_MANAGER_TAG;

public class SSOManager implements SSO {
    @SuppressLint("StaticFieldLeak")
    private static SSOManager INSTANCE;
    private final Context context;
    private final SSOClient ssoClient;
    private SSOUser ssoUser;
    private RequestQueue queue;

    private SSOManager(Builder builder) {
        if (builder == null)
            throw new IllegalArgumentException("Builder cannot be null");
        if (builder.context == null)
            throw new IllegalArgumentException("Context cannot be null");
        if (builder.ssoClient == null)
            throw new IllegalArgumentException("SSO Client cannot be null");
        context = builder.context;
        ssoClient = builder.ssoClient;

        Gson gson = new Gson();
        String json = getSharedPrefs().getString(SSO_PREFS_USER, "");
        ssoUser = gson.fromJson(json, SSOUser.class);
        if (ssoUser == null)
            ssoUser = new SSOUser();
        queue = Volley.newRequestQueue(context);
    }

    private SharedPreferences getSharedPrefs() {
        return context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return context.getSharedPreferences(SSO_PREFS_FILE_NAME,
                Context.MODE_PRIVATE).edit();
    }

    private SSOToken getOauthToken(String response) {
        long currentTimeStamp = System.currentTimeMillis()/1000;
        return new SSOToken("acc", "ref",
                currentTimeStamp, SSOTokenType.getSSOTokenType("bearer"));
    }

    private void saveUser(SSOToken token) {
        ssoUser.updateUserStatus(SSOUserStatus.USER_OK);
        ssoUser.updateToken(token);
        Gson gson = new Gson();
        String json = gson.toJson(ssoUser);
        SharedPreferences.Editor editor = getEditor();
        editor.putString(SSO_PREFS_USER, json);
        editor.apply();
    }

    private void saveUserByPhoneNumber(String phoneNumber, SSOToken token) {
        ssoUser.updatePhoneNumber(phoneNumber);
        saveUser(token);
        Log.d(SSO_MANAGER_TAG, "User logged in by phone number successfully");
    }

    private void saveUserByUsername(String username, String password, SSOToken token) {
        ssoUser.updateUsername(username);
        ssoUser.updatePassword(password);
        saveUser(token);
        Log.d(SSO_MANAGER_TAG, "User logged in by username successfully");
    }

    private void saveUserByEmail(String email, String password, SSOToken token) {
        ssoUser.updateEmail(email);
        ssoUser.updatePassword(password);
        saveUser(token);
        Log.d(SSO_MANAGER_TAG, "User logged in by email successfully");
    }

    private void removeUser() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(SSO_PREFS_USER);
        editor.apply();
        ssoUser = new SSOUser();
        Log.d(SSO_MANAGER_TAG, "User removed");
    }

    private void addToQueue(Request request) {
        queue.add(request);
    }

    public static SSOManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalArgumentException("SSO Manager is not created!");
        }
        return INSTANCE;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public SSOUserStatus checkLogin() {
        return ssoUser.getUserStatus();
    }

    @Override
    public void requestCode(String url, String phoneNumber, SSOCallback ssoCallback) {

    }

    @Override
    public void loginByPhoneNumber(String url, final String phoneNumber,
                                   final String verificationCode, final SSOCallback ssoCallback) {
        final int[] statusCode = new int[1];
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                saveUserByPhoneNumber(phoneNumber, getOauthToken(response));
                ssoCallback.onResponse(response, statusCode[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(SSO_MANAGER_TAG,"Error: " + error.toString());
                ssoCallback.onFailure(error, statusCode[0]);
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        addToQueue(stringRequest);
    }

    @Override
    public void loginByUsername(String url, String username, String password, SSOCallback ssoCallback) {

    }

    @Override
    public void loginByEmail(String url, String email, String password, SSOCallback ssoCallback) {

    }

    @Override
    public void logout() {
        removeUser();
    }

    @Override
    public SSOUser getUser() {
        return ssoUser;
    }

    @Override
    public void destroy() {
        if (queue != null)
            queue.cancelAll(SSO_MANAGER_TAG);
    }

    public static class Builder {
        private Context context;
        private SSOClient ssoClient;

        public Builder(){}

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setClient(String clientId, String clientSecret) {
            this.ssoClient = new SSOClient(clientId, clientSecret);
            return this;
        }

        public void build() throws IllegalArgumentException {
            INSTANCE = new SSOManager(this);
        }
    }
}
