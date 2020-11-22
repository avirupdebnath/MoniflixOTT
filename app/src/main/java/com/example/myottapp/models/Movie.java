package com.example.myottapp.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Movie implements Serializable {
        static final long serialVersionUID = 727566175075960653L;
        private int MovieId; //": 273,
        private String MovieKey; //": "215ee68d-a52f-4bc4-9b58-a71e0717a9a8",
        private String Title; //": "Asuran",
        private String  Description; //": " Indian Tamil-language action drama film written and directed by Vetrimaaran and produced by Kalaipuli S. Thanu under his production banner V Creations. The film is based on Poomani's novel Vekkai",
        private int  LanguageI;  //": 1,
        private String  LanguageName;  //": "Kannada",
        private String AgeRestriction; //": "13",
        private int RunTime;  //": 132,
        private int YearOfProduction; //": 0,
        private String Studio; //": "reddy",
        private String Origin; //": "India",
        private Poster Poster;
        /*": {
        "Id": 218,
                "PosterKey": "273593d0524c-6629-4fba-b694-bd14cf60c9189",
                "Extension": "png       ",
                "Url": "https://dev-videovillage-content-essences.s3.ap-south-1.amazonaws.com/273593d0524c-6629-4fba-b694-bd14cf60c9189.png"
        },*/
        private Trailer Trailer;
        /*": {
        "Id": 0,
                "IsTeaser": false
    },*/
        private AccessUrls AccessUrls;
        /*": {
        "Hls_High": "https://d2s5cesslnh4wz.cloudfront.net/215ee68d-a52f-4bc4-9b58-a71e0717a9a8/hls1/master_high.m3u8?Expires=1606002395&Signature=ivMb9UaI1~hnKqZ-q3zUhfnMKeTOVNPSmlM0Ai58RqyCe1HOWxFqKv1zxaQDaBnMFoWrBt-kiNwqTfI9qkS2l7JaBemRyS3tFrySfcS621VifmXROD~otJc~wAxwLa2DppMfD8se4L29k00PvOx3wnG7oYhwsHu8~5fEKs5fznPpcLER9lI9pQu3xNjBodeu-McCd8XvOa2kmLP9FZCgPjw27-0JPZTdNsUzBuKVO4QOQizNKsHwLNFp7XqgV6XwQUhCzx06WdqCGd32vHfsX3xwsErF8A9AyNXPFWj6c-JAz9tkow2lmSVpeW-sfIQRUkserKIpzvkfiQ2Jr69rAw__&Key-Pair-Id=APKAIKKEJUUNH5EV374Q",
                "Hls_Med": "https://d2s5cesslnh4wz.cloudfront.net/215ee68d-a52f-4bc4-9b58-a71e0717a9a8/hls1/master_med.m3u8?Expires=1606002395&Signature=OqMAn-CjUdrUPPN7XxXwRLH-uIC1di2dtIsg-7p7hvBWROfGkCGnNO8JRNWRSFYhDKycPHy4yG7mM1656G4A8MgVDrH7DqZh3rAFE27nkoYgrp4iwgaT-hv6DNRfvGFsWlVbevlRJFYUm5fRqWF4fRUuj3JbNhYIBpkCF-PWnDlA9kWcNbr6Bfwt0NQ7IriCYxi0T9n~ZHFVJzj1~9XW0wfKDbi-uoZsgojy4udNbxTWHBfrKy7Pmk4zoPFTH2e3tqTYDjOSA0NA6mhgSRk0rR5G56m4y9ouRFjUBBm25yUtHIKZulrs0~vDB5nAud7OBzNkl-CJWo6JdVR-VX6-pA__&Key-Pair-Id=APKAIKKEJUUNH5EV374Q",
                "Hls_Low": "https://d2s5cesslnh4wz.cloudfront.net/215ee68d-a52f-4bc4-9b58-a71e0717a9a8/hls1/master_low.m3u8?Expires=1606002395&Signature=RwuH4eQVnBdEOE744yLSJ69rBNdTXWkHGAtfXr9lQlzmu56T5nP~r6hshoxdTjLsjttz4nwaNWFrPoUo4tQRLPXCVsRZ~bhGpc-MJdiDWHgFshc9-IyrWxPW-UBfBTBF4hbohb9r-5oBzORZPllL~DmN0peFO6Q-nL9VIfov4vLDBF9ZJpVfuoHk3TX67Flv30DYkbbrEsNCRAXgqPqwwaINW7pYljk7M24jCN2kZgJd49Thw2aBbpQH1Mn47UI-KokfYFfCMYEsnOMA9wFrA4vStWVBdwcLkVNBmKy0tshDRtz3m6aeLeh~bNJlwP3aXUBYoTb1ADzQwWHASNZrog__&Key-Pair-Id=APKAIKKEJUUNH5EV374Q"
    },*/
        private CastAndCrewInfo[] CastAndCrewInfo;
        /*": [
        {
            "CastCrewName": "Manju Warrier",
                "SkillName": "Actress",
                "Type": 1
        },
        {
            "CastCrewName": "Prakash Raj",
                "SkillName": "Director",
                "Type": 2
        },
        {
            "CastCrewName": "Pasupathy",
                "SkillName": "Music",
                "Type": 1
        },
        {
            "CastCrewName": "Pavan",
                "SkillName": "Stunts",
                "Type": 2
        },
        {
            "CastCrewName": "Aadukalam Naren",
                "SkillName": "Supporting Role",
                "Type": 1
        },
        {
            "CastCrewName": "Creq1",
                "SkillName": "Music",
                "Type": 1
        },
        {
            "CastCrewName": "Vikas",
                "SkillName": "Director",
                "Type": 2
        }
    ],*/
       private CategoryInfo[] CategoryInfo;
       /*: [
        {
            "Id": 175,
                "Name": "Action"
        },
        {
            "Id": 176,
                "Name": "Comedy"
        },
        {
            "Id": 177,
                "Name": "Sci-Fi"
        }
    ]*/

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
        return LanguageI;
    }

    public void setLanguageI(int languageI) {
        LanguageI = languageI;
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
