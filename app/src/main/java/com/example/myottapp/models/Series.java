package com.example.myottapp.models;

import java.io.Serializable;

public class Series implements Serializable {
    int SeriesId;        //": 296,
    String Title;//: "Game Of Thrones",
    String Description;//": "Game of Thrones is an American fantasy drama television series created by David Benioff and D. B. Weiss for HBO. It is an adaptation of A Song of Ice and Fire, George R. R. Martin's series of fantasy novels, the first of which is A Game of Thrones (1996)",
    int Type;//": 0,
    int LanguageId;//": 3,
    String LanguageName;//": "Malayalum",
    String AgeRestriction; //": "18+",
    String AirDate;//": "0001-01-01T00:00:00",
    String TierName;//": "Free",
    String Origin;//": "Russia",
    int YearOfProduction;//": 2020,
    String Studio;//": "reddy",
    int NoOfSeasons;//": 2,
    int NoOfEpisodes;//": 0,
    Poster Poster;//": {
    CategoryInfo[] CategoryInfo;
    Trailer Trailer;
    CastAndCrewInfo[] CastAndCrewInfo;
    Seasons[] Seasons;

    public int getSeriesId() {
        return SeriesId;
    }

    public void setSeriesId(int seriesId) {
        SeriesId = seriesId;
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

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getLanguageId() {
        return LanguageId;
    }

    public void setLanguageId(int languageId) {
        LanguageId = languageId;
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

    public String getAirDate() {
        return AirDate;
    }

    public void setAirDate(String airDate) {
        AirDate = airDate;
    }

    public String getTierName() {
        return TierName;
    }

    public void setTierName(String tierName) {
        TierName = tierName;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
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

    public int getNoOfSeasons() {
        return NoOfSeasons;
    }

    public void setNoOfSeasons(int noOfSeasons) {
        NoOfSeasons = noOfSeasons;
    }

    public int getNoOfEpisodes() {
        return NoOfEpisodes;
    }

    public void setNoOfEpisodes(int noOfEpisodes) {
        NoOfEpisodes = noOfEpisodes;
    }

    public com.example.myottapp.models.Poster getPoster() {
        return Poster;
    }

    public void setPoster(com.example.myottapp.models.Poster poster) {
        Poster = poster;
    }

    public com.example.myottapp.models.CategoryInfo[] getCategoryInfo() {
        return CategoryInfo;
    }

    public void setCategoryInfo(com.example.myottapp.models.CategoryInfo[] categoryInfo) {
        CategoryInfo = categoryInfo;
    }

    public com.example.myottapp.models.Trailer getTrailer() {
        return Trailer;
    }

    public void setTrailer(com.example.myottapp.models.Trailer trailer) {
        Trailer = trailer;
    }

    public com.example.myottapp.models.CastAndCrewInfo[] getCastAndCrewInfo() {
        return CastAndCrewInfo;
    }

    public void setCastAndCrewInfo(com.example.myottapp.models.CastAndCrewInfo[] castAndCrewInfo) {
        CastAndCrewInfo = castAndCrewInfo;
    }

    public com.example.myottapp.models.Seasons[] getSeasons() {
        return Seasons;
    }

    public void setSeasons(com.example.myottapp.models.Seasons[] seasons) {
        Seasons = seasons;
    }
}