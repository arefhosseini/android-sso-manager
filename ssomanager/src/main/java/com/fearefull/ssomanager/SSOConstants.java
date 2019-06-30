package com.fearefull.ssomanager;

import com.android.volley.Request;

class SSOConstants {
    final static String SSO_PREFS_FILE_NAME = "SSO_PREFS_FILE";
    final static String SSO_PREFS_USER = "SSO_PREFS_USER";
    final static String SSO_MANAGER_TAG = "SSO_MANAGER";

    final static int GET = Request.Method.GET;
    final static int POST = Request.Method.POST;
    final static int DELETE = Request.Method.DELETE;

    final static String VERIFICATION_CODE = "verification_code";
    final static String PHONE_NUMBER = "phone_number";
    final static String GRANT_TYPE = "grant_type";
    final static String SCOPE = "scope";
    final static String CLIENT_ID = "client_id";
    final static String CLIENT_SECRET = "client_secret";

    final static String ACCESS_TOKEN = "access_token";
    final static String TOKEN_TYPE = "access_token";
    final static String REFRESH_TOKEN = "access_token";
    final static String EXPIRES_IN = "expires_in";
}
