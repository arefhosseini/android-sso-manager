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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fearefull.ssomanager.SSOConstants.GET;
import static com.fearefull.ssomanager.SSOConstants.POST;
import static com.fearefull.ssomanager.SSOConstants.SSO_MANAGER_TAG;
import static com.fearefull.ssomanager.SSOConstants.SSO_PREFS_FILE_NAME;
import static com.fearefull.ssomanager.SSOConstants.SSO_PREFS_USER;

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
            ssoUser = new SSOUser.Builder().build();
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

    private SSOToken getOauthToken(JSONObject response) throws JSONException {
        long currentTimeStamp = System.currentTimeMillis()/1000;
        return new SSOToken(response.getString(SSOConstants.ACCESS_TOKEN),
                response.getString(SSOConstants.REFRESH_TOKEN),
                currentTimeStamp + response.getLong(SSOConstants.EXPIRES_IN),
                SSOTokenType.getSSOTokenType(response.getString(SSOConstants.TOKEN_TYPE)));
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
        updateUser(null, null, null, phoneNumber, null, null);
        saveUser(token);
        print("User logged in by phone number successfully");
    }

    private void saveUserByUsername(String username, String password, SSOToken token) {
        updateUser(username, password, null, null, null, null);
        saveUser(token);
        print("User logged in by username successfully");
    }

    private void saveUserByEmail(String email, String password, SSOToken token) {
        updateUser(null, password, email, null, null, null);
        saveUser(token);
        print("User logged in by email successfully");
    }

    private void removeUser() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(SSO_PREFS_USER);
        editor.apply();
        ssoUser.clear();
        print("User removed");
    }

    private void print(String message) {
        Log.d(SSO_MANAGER_TAG, message);
    }

    private void printError(String message) {
        Log.e(SSO_MANAGER_TAG, message);
    }

    private void addToQueue(Request request) {
        queue.add(request);
    }

    private void updateUser(String username, String password, String email, String phoneNumber,
                            String firstname, String lastname) {
        if (username != null)
            ssoUser.updateUsername(username);
        if (password != null)
            ssoUser.updatePassword(password);
        if (email != null)
            ssoUser.updateEmail(email);
        if (phoneNumber != null)
            ssoUser.updatePhoneNumber(phoneNumber);
        if (firstname != null)
            ssoUser.updateFirstname(firstname);
        if (lastname != null)
            ssoUser.updateLastname(lastname);
    }

    private void showSSOError(Exception error, SSOCallback ssoCallback,
                              String message, int statusCode) {
        ssoUser.clear();
        printError("Error in " + message + ": " + error.getMessage());
        ssoCallback.onFailure(error, error.getMessage(), statusCode);
    }

    public static SSOManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalArgumentException("SSO Manager is not created!");
        }
        return INSTANCE;
    }

    @Override
    public void signupByUsername(String url, String username, String password,
                                 String firstname, String lastname, final SSOCallback ssoCallback) {
        ssoUser.clear();
        updateUser(username, password, null, null, firstname, lastname);
        final int[] statusCode = new int[1];

        try {
            JsonObjectRequest request = new JsonObjectRequest(POST, url,
                    ssoUser.toSignupJsonByUsername(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            print("User signed up by username successfully");
                            ssoCallback.onResponse(response, statusCode[0]);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showSSOError(error, ssoCallback, "signup by username", statusCode[0]);
                        }
                    }) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    statusCode[0] = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };
            addToQueue(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signupByEmail(String url, String email, String password,
                              String firstname, String lastname, final SSOCallback ssoCallback) {
        ssoUser.clear();
        updateUser(email, password, email, null, firstname, lastname);
        final int[] statusCode = new int[1];
        try {
            JsonObjectRequest request = new JsonObjectRequest(POST, url,
                    ssoUser.toSignupJsonByEmail(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            print("User signed up by email successfully");
                            ssoCallback.onResponse(response, statusCode[0]);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showSSOError(error, ssoCallback, "signup by email", statusCode[0]);
                        }
                    }) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    statusCode[0] = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };
            addToQueue(request);
        } catch (JSONException e) {
            ssoCallback.onFailure(e, e.getMessage(), 400);
            e.printStackTrace();
        }
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
    public void requestCode(String url, final String phoneNumber, final SSOCallback ssoCallback) {
        final int[] statusCode = new int[1];
        url = url + "?" + SSOConstants.USER_PHONE_NUMBER + "=" + phoneNumber;

        JsonObjectRequest request = new JsonObjectRequest(GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ssoCallback.onResponse(response, statusCode[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showSSOError(error, ssoCallback, "request code", statusCode[0]);
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        addToQueue(request);
    }

    @Override
    public void loginByPhoneNumber(String url, final String phoneNumber,
                                   final String verificationCode, final SSOCallback ssoCallback) {

        final int[] statusCode = new int[1];
        url = url + "?" +
                SSOConstants.VERIFICATION_CODE + "=" + verificationCode + "&" +
                SSOConstants.PHONE_NUMBER + "=" + phoneNumber + "&" +
                SSOConstants.GRANT_TYPE + "=" + GrantType.PHONE.getText() + "&" +
                SSOConstants.SCOPE + "=" + Scope.READ.getText() + "&" +
                SSOConstants.CLIENT_ID + "=" + ssoClient.getClientId() + "&" +
                SSOConstants.CLIENT_SECRET + "=" + ssoClient.getClientSecret();

        JsonObjectRequest request = new JsonObjectRequest(POST, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            saveUserByPhoneNumber(phoneNumber, getOauthToken(response));
                        } catch (JSONException error) {
                            ssoUser.clear();
                            printError("Error in login by phone number: " + error.toString());
                            ssoCallback.onFailure(error, error.getMessage(), statusCode[0]);
                        }
                        ssoCallback.onResponse(response, statusCode[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showSSOError(error, ssoCallback, "login by phone number", statusCode[0]);
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        addToQueue(request);
    }

    @Override
    public void loginByUsername(String url, final String username, final String password,
                                final SSOCallback ssoCallback) {
        final int[] statusCode = new int[1];
        url = url + "?" +
                SSOConstants.USERNAME + "=" + username + "&" +
                SSOConstants.PASSWORD + "=" + password + "&" +
                SSOConstants.GRANT_TYPE + "=" + GrantType.PASSWORD.getText() + "&" +
                SSOConstants.SCOPE + "=" + Scope.READ.getText() + "&" +
                SSOConstants.CLIENT_ID + "=" + ssoClient.getClientId() + "&" +
                SSOConstants.CLIENT_SECRET + "=" + ssoClient.getClientSecret();
        JsonObjectRequest request = new JsonObjectRequest(POST, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            saveUserByUsername(username, password, getOauthToken(response));
                        } catch (JSONException error) {
                            showSSOError(error, ssoCallback, "login by phone username", statusCode[0]);
                        }
                        ssoCallback.onResponse(response, statusCode[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showSSOError(error, ssoCallback, "login by phone username", statusCode[0]);
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        addToQueue(request);
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
