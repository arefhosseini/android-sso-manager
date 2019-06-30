package com.fearefull.ssomanager;

public interface SSOCallback {
    void onFailure(Exception e, int statusCode);
    void onResponse(String response, int statusCode);
}
