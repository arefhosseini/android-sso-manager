package com.fearefull.ssomanager;

public enum Scope {
    READ("read"),
    WRITE("write");

    private String text;

    Scope(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}