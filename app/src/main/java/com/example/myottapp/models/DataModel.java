package com.example.myottapp.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DataModel {

    public static final String carousalURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Carousel/GetBannersBYConfigId/";
    public static final String searchURL="https://n74rb8wscl.execute-api.ap-south-1.amazonaws.com/api/Search/GetByKey/";
    public static final String movieByfilterURL="https://n74rb8wscl.execute-api.ap-south-1.amazonaws.com/api/Search/GetByFilter";
    public static final String movieCategoriesURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllCategories";
    public static final String movieDetailsByIdURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Movie/GetById/";
    public static String accessToken="";
    public static List<Category> CategoriesList;
    public static final String carousalTAG="Carousal";


    public static String languageNames[]={"Kannada","Telugu","Malayalam","Tamil","Hindi","English","Korean","Russian"};

    public static MovieBasicInfoList carousalItems=new MovieBasicInfoList();

    public static int getCategoryIdByName(String categoryName){
        for (Category c: CategoriesList){
            if(c.getName().equals(categoryName))return c.getId();
        }
        return 1;
    }

    public static List<MovieBasicInfo> watchlist=new ArrayList<MovieBasicInfo>();

}
