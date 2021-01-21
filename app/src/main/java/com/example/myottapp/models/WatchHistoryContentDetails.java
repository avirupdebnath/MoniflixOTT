package com.example.myottapp.models;

import java.io.Serializable;

public class WatchHistoryContentDetails implements Serializable {
    String Id; //": "70",
    int Type; //": 1,
    String Title; //": "Chittam maharaja",
    String Time; //": "2021-01-19T13:44:57.35",
    int TimeCode; //": 85,
    int Percentage; //": 71,
    String PosterUrl; //": "https://dev-videovillage-content-essences.s3.ap-south-1.amazonaws.com/abd37661-7b02-40cd-ad7d-d5022bebfaff"

    public String getId() {
        return Id;
    }

    public void setId(String id) {
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getTimeCode() {
        return TimeCode;
    }

    public void setTimeCode(int timeCode) {
        TimeCode = timeCode;
    }

    public int getPercentage() {
        return Percentage;
    }

    public void setPercentage(int percentage) {
        Percentage = percentage;
    }

    public String getPosterUrl() {
        return PosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        PosterUrl = posterUrl;
    }
}
