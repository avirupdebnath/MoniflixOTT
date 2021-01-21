package com.example.myottapp.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataModel {

    public static final String carousalURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Carousel/GetBannersBYConfigId/";
    public static final String searchURL="https://n74rb8wscl.execute-api.ap-south-1.amazonaws.com/api/Search/GetByKey/";
    public static final String movieByfilterURL="https://n74rb8wscl.execute-api.ap-south-1.amazonaws.com/api/Search/GetByFilter";
    public static final String movieCategoriesURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllCategories";
    public static final String movieDetailsByIdURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Movie/GetById/";
    public static final String getAllLanguagesURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllLanguages";
    public static final String seriesDetailsByIdURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Series/GetById/";
    public static final String episodesBySeasonIdURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Series/GetEpisodesBySeasonId/";

    public static final String trackUserWatchHistoryURL="https://21rqkixlud.execute-api.ap-south-1.amazonaws.com/api/Track/GetWatchHistory";

    public static String accessToken="";
    public static List<Category> CategoriesList;
    public static final String carousalTAG="Carousal";

    public static int refreshTokenCount=0;

    public  static List<Language> staticLanguageList=new ArrayList<Language>();
    public static String languageNames[]={"Kannada","Telugu","Malayalam","Tamil","Hindi","English","Korean","Russian"};

    public static MovieBasicInfoList carousalItems=new MovieBasicInfoList();
    public static List<MovieBasicInfo> watchlist=new ArrayList<MovieBasicInfo>();

    public static int getCategoryIdByName(String categoryName){
        for (Category c: CategoriesList){
            if((c.getName()+" Movies").equals(categoryName))return c.getId();
            else if((c.getName()+" Series").equals(categoryName))return c.getId();
        }
        return 1;
    }

    public static void updateLanguageList(int[] idsToRemove){
        Iterator i=staticLanguageList.iterator();
        while(i.hasNext()){
            Language l= (Language) i.next();
            for(int id: idsToRemove){
                if(l.getId()==id){
                    i.remove();
                }
            }
        }
    }
}
