package com.example.myottapp.models;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    private boolean IsEligible;
    private int MovieId;
    private String MovieKey;
    private String Title;
    private String  Description;
    private int  LanguageId;
    private String  LanguageName;
    private int TierId;
    private String TierName;
    private String AgeRestriction;
    private int RunTime;
    private int YearOfProduction;
    private String Studio;
    private String Origin;
    private Poster Poster;
    private Trailer Trailer;
    private Subtitle []Subtitle;
    private AccessUrls AccessUrls;
    private CastAndCrewInfo[] CastAndCrewInfo;
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

    public int getLanguageId() {
        return LanguageId;
    }

    public void setLanguageId(int languageI) {
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

    public boolean isEligible() {
        return IsEligible;
    }

    public void setEligible(boolean eligible) {
        IsEligible = eligible;
    }

    public int getTierId() {
        return TierId;
    }

    public void setTierId(int tierId) {
        TierId = tierId;
    }

    public String getTierName() {
        return TierName;
    }

    public void setTierName(String tierName) {
        TierName = tierName;
    }

    public com.example.myottapp.models.Subtitle[] getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(com.example.myottapp.models.Subtitle[] subtitle) {
        Subtitle = subtitle;
    }

    @NonNull
    @Override
    public String toString() {
        return Title;
    }
}
