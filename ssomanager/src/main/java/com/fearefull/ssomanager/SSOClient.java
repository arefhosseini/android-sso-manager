package com.fearefull.ssomanager;

public class SSOClient {
    private String clientId;
    private String clientSecret;

    SSOClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
