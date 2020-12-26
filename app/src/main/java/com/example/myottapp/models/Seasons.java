package com.example.myottapp.models;

import java.io.Serializable;

public class Seasons implements Serializable {
     int SeasonId; //": 297,
      String Title; //": "Winter is Comming",
      String CreatedOn; //": 0,
      int YearOfProduction; //": 2020,
      Poster Poster;
      Episodes[] Episodes;

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

    public com.example.myottapp.models.Episodes[] getEpisodes() {
        return Episodes;
    }

    public void setEpisodes(com.example.myottapp.models.Episodes[] episodes) {
        Episodes = episodes;
    }
}
