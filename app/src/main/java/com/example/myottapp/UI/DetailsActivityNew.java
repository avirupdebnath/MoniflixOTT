package com.example.myottapp.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieBasicInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * Details activity class that loads LeanbackDetailsFragment class
 */
public class DetailsActivityNew extends Activity {
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String MOVIE = "Movie";
    public Context mContext=this;
    public Movie movie;
    public MovieBasicInfo movieBasicInfo;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        movieBasicInfo = (MovieBasicInfo) this.getIntent().getSerializableExtra(DetailsActivityNew.MOVIE);
        setContentView(R.layout.details_activity_new);
        getMovieDetails();
        showOnLoadPage();
        /*
        if(mSelectedMovie!=null){
            setMovieName(mSelectedMovie.getTitle());
            setMovieAgeRestriction(mSelectedMovie.getAgeRestriction());
            setMovieDescription(mSelectedMovie.getDescription());
            setMovieLanguage(mSelectedMovie.getLanguageName());
            setMoviePoster(mSelectedMovie.getPoster().getUrl());
            setMovieRuntime(mSelectedMovie.getRunTime());
            //movieLanguage.setText(mSelectedMovie.getLanguageName());
            //movieAge.setText(mSelectedMovie.getAgeRestriction());
            //movieDescription.setText(mSelectedMovie.getDescription().trim());
            //int hr=mSelectedMovie.getRunTime()/60;
            //int min=mSelectedMovie.getRunTime()%60;
            //String runtime= hr+" hr "+min+" min";
            //movieTime.setText(runtime);
            //Glide.with(mContext)
            //        .load(Uri.parse(mSelectedMovie.getPoster().getUrl()))
            //        .centerCrop()
            //        .into(moviePoster);

        }  */
    }

    void loadDetailsPage(Movie mSelectedMovie){
        if(mSelectedMovie!=null) {
            setMovieName(mSelectedMovie.getTitle());
            setMovieAgeRestriction(mSelectedMovie.getAgeRestriction());
            setMovieDescription(mSelectedMovie.getDescription());
            setMovieLanguage(mSelectedMovie.getLanguageName());
            setMoviePoster(mSelectedMovie.getPoster().getUrl());
            setMovieRuntime(mSelectedMovie.getRunTime());
            AppCompatButton watchNow=(AppCompatButton) findViewById(R.id.watchNowButton);
            watchNow.requestFocus();
            watchNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailsActivityNew.this, PlayerActivity.class);
                    intent.putExtra(PlayerActivity.MOVIE, mSelectedMovie);
                    startActivity(intent);
                }
            });
            hideOnLoadPage();
        }

    }

    void getMovieDetails(){
        String tag="";
        VolleyRequest volleyRequest=new VolleyRequest();
        volleyRequest.sendJSONObjGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                Gson gson=new GsonBuilder().create();
                movie = gson.fromJson(volleyRequest.getResponseString(),Movie.class);
                loadDetailsPage(movie);
            }
        }, DataModel.movieDetailsByIdURL+(movieBasicInfo.getId()),tag);
    }

    void showOnLoadPage(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.load_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    void hideOnLoadPage() {
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.load_frame);
        frameLayout.setVisibility(View.INVISIBLE);
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