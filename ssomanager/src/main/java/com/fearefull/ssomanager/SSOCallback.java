package com.fearefull.ssomanager;


import java.io.IOException;
import okhttp3.Response;

public interface SSOCallback {
    public void onFailure(IOException e);
    public void onResponse(Response response);
}
