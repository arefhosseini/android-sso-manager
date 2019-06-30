package com.fearefull.ssomanager;

import org.json.JSONObject;

public interface SSOCallback {
    void onFailure(Exception error, int statusCode);
    void onResponse(JSONObject response, int statusCode);
}
