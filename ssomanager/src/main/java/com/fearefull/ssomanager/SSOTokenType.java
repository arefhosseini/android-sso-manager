package com.fearefull.ssomanager;

public enum SSOTokenType {
    BEARER("bearer");

    private String text;

    SSOTokenType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static SSOTokenType getSSOTokenType(String text) {
        switch (text) {
            case "bearer":
                return BEARER;
            default:
                return BEARER;
        }
    }
}
