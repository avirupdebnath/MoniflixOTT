package com.example.myottapp.UI.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.UI.Fragments.BannerFragment;
import com.example.myottapp.UI.Fragments.CarousalFragment;
import com.example.myottapp.models.DataModel;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {
    public Context mContext=this;
    public static int activityCreated;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreated=1;
        DataModel.refreshTokenCount=0;
        setContentView(R.layout.activity_main);

        CarousalFragment carousalFragment= (CarousalFragment) getFragmentManager().findFragmentById(R.id.carousal_fragment);
        carousalFragment.getCarousal(1,"Main");

        showOnLoadPage();
        showCarousal();
        hideMovieDetails();
        //collapseLanguageRow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BannerFragment.position=1;
        DataModel.refreshTokenCount=0;
        if(activityCreated!=1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void hideCarousal(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.carousal_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }
    public void collapseLanguageRow(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.browse_frame);
        FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height=400;
        layoutParams.width= FrameLayout.LayoutParams.MATCH_PARENT;
        layoutParams.gravity=Gravity.BOTTOM;
    }
    public void expandLanguageRow(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.browse_frame);
        FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height=550;
        layoutParams.width= FrameLayout.LayoutParams.MATCH_PARENT;
        layoutParams.gravity=Gravity.BOTTOM;
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

    public void setMovieName(String s){
        TextView movieName=(TextView) findViewById(R.id.movie_name);
        movieName.setText(s);
    }
    /*
    void setMovieLanguage(String s){
        TextView movieLanguage=(TextView) findViewById(R.id.movie_language);
        movieLanguage.setText(s);
    }*/
    public void setMovieAgeRestriction(String s){
        TextView movieAge=(TextView) findViewById(R.id.movie_age);
        movieAge.setText(s);
    }
    public void setMovieDescription(String s){
        TextView movieDescription=(TextView) findViewById(R.id.movie_desrciption);
        movieDescription.setText(s);
    }
    public void setMovieRuntime(int n){
        TextView movieTime=(TextView) findViewById(R.id.movie_time);
        int hr=n/60;
        int min=n%60;
        String runtime= hr+" hr "+min+" min";
        movieTime.setText(runtime);
    }
    public void setMoviePoster(String url){
        ImageView moviePoster=(ImageView) findViewById(R.id.movie_poster);
        Glide.with(mContext)
                .load(Uri.parse(url))
                .centerCrop()
                .into(moviePoster);
    }

    public void setYearOfProduction(int year){
        String yearOfProduction=year+"";
        TextView prodYear=findViewById(R.id.yearOfProduction);
        prodYear.setText(yearOfProduction);
    }

    void showOnLoadPage(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.load_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    public void hideOnLoadPage() {
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.load_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }



}