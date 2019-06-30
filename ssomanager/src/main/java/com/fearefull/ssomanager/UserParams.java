package com.fearefull.ssomanager;

public enum UserParams {
    USERNAME("username"),
    PASSWORD("password"),
    EMAIL("email"),
    PHONE_NUMBER("phoneNumber"),
    FIRST_NAME_BY_USERNAME("firstname"),
    FIRST_NAME_BY_Email("firstName"),
    LAST_NAME("lastName");

    private String text;

    UserParams(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
