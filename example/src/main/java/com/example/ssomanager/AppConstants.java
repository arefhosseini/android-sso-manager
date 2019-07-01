package com.example.ssomanager;

class AppConstants {
    // requests url
    static final String REQUEST_CODE_URL = "http://sso.billionsapp.solutions/auth-api/public/v1/phone/requestCode";
    static final String SIGNUP_BY_USERNAME_URL = "http://sso.billionsapp.solutions/auth-api/public/v1/signUp/username";
    static final String SIGNUP_BY_EMAIL_URL = "http://sso.billionsapp.solutions/auth-api/public/v1/signUp/email";
    static final String LOGIN_BY_PHONE_NUMBER_URL = "http://sso.billionsapp.solutions/auth-api/oauth/token";
    static final String LOGIN_BY_USERNAME_URL = "http://sso.billionsapp.solutions/auth-api/oauth/token";


    // client config for SSO
    static final String CLIENT_ID = "client-id";
    static final String CLIENT_SECRET = "client-secret";
}
