package com.example.myottapp.UI;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;

public class SeriesActivity extends Activity {
    public Context mContext=this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        showCarousal();
        hideMovieDetails();
    }
    public void hideCarousal(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.carousal_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }
    public void showCarousal(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.carousal_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }
    public void hideMovieDetails(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.details_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }
    public void showMovieDetails(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.details_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    void setMovieName(String s){
        TextView movieName=(TextView) findViewById(R.id.movie_name);
        movieName.setText(s);
    }
    void setMovieLanguage(String s){
        TextView movieLanguage=(TextView) findViewById(R.id.movie_language);
        movieLanguage.setText(s);
    }
    void setMovieAgeRestriction(String s){
        TextView movieAge=(TextView) findViewById(R.id.movie_age);
        movieAge.setText(s);
    }
    void setMovieDescription(String s){
        TextView movieDescription=(TextView) findViewById(R.id.movie_desrciption);
        movieDescription.setText(s);
    }
    void setMovieRuntime(int n){
        TextView movieTime=(TextView) findViewById(R.id.movie_time);
        int hr=n/60;
        int min=n%60;
        String runtime= hr+" hr "+min+" min";
        movieTime.setText(runtime);
    }
    void setMoviePoster(String url){
        ImageView moviePoster=(ImageView) findViewById(R.id.movie_poster);
        Glide.with(mContext)
                .load(Uri.parse(url))
                .centerCrop()
                .into(moviePoster);
    }


}
