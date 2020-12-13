package com.example.myottapp.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class AllCategoriesList {

    public List<Category> categories;

    public AllCategoriesList(List<Category> categories) {
       this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public static AllCategoriesList parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        AllCategoriesList allCategoriesList = gson.fromJson(response, AllCategoriesList.class);
        return allCategoriesList;
    }

    public String[] getCategoryNameArray(){
        String names[]=new String[this.getCategories().size()];
        int count=0;
        for(Category category:categories){
           names[count++]=category.Name;
        }
        return names;
    }


    @Override
    public String toString() {
        String result="";
        for(Category category:categories){
            result+=category.Id+"\t"+category.Name+"\n";
        }
        return result;
    }
}
