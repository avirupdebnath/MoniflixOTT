package com.example.myottapp.models;

import java.io.Serializable;

public class Poster implements Serializable {
    private int Id;
    private String PosterKey;
    //private String Extension;
    private String Url;
    private int Type;
    private int MasterType;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getMasterType() {
        return MasterType;
    }

    public void setMasterType(int masterType) {
        MasterType = masterType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPosterKey() {
        return PosterKey;
    }

    public void setPosterKey(String posterKey) {
        PosterKey = posterKey;
    }

//    public String getExtension() {
//        return Extension;
//    }
//
//    public void setExtension(String extension) {
//        Extension = extension;
//    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
