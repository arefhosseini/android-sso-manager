package com.fearefull.ssomanager;

public enum GrantType {
    AUTHORIZE("authorization_code"),
    REFRESH("refresh_token"),
    PASSWORD("password"),
    PHONE("phone_verification");

    private String text;

    GrantType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}