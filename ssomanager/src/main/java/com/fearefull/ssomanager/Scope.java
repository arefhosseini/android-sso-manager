package com.fearefull.ssomanager;

enum Scope {
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