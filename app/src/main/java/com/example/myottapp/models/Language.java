package com.example.myottapp.models;

public class Language {
    int Id;
    String Name;
    String[] Content;
    String[] UserPreferences;

    public Language(int Id, String Name){
        this.Id=Id;
        this.Name=Name;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setContent(String[] content) {
        Content = content;
    }

    public void setUserPreferences(String[] userPreferences) {
        UserPreferences = userPreferences;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String[] getContent() {
        return Content;
    }

    public String[] getUserPreferences() {
        return UserPreferences;
    }

}
