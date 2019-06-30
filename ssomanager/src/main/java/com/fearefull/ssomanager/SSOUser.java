package com.fearefull.ssomanager;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SSOUser {
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private SSOUserStatus ssoUserStatus;
    private SSOToken ssoToken;

    private SSOUser(Builder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    void updateFirstname(String firstname) {
        this.firstname = firstname;
    }

    void updateLastname(String lastname) {
        this.lastname = lastname;
    }

    void updateToken(SSOToken ssoToken) {
        this.ssoToken = ssoToken;
    }

    void updateUserStatus(SSOUserStatus ssoUserStatus) {
        this.ssoUserStatus = ssoUserStatus;
    }

    void clear() {
        this.phoneNumber = null;
        this.email = null;
        this.username = null;
        this.password = null;
        this.firstname = null;
        this.lastname = null;
        this.ssoUserStatus = SSOUserStatus.NOT_USER;
        this.ssoToken = null;
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

    JSONObject toSignupJsonByUsername() throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put(UserParams.USERNAME.getText(), username);
        jsonBody.put(UserParams.PASSWORD.getText(), password);
        jsonBody.put(UserParams.FIRST_NAME_BY_USERNAME.getText(), firstname);
        jsonBody.put(UserParams.LAST_NAME.getText(), lastname);

        return jsonBody;
    }

    JSONObject toSignupJsonByEmail() throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put(UserParams.USERNAME.getText(), username);
        jsonBody.put(UserParams.PASSWORD.getText(), password);
        jsonBody.put(UserParams.EMAIL.getText(), email);
        jsonBody.put(UserParams.FIRST_NAME_BY_Email.getText(), firstname);
        jsonBody.put(UserParams.LAST_NAME.getText(), lastname);

        return jsonBody;
    }

    static class Builder {
        private String username;
        private String password;
        private String email;
        private String phoneNumber;
        private String firstname;
        private String lastname;

        Builder() {}

        Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        Builder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        SSOUser build() {
            return new SSOUser(this);
        }
    }
}
