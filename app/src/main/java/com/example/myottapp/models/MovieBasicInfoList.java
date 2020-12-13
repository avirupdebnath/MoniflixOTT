package com.example.myottapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MovieBasicInfoList {
    private List<MovieBasicInfo> movieBasicInfos;

    public List<MovieBasicInfo> getMovieBasicInfos() {
        return movieBasicInfos;
    }

    public void setMovieBasicInfos(List<MovieBasicInfo> movieBasicInfos) {
        this.movieBasicInfos = movieBasicInfos;
    }

    public static MovieBasicInfoList parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieBasicInfoList movieBasicInfoList = gson.fromJson(response, MovieBasicInfoList.class);
        return movieBasicInfoList;
    }

    @Override
    public String toString() {
        return "MovieBasicInfoList{" +
                "movieBasicInfos=" + movieBasicInfos +
                '}';
    }
}
