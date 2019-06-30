package com.fearefull.ssomanager;

public class SSOToken {


    private String accessToken;
    private String refreshToken;
    private long expiresTime;
    private SSOTokenType ssoTokenType;

    SSOToken(String accessToken, String refreshToken, long expiresTime, SSOTokenType ssoTokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresTime = expiresTime;
        this.ssoTokenType = ssoTokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public SSOTokenType getSsoTokenType() {
        return ssoTokenType;
    }
}
