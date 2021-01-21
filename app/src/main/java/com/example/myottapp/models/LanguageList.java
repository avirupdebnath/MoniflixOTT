package com.example.myottapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

public class LanguageList implements Serializable {
    private List<Language> languages;

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
    public static LanguageList parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        LanguageList languageList = gson.fromJson(response, LanguageList.class);
        return languageList;
    }
}
