package com.example.myottapp.models;
import java.io.Serializable;

public class Trailer implements Serializable {
    private int Id;
    private String TrailerKey;
    private AccessUrls Url;
    private boolean IsTeaser;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isTeaser() {
        return IsTeaser;
    }

    public void setTeaser(boolean teaser) {
        IsTeaser = teaser;
    }

    public String getTrailerKey() {
        return TrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        TrailerKey = trailerKey;
    }

    public AccessUrls getUrl() {
        return Url;
    }

    public void setUrl(AccessUrls url) {
        Url = url;
    }
}
