package com.example.myottapp.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AllLanguagesList{
    public List<Language> languages;

    public AllLanguagesList(List<Language> languages) {
        this.languages = languages;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public static AllLanguagesList parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        AllLanguagesList languageResponse = gson.fromJson(response, AllLanguagesList.class);
        return languageResponse;
    }

    public String[] getLanguagesArray(){
        String names[]=new String[this.getLanguages().size()];
        int count=0;
        for(Language language:languages){
            names[count++]=language.Name;
        }
        return names;
    }

    @NonNull
    @Override
    public String toString() {
        String allLanguages="";
        for (Language language: languages){
            allLanguages+=language.Name+"\n";
        }
        return allLanguages;
    }
}