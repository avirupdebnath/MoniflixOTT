package com.example.myottapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Seasons implements Serializable {
     int SeasonId; //": 297,
      String Title; //": "Winter is Comming",
      String Description;
      String AgeRestriction;
      String Studio;
      String Origin;
      String CreatedOn; //": 0,
      int YearOfProduction; //": 2020,
      Poster Poster;
      Episodes[] Episodes;
      CategoryInfo[] CategoryInfo;
      CastAndCrewInfo[] CastAndCrewInfo;
      int SeasonNo;


    public int getSeasonId() {
        return SeasonId;
    }

    public void setSeasonId(int seasonId) {
        SeasonId = seasonId;
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

    public String getAgeRestriction() {
        return AgeRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        AgeRestriction = ageRestriction;
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

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getYearOfProduction() {
        return YearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        YearOfProduction = yearOfProduction;
    }

    public com.example.myottapp.models.Poster getPoster() {
        return Poster;
    }

    public void setPoster(com.example.myottapp.models.Poster poster) {
        Poster = poster;
    }

    public List<com.example.myottapp.models.Episodes> getEpisodes() {
        return Arrays.asList(Episodes);
    }

    public void setEpisodes(com.example.myottapp.models.Episodes[] episodes) {
        Episodes = episodes;
    }

    public com.example.myottapp.models.CategoryInfo[] getCategoryInfo() {
        return CategoryInfo;
    }

    public void setCategoryInfo(com.example.myottapp.models.CategoryInfo[] categoryInfo) {
        CategoryInfo = categoryInfo;
    }

    public com.example.myottapp.models.CastAndCrewInfo[] getCastAndCrewInfo() {
        return CastAndCrewInfo;
    }

    public void setCastAndCrewInfo(com.example.myottapp.models.CastAndCrewInfo[] castAndCrewInfo) {
        CastAndCrewInfo = castAndCrewInfo;
    }

    public int getSeasonNo() {
        return SeasonNo;
    }

    public void setSeasonNo(int seasonNo) {
        SeasonNo = seasonNo;
    }
}
