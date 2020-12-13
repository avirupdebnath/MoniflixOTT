package com.example.myottapp.models;

import java.util.Arrays;

public class Category {
    int Id;
    String Name;
    String[] CategoryMapping;
    String[] UserPreferences;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String[] getCategoryMapping() {
        return CategoryMapping;
    }

    public void setCategoryMapping(String[] categoryMapping) {
        CategoryMapping = categoryMapping;
    }

    public String[] getUserPreferences() {
        return UserPreferences;
    }

    public void setUserPreferences(String[] userPreferences) {
        UserPreferences = userPreferences;
    }

    @Override
    public String toString() {
        return "Category{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", CategoryMapping=" + Arrays.toString(CategoryMapping) +
                ", UserPreferences=" + Arrays.toString(UserPreferences) +
                '}';
    }
}
