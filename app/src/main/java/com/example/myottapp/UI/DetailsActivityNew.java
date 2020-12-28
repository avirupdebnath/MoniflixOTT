package com.example.myottapp.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.models.CastAndCrewInfo;
import com.example.myottapp.models.CategoryInfo;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieBasicInfo;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/*
 * Details activity class that loads LeanbackDetailsFragment class
 */
public class DetailsActivityNew extends Activity {
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String MOVIE = "Movie";
    public static int relatedContent;
    public static String fromPage="";
    public static int languageId;
    public Context mContext=this;
    public Movie movie;
    public static MovieBasicInfo movieBasicInfo;
    public static ExoPlayer player=null;
    public static PlayerView exoPlayerView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fromPage=this.getIntent().getStringExtra("fromPage");
        movieBasicInfo = (MovieBasicInfo) this.getIntent().getSerializableExtra(DetailsActivityNew.MOVIE);
        if(fromPage.equals("Main")){
            relatedContent=this.getIntent().getIntExtra("relatedContent",0);
        }
        System.out.println("Related Content Value: "+relatedContent);

        setContentView(R.layout.details_activity_new);
        //getMovieDetails();
        showOnLoadPage();

    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        finish();
        startActivity(getIntent());
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
    public void releasePlayer(){
        if(player!=null){
            player.stop();
            player.release();
        }
    }


    void loadDetailsPage(Movie mSelectedMovie){
        if(mSelectedMovie!=null) {
            showOnLoadPage();
            showPosterFrame();
            hideTrailerVideoFrame();
            setMovieName(mSelectedMovie.getTitle());
            setMovieAgeRestriction(mSelectedMovie.getAgeRestriction());
            setMovieDescription(mSelectedMovie.getDescription());
            setMovieLanguage(mSelectedMovie.getLanguageName());
            setMoviePoster(mSelectedMovie.getPoster().getUrl());
            setMovieRuntime(mSelectedMovie.getRunTime());
            setMovieCast(mSelectedMovie.getCastAndCrewInfo());
            setYearOfProduction(mSelectedMovie.getYearOfProduction());
            setMovieGenre(mSelectedMovie.getCategoryInfo());
            try {
                playTrailer(mSelectedMovie.getTrailer().getUrl().getHls_High());
            }catch (Exception e){
                hideTrailerVideoFrame();
                showPosterFrame();
            }
            AppCompatButton watchNow=(AppCompatButton) findViewById(R.id.watchNowButton);
            watchNow.requestFocus();
            watchNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    releasePlayer();
                    hideTrailerVideoFrame();
                    showPosterFrame();
                    Intent intent = new Intent(DetailsActivityNew.this, PlayerActivity.class);
                    intent.putExtra(PlayerActivity.MOVIE, mSelectedMovie);
                    startActivity(intent);
                }
            });
            AppCompatButton addToWatchList=(AppCompatButton) findViewById(R.id.AddToWatchListButton);
            addToWatchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataModel.watchlist.add(movieBasicInfo);
                    System.out.println(movieBasicInfo.getId()+" Added to watchlist");
                    Toast.makeText(DetailsActivityNew.this, "Item Added to Watchlist", Toast.LENGTH_SHORT).show();
                }
            });
            hideOnLoadPage();
        }
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
        movieAge.setText(s+"+");
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
    void setYearOfProduction(int year){
        String yearOfProduction=year+"";
        TextView prodYear=findViewById(R.id.yearOfProduction);
        prodYear.setText(yearOfProduction);
    }
    void setMovieCast(CastAndCrewInfo[] cast){
        String directors="";
        String actors="";
        LinearLayout d=findViewById(R.id.directorsLayout);
        LinearLayout c=findViewById(R.id.castLayout);
        for(CastAndCrewInfo crew : cast){
            if(crew.getType()==1){
                if(actors.equals(""))actors+=crew.getCastCrewName();
                else actors+=", "+crew.getCastCrewName();
            }
            else if(crew.getType()==2){
                if(directors.equals(""))directors+=crew.getCastCrewName();
                else directors+=", "+crew.getCastCrewName();
            }
        }
        TextView directorText=findViewById(R.id.Directors);
        TextView castAndCrew=findViewById(R.id.CastInfo);
        directorText.setText(directors);
        castAndCrew.setText(actors);
        d.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        if(directors.equals("")) {
            d.setVisibility(View.INVISIBLE);
        }
        if(actors.equals("")){
            c.setVisibility(View.INVISIBLE);
        }
    }
    void setMovieGenre(CategoryInfo[] categoryInfos){
        String genres="";
        for(CategoryInfo c: categoryInfos){
            if(genres.equals(""))genres+=c.getName();
            else genres+=", "+c.getName();
        }
        TextView genreText=findViewById(R.id.genres);
        genreText.setText(genres);

    }
    void hidePosterFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.posterFrame);
        frameLayout.setVisibility(View.INVISIBLE);
    }

    void showPosterFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.posterFrame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    void hideTrailerVideoFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.trailer_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }

    void showTrailerVideoFrame(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.trailer_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    void playTrailer(String url){
        try {
            if (player != null) {
                player.release();
            }
            player = ExoPlayerFactory.newSimpleInstance(this);
            exoPlayerView = (PlayerView) findViewById(R.id.trailer);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            player.setPlayWhenReady(true);
            exoPlayerView.setPlayer(player);
            exoPlayerView.hideController();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
            MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(url));
            player.prepare(videoSource);
            player.addListener(new Player.DefaultEventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == Player.STATE_READY) {
                        hidePosterFrame();
                        showTrailerVideoFrame();
                    } else if (playbackState == Player.STATE_ENDED) {
                        hideTrailerVideoFrame();
                        showPosterFrame();
                    } else if (playbackState == Player.STATE_BUFFERING) {
                        hideTrailerVideoFrame();
                        showPosterFrame();
                        player.setPlayWhenReady(true);
                    } /*else {
                    // player paused in any state
                    isPlaying = false;
                    progressBar.setVisibility(View.GONE);
                }*/
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}