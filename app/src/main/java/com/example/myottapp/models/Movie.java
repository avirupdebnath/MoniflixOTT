package com.example.myottapp.models;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("MovieId")
    private int MovieId;

    @SerializedName("MovieKey")
    private String MovieKey;

    @SerializedName("Title")
    private String Title;

    @SerializedName("Description")
    private String  Description;

    @SerializedName("LanguageId")
    int  LanguageId;

    @SerializedName("LanguageName")
    private String  LanguageName;

    @SerializedName("AgeRestriction")
    private String AgeRestriction;

    @SerializedName("RunTime")
    private int RunTime;

    @SerializedName("YearOfProduction")
    private int YearOfProduction;

    @SerializedName("Studio")
    private String Studio;

    @SerializedName("Origin")
    private String Origin;

    @SerializedName("Poster")
    private Poster Poster;

    @SerializedName("Trailer")
    private Trailer Trailer;

    @SerializedName("AccessUrls")
    private AccessUrls AccessUrls;

    @SerializedName("CastAndCrewInfo")
    private CastAndCrewInfo[] CastAndCrewInfo;

    @SerializedName("CategoryInfo")
    private CategoryInfo[] CategoryInfo;

    public int getMovieId() {
        return MovieId;
    }

    public void setMovieId(int movieId) {
        MovieId = movieId;
    }

    public String getMovieKey() {
        return MovieKey;
    }

    public void setMovieKey(String movieKey) {
        MovieKey = movieKey;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getLanguageI() {
        return LanguageId;
    }

    public void setLanguageI(int languageI) {
        LanguageId = languageI;
    }

    public String getLanguageName() {
        return LanguageName;
    }

    public void setLanguageName(String languageName) {
        LanguageName = languageName;
    }

    public String getAgeRestriction() {
        return AgeRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        AgeRestriction = ageRestriction;
    }

    public int getRunTime() {
        return RunTime;
    }

    public void setRunTime(int runTime) {
        RunTime = runTime;
    }

    public int getYearOfProduction() {
        return YearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        YearOfProduction = yearOfProduction;
    }

    public String getStudio() {
        return Studio;
    }

    public void setStudio(String studio) {
        Studio = studio;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public com.example.myottapp.models.Poster getPoster() {
        return Poster;
    }

    public void setPoster(com.example.myottapp.models.Poster poster) {
        Poster = poster;
    }

    public com.example.myottapp.models.Trailer getTrailer() {
        return Trailer;
    }

    public void setTrailer(com.example.myottapp.models.Trailer trailer) {
        Trailer = trailer;
    }

    public com.example.myottapp.models.AccessUrls getAccessUrls() {
        return AccessUrls;
    }

    public void setAccessUrls(com.example.myottapp.models.AccessUrls accessUrls) {
        AccessUrls = accessUrls;
    }

    public com.example.myottapp.models.CastAndCrewInfo[] getCastAndCrewInfo() {
        return CastAndCrewInfo;
    }

    public void setCastAndCrewInfo(com.example.myottapp.models.CastAndCrewInfo[] castAndCrewInfo) {
        CastAndCrewInfo = castAndCrewInfo;
    }

    public com.example.myottapp.models.CategoryInfo[] getCategoryInfo() {
        return CategoryInfo;
    }

    public void setCategoryInfo(com.example.myottapp.models.CategoryInfo[] categoryInfo) {
        CategoryInfo = categoryInfo;
    }

    @NonNull
    @Override
    public String toString() {
        return Title;
    }
}
