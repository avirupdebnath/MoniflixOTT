package com.example.myottapp.UI.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyCallback;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.UI.Fragments.EpisodesFragment;
import com.example.myottapp.models.CastAndCrewInfo;
import com.example.myottapp.models.CategoryInfo;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Episodes;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.example.myottapp.models.Seasons;
import com.example.myottapp.models.Series;
import com.example.myottapp.models.SessionManager;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
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

/*
 * Details activity class that loads LeanbackDetailsFragment class
 */
public class DetailsActivitySeries extends Activity {
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String SERIES = "Series";
    public static int relatedContent;
    public static String fromPage="";
    public static int languageId;
    public Context mContext=this;
    public Series Series;
    public static MovieBasicInfo movieBasicInfo;
    public static ExoPlayer player;
    public static PlayerView exoPlayerView;
    Seasons season;
    Spinner seasonSpinner;
    public static Episodes episode;
    boolean justCreated=false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieBasicInfo = (MovieBasicInfo) this.getIntent().getSerializableExtra(DetailsActivitySeries.SERIES);
        setContentView(R.layout.activity_details_series);
        //getMovieDetails();
        episode=new Episodes();
        showOnLoadPage();
        justCreated=true;
        seasonSpinner=findViewById(R.id.seasonSpinner);

    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        finish();
        startActivity(getIntent());
    }*/

    public void setSeasonSpinner(Series mSelectedSeries){
        Seasons season[]=mSelectedSeries.getSeasons();
        String []spinnerArray=new String[mSelectedSeries.getNoOfSeasons()];
        ArrayList<Seasons> list=new ArrayList<Seasons>();
        int i=0;
        for(Seasons s: season){
            spinnerArray[i++]="Season "+s.getSeasonNo();
            list.add(s);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this,(R.layout.spinner_item), spinnerArray); //selected item will look like a spinner set from XML
        seasonSpinner.setAdapter(spinnerArrayAdapter);
        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                justCreated=true;
                int seasonId=list.get(position).getSeasonId();
                getEpisodesBySeasonId(seasonId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getEpisodesBySeasonId(int id){
        VolleyRequest volleyRequest=new VolleyRequest();
        volleyRequest.sendJSONObjGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                Gson gson=new GsonBuilder().create();
                season = gson.fromJson(volleyRequest.getResponseString(),Seasons.class);
                List<Episodes> episodes=season.getEpisodes();
                EpisodesFragment episodesFragment=(EpisodesFragment) getFragmentManager().findFragmentById(R.id.episodes_fragment);
                episodesFragment.createRow(episodes);
                initialPage(season);
                loadDetailsPage(season);
            }
            @Override
            public void onError() {
            }
        }, DataModel.episodesBySeasonIdURL+id,"");
    }


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

    void loadDetailsPage(Seasons season){
        if(season!=null) {
            if(player!=null) player.release();
            showOnLoadPage();
            showPosterFrame();
            hideTrailerVideoFrame();
            setMovieName(season.getTitle());
            setMovieAgeRestriction(season.getAgeRestriction());
            setMovieDescription(season.getDescription());
            //setMovieLanguage(mSelectedSeries.getLanguageName());
            setMovieCast(season.getCastAndCrewInfo());
            setYearOfProduction(season.getYearOfProduction());
            setMovieGenre(season.getCategoryInfo());
            setMoviePoster(season.getPoster().getUrl());
            /*
            try {
                playTrailer(season.getTrailer().getUrl().getHls_Low());
            }catch (Exception e){
                hideTrailerVideoFrame();
                showPosterFrame();
            }*/
            AppCompatButton addToWatchList=(AppCompatButton) findViewById(R.id.AddToWatchListButton);
            addToWatchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataModel.watchlist.add(movieBasicInfo);
                    System.out.println(movieBasicInfo.getId()+" Added to watchlist");
                    MainActivity.activityCreated=0;
                    SeriesActivity.activityCreated=0;
                    KidsActivity.activityCreated=0;
                    MovieBasicInfoList watchlistMovies=new MovieBasicInfoList();
                    watchlistMovies.setMovieBasicInfos(DataModel.watchlist);
                    Gson gson = new Gson();
                    SessionManager.editor.putString("WATCHLIST", gson.toJson(watchlistMovies));
                    SessionManager.editor.commit();
                    System.out.println(SessionManager.sharedPreferences.getString("WATCHLIST",null));
                    Toast.makeText(DetailsActivitySeries.this, "Item Added to Watchlist", Toast.LENGTH_SHORT).show();
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
            if (player != null) {
                player.release();
            }
            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
//            TrackSelection.Factory videoTrackSelectionFactory = new
//                    AdaptiveTrackSelection.Factory(bandwidthMeter);
//            TrackSelector trackSelector = new
//                    DefaultTrackSelector(videoTrackSelectionFactory);
            player = new SimpleExoPlayer.Builder(this).build();
            player.setPlayWhenReady(true);
            exoPlayerView = (PlayerView) findViewById(R.id.trailer);
            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            player.setPlayWhenReady(true);
            exoPlayerView.setPlayer(player);
            exoPlayerView.hideController();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, getString(R.string.app_name)), defaultBandwidthMeter);
            MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(url));
            player.prepare(videoSource);
            player.addListener(new Player.Listener() {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                initialPage(season);
                seasonSpinner.requestFocus();
                releasePlayer();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                justCreated=false;
                episodePage(episode);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initialPage(Seasons season){
        TextView episodeName=(TextView) findViewById(R.id.episode_name);
        TextView runtime=(TextView) findViewById(R.id.movie_time);
        AppCompatButton addToWatchList=(AppCompatButton) findViewById(R.id.AddToWatchListButton);
        LinearLayout dirLayout=findViewById(R.id.directorsLayout);
        LinearLayout castlayout=findViewById(R.id.castLayout);

        episodeName.setVisibility(View.GONE);
        runtime.setVisibility(View.GONE);
        addToWatchList.setVisibility(View.VISIBLE);
        seasonSpinner.setVisibility(View.VISIBLE);
        dirLayout.setVisibility(View.VISIBLE);
        castlayout.setVisibility(View.VISIBLE);
        hideTrailerVideoFrame();
        showPosterFrame();
        seasonSpinner.requestFocus();
        setMovieDescription(season.getDescription());
        setMoviePoster(season.getPoster().getUrl());

    }
    public void episodePage(Episodes episode){
        DetailsActivitySeries.episode = episode;
        if(!justCreated) {
            TextView episodeName = (TextView) findViewById(R.id.episode_name);
            TextView runtime = (TextView) findViewById(R.id.movie_time);
            AppCompatButton addToWatchList = (AppCompatButton) findViewById(R.id.AddToWatchListButton);
            LinearLayout dirLayout=findViewById(R.id.directorsLayout);
            LinearLayout castlayout=findViewById(R.id.castLayout);

            episodeName.setVisibility(View.VISIBLE);
            runtime.setVisibility(View.VISIBLE);

            dirLayout.setVisibility(View.GONE);
            castlayout.setVisibility(View.GONE);
            //addToWatchList.setVisibility(View.GONE);
            //seasonSpinner.setVisibility(View.GONE);
            episodeName.setText(episode.getTitle());
            setMovieRuntime(episode.getRunTime());
            setMovieDescription(episode.getDescription());
            setMoviePoster(episode.getPoster().getUrl());
            hideTrailerVideoFrame();
            showPosterFrame();
            try{
                playTrailer(episode.getAccessUrls().getHls_High());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void refreshToken() {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (DataModel.refreshTokenCount == 0) {
            Intent intent = new Intent(DetailsActivitySeries.this, TokenRefresherActivity.class);
            intent.putExtra(TokenRefresherActivity.MOVIE, movieBasicInfo);
            intent.putExtra("fromPage", "Series");
            DataModel.refreshTokenCount = 1;
            progressBar.setVisibility(View.INVISIBLE);
            this.startActivity(intent);
            this.finish();
        }
        else progressBar.setVisibility(View.INVISIBLE);
    }
}