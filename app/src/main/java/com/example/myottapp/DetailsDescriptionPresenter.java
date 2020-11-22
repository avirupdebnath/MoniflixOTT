package com.example.myottapp;

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieList;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;

        if (movie != null) {
            viewHolder.getTitle().setText(movie.getTitle());
            viewHolder.getSubtitle().setText(movie.getStudio());
            viewHolder.getBody().setText(movie.getDescription());
        }
    }
}