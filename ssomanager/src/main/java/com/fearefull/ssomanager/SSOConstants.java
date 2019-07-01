package com.fearefull.ssomanager;

import com.android.volley.Request;

class SSOConstants {
    // shared preference parameters
    final static String SSO_PREFS_FILE_NAME = "SSO_PREFS_FILE";
    final static String SSO_PREFS_USER = "SSO_PREFS_USER";

    // sso manager tag for logging
    final static String SSO_MANAGER_TAG = "SSO_MANAGER";

    // request methods
    final static int GET = Request.Method.GET;
    final static int POST = Request.Method.POST;
    final static int DELETE = Request.Method.DELETE;

    // body parameters for login request
    final static String VERIFICATION_CODE = "verification_code";
    final static String PHONE_NUMBER = "phone_number";
    final static String GRANT_TYPE = "grant_type";
    final static String SCOPE = "scope";
    final static String CLIENT_ID = "client_id";
    final static String CLIENT_SECRET = "client_secret";

    // token parameters for receiving token
    final static String ACCESS_TOKEN = "access_token";
    final static String TOKEN_TYPE = "access_token";
    final static String REFRESH_TOKEN = "access_token";
    final static String EXPIRES_IN = "expires_in";


    // user parameters for sending request
    final static String USERNAME = "username";
    final static String PASSWORD = "password";
    final static String EMAIL = "email";
    final static String USER_PHONE_NUMBER = "phoneNumber";
    final static String FIRST_NAME_BY_USERNAME = "firstname";
    final static String FIRST_NAME_BY_Email = "firstName";
    final static String LAST_NAME = "lastName";
}
