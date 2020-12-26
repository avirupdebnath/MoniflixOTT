package com.example.myottapp.models;

import java.io.Serializable;

public class Subtitle implements Serializable {
    int Id;
    String ContentKey;
    int LanguageId;
    String LanguageName;
    String  Url;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContentKey() {
        return ContentKey;
    }

    public void setContentKey(String contentKey) {
        ContentKey = contentKey;
    }

    public int getLanguageId() {
        return LanguageId;
    }

    public void setLanguageId(int languageId) {
        LanguageId = languageId;
    }

    public String getLanguageName() {
        return LanguageName;
    }

    public void setLanguageName(String languageName) {
        LanguageName = languageName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
