package com.example.myottapp.models;

import java.io.Serializable;

public class UserDetails implements Serializable {
    private String userName;
    private String accessToken;
    private String refreshToken;

    public UserDetails(String userName, String accessToken, String refreshToken) {
        this.userName = userName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }

    public String getUserName() {
        return userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
