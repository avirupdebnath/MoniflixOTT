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
import com.example.myottapp.models.Language;
import com.example.myottapp.models.Movie;

public class LanguageActivity extends Activity {
    public static Language mLanguage;
    public static final String LANGUAGE="Language";
    private Context mContext=this;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mLanguage = (Language) this.getIntent().getSerializableExtra(LanguageActivity.LANGUAGE);
        setContentView(R.layout.activity_language);

        TextView textView=findViewById(R.id.text_language);
        textView.setText(getLanguageName());

        TextView textView_onLoad=findViewById(R.id.text_language_onLoad);
        textView_onLoad.setText(getLanguageName());

        showLoadFrame();
    }

    public void showNoResultsFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.no_results_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    public void hideNoResultsFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.no_results_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }

    public void showResultsFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.results_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    public void hideResultsFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.results_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }

    public void showLoadFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.load_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }
    public void hideLoadFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.load_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }

    public static String getLanguageName(){
        return mLanguage.getName();
    }

    public static int getLanguageId(){
        return mLanguage.getId();
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
