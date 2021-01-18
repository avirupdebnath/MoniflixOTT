package com.example.myottapp.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MovieBasicInfo implements Serializable {
    private int Id;
    private int Type;
    private String Title;
    private String PosterUrl;
    private int TierId;
    private String Description;
    private int  MasterType;
    private String AgeRestriction;
    private int RunTime;
    private int YearOfProduction;
    private String Rating;
    //private int RegionOfOrigin;
    //private int[] Categorys;
    //private CastAndCrewInfo CastAndCrew;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPosterUrl() {
        return PosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        PosterUrl = posterUrl;
    }

    public int getTierId() {
        return TierId;
    }

    public void setTierId(int tierId) {
        TierId = tierId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getMasterType() {
        return MasterType;
    }

    public void setMasterType(int masterType) {
        MasterType = masterType;
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

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getId()+" "+this.getPosterUrl();
    }
}
