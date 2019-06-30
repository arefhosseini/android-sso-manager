package com.fearefull.ssomanager;

class SSOClient {
    private String clientId;
    private String clientSecret;

    SSOClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    String getClientId() {
        return clientId;
    }

    String getClientSecret() {
        return clientSecret;
    }
}
