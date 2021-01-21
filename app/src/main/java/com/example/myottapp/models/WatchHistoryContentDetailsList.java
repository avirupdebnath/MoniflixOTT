package com.example.myottapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

public class WatchHistoryContentDetailsList implements Serializable {
    private List<WatchHistoryContentDetails> watchHistoryContentDetails;

    public List<WatchHistoryContentDetails> getWatchHistoryContentDetails() {
        return watchHistoryContentDetails;
    }

    public void setWatchHistoryContentDetails(List<WatchHistoryContentDetails> watchHistoryContentDetails) {
        this.watchHistoryContentDetails = watchHistoryContentDetails;
    }
    public static WatchHistoryContentDetailsList parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        WatchHistoryContentDetailsList watchHIstoryContentDetailsList = gson.fromJson(response, WatchHistoryContentDetailsList.class);
        return watchHIstoryContentDetailsList;
    }
}
