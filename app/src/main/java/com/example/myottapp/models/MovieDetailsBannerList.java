package com.example.myottapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MovieDetailsBannerList {
    private List<MovieDetailsBanner> movieDetailsBanners;

    public List<MovieDetailsBanner> getMovieDetailsBanners() {
        return movieDetailsBanners;
    }

    public void setMovieDetailsBanners(List<MovieDetailsBanner> movieDetailsBanners ) {
        this.movieDetailsBanners = movieDetailsBanners;
    }

    public static MovieDetailsBannerList parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieDetailsBannerList movieDetailsBannerList = gson.fromJson(response, MovieDetailsBannerList.class);
        return movieDetailsBannerList;
    }

    @Override
    public String toString() {
        return "MovieBasicInfoList{" +
                "movieBasicInfos=" + movieDetailsBanners+
                '}';
    }
}
