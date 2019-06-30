package com.fearefull.ssomanager;

enum SSOParams {
    USERNAME("username"),
    PASSWORD("password"),
    EMAIL("email"),
    PHONE_NUMBER("phoneNumber"),
    FIRST_NAME_BY_USERNAME("firstname"),
    FIRST_NAME_BY_Email("firstName"),
    LAST_NAME("lastName");

    private String text;

    SSOParams(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
