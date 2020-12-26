package com.example.myottapp.models;

import java.io.Serializable;

public class Episodes implements Serializable {
        int EpisodeId; //": 301,
        String EpisodeKey; //": "37ef5ba9-60d6-4ed4-9805-ce220f20856b",
        String  Title; //": "S01E01",
        String  Description; //": "Winter is on the way",
        int  RunTime; //": 60,
        int YearOfProduction; //": 2020,
        Poster Poster; //":
        Subtitle []Subtitle; //": [],
        Trailer Trailer; //
        AccessUrls AccessUrls; //

    public int getEpisodeId() {
        return EpisodeId;
    }

    public void setEpisodeId(int episodeId) {
        EpisodeId = episodeId;
    }

    public String getEpisodeKey() {
        return EpisodeKey;
    }

    public void setEpisodeKey(String episodeKey) {
        EpisodeKey = episodeKey;
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

    public com.example.myottapp.models.Poster getPoster() {
        return Poster;
    }

    public void setPoster(com.example.myottapp.models.Poster poster) {
        Poster = poster;
    }

    public com.example.myottapp.models.Subtitle[] getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(com.example.myottapp.models.Subtitle[] subtitle) {
        Subtitle = subtitle;
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
}

