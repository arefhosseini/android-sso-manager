package com.fearefull.ssomanager;

public class SSOUser {
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private SSOUserStatus ssoUserStatus;
    private SSOToken ssoToken;

    SSOUser(String phoneNumber, String email, String username, String password,
            SSOUserStatus ssoUserStatus, SSOToken ssoToken) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        if (ssoUserStatus != null)
            this.ssoUserStatus = ssoUserStatus;
        else
            this.ssoUserStatus = SSOUserStatus.NOT_USER;
        this.ssoToken = ssoToken;
    }

    SSOUser() {
        this.phoneNumber = null;
        this.email = null;
        this.username = null;
        this.password = null;
        this.ssoUserStatus = SSOUserStatus.NOT_USER;
        this.ssoToken = null;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SSOUserStatus getUserStatus() {
        return ssoUserStatus;
    }

    public SSOToken getToken() {
        return ssoToken;
    }

    void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    void updateUsername(String username) {
        this.username = username;
    }

    void updatePassword(String password) {
        this.password = password;
    }

    void updateEmail(String email) {
        this.email = email;
    }

    void updateToken(SSOToken ssoToken) {
        this.ssoToken = ssoToken;
    }

    void updateUserStatus(SSOUserStatus ssoUserStatus) {
        this.ssoUserStatus = ssoUserStatus;
    }

    public boolean hasUsername() {
        return username != null && !username.isEmpty();
    }

    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }

    public boolean hasPhoneNumber() {
        return phoneNumber != null && !phoneNumber.isEmpty();
    }

    public boolean hasPassword() {
        return password != null && !password.isEmpty();
    }
}
