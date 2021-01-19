package com.example.myottapp.UI.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.models.CastAndCrewInfo;
import com.example.myottapp.models.CategoryInfo;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.example.myottapp.models.SessionManager;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

/*
 * Details activity class that loads LeanbackDetailsFragment class
 */
public class DetailsActivity extends Activity {
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String MOVIE = "Movie";
    public static int relatedContent;
    public static String fromPage="";
    public static int languageId;
    public Context mContext=this;
    public Movie movie;
    public static MovieBasicInfo movieBasicInfo;
    public static ExoPlayer player;
    public static PlayerView exoPlayerView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromPage=this.getIntent().getStringExtra("fromPage");
        movieBasicInfo = (MovieBasicInfo) this.getIntent().getSerializableExtra(DetailsActivity.MOVIE);
        if(fromPage.equals("Main")){
            relatedContent=this.getIntent().getIntExtra("relatedContent",0);
        }
        System.out.println("Related Content Value: "+relatedContent);
        setContentView(R.layout.activity_details);
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
    }
    public void releasePlayer(){
        if(player!=null){
            player.release();
        }
    }

    @Override
    public void onBackPressed() {
        releasePlayer();
        super.onBackPressed();
    }

    public void loadDetailsPage(Movie mSelectedMovie){
        if(mSelectedMovie!=null) {
            showOnLoadPage();
            showPosterFrame();
            hideTrailerVideoFrame();
            setMovieName(mSelectedMovie.getTitle());
            setMovieAgeRestriction(mSelectedMovie.getAgeRestriction());
            setMovieDescription(mSelectedMovie.getDescription());
            setMovieLanguage(mSelectedMovie.getLanguageName());
            setMovieRuntime(mSelectedMovie.getRunTime());
            setMovieCast(mSelectedMovie.getCastAndCrewInfo());
            setYearOfProduction(mSelectedMovie.getYearOfProduction());
            setMovieGenre(mSelectedMovie.getCategoryInfo());
            try {
                setMoviePoster(mSelectedMovie.getPoster().getUrl());
                playTrailer(mSelectedMovie.getTrailer().getUrl().getHls_Low());
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
                    Intent intent = new Intent(DetailsActivity.this, PlayerActivity.class);
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
                    MainActivity.activityCreated=0;
                    MovieBasicInfoList watchlistMovies=new MovieBasicInfoList();
                    watchlistMovies.setMovieBasicInfos(DataModel.watchlist);
                    Gson gson = new Gson();
                    SessionManager.editor.putString("WATCHLIST", gson.toJson(watchlistMovies));
                    SessionManager.editor.commit();
                    System.out.println(SessionManager.sharedPreferences.getString("WATCHLIST",null));
                    Toast.makeText(DetailsActivity.this, "Item Added to Watchlist", Toast.LENGTH_SHORT).show();
                }
            });
            hideOnLoadPage();
        }
    }

    public void showOnLoadPage(){
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
    void setMovieDescription(String description){
        TextView movieDescription=(TextView) findViewById(R.id.movie_desrciption);
        if ((description.length() <= 303)) {
            movieDescription.setText(description);
        } else {
            movieDescription.setText(description.substring(0, 300) + "...");
        }
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
            d.setVisibility(View.GONE);
        }
        if(actors.equals("")){
            c.setVisibility(View.GONE);
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
            MergingMediaSource mergedSource= new MergingMediaSource(videoSource);
            player.prepare(mergedSource);
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

    public void refreshToken() {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (DataModel.refreshTokenCount == 0) {
            Intent intent = new Intent(DetailsActivity.this, TokenRefresherActivity.class);
            intent.putExtra(DetailsActivity.MOVIE, movieBasicInfo);
            if (fromPage.equals("Main")) {
                intent.putExtra("relatedContent", relatedContent);
            }
            intent.putExtra("fromPage", fromPage);
            DataModel.refreshTokenCount = 1;
            progressBar.setVisibility(View.INVISIBLE);
            this.startActivity(intent);
            this.finish();
        }
        else progressBar.setVisibility(View.INVISIBLE);
    }
}