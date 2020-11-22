package com.example.myottapp.models;

import java.io.Serializable;

public class AccessUrls implements Serializable {
    private String Hls_High;
    private String Hls_Med;
    private String Hls_Low;

    public String getHls_High() {
        return Hls_High;
    }

    public void setHls_High(String hls_High) {
        Hls_High = hls_High;
    }

    public String getHls_Med() {
        return Hls_Med;
    }

    public void setHls_Med(String hls_Med) {
        Hls_Med = hls_Med;
    }

    public String getHls_Low() {
        return Hls_Low;
    }

    public void setHls_Low(String hls_Low) {
        Hls_Low = hls_Low;
    }
}
