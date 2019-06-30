package com.fearefull.ssomanager;

interface SSO {
    void showToast(String message);
    SSOUserStatus checkLogin();
    void requestCode(String url, String phoneNumber, SSOCallback ssoCallback);
    void loginByPhoneNumber(String url, String phoneNumber, String verificationCode, SSOCallback ssoCallback);
    void loginByUsername(String url, String username, String password, SSOCallback ssoCallback);
    void loginByEmail(String url, String email, String password, SSOCallback ssoCallback);
    void logout();
    SSOUser getUser();
    void destroy();
}
