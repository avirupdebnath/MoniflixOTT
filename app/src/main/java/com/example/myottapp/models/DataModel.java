package com.example.myottapp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataModel {

    public static final String carousalURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Carousel/GetBanners";

    public static final String searchURL="https://n74rb8wscl.execute-api.ap-south-1.amazonaws.com/api/Search/GetByKey/";

    public static final String movieByfilterURL="https://n74rb8wscl.execute-api.ap-south-1.amazonaws.com/api/Search/GetByFilter";
    public static final String movieCategoriesURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllCategories";
    public static final String movieDetailsByIdURL="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Movie/GetById/";
    public static List<Category> CategoriesList;
    public static final String carousalTAG="Carousal";
    public static String CATEGORY[];
    public static List<MovieBasicInfoList> allMovieBasicInfos;

    public static MovieDetailsBannerList carousalItems=new MovieDetailsBannerList();

    public static String CarousalResponse="";

    public static HashMap<String, String> responseCache=new HashMap<>();

}
