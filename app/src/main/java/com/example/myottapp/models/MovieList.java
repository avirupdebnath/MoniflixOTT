package com.example.myottapp.models;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MovieList {
    private List <Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public static MovieList parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieList movieResponse = gson.fromJson(response, MovieList.class);
        return movieResponse;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "";
        for(Movie movie: movies)
            result+= "\n"+ movie.toString();
        return result;
    }
}
