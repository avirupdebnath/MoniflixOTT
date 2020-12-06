package com.example.myottapp.UI;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.example.myottapp.R;
import com.example.myottapp.models.Movie;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static android.view.View.VISIBLE;

public class PlayerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "PlayerActivity";
    private static final String CLASS_NAME = "com.oxoo.spagreen.ui.activity.PlayerActivity";
    private PlayerView exoPlayerView;
    private ExoPlayer player;
    private RelativeLayout rootLayout;
    private MediaSource mediaSource;
    private boolean isPlaying;
//    private List<Video> videos = new ArrayList<>();
//    private Video video = null;
    private String url = "";
    private String videoType = "";
    private String category = "";
    private int visible;
    private ImageButton serverButton, subtitleButton;
    private LinearLayout rewindLayout, ffLayout, seekBarLayout;
    private ProgressBar progressBar;
    private Spinner videoQuality;
    String videoUri;

    DefaultTrackSelector trackSelector;

    int videoRendererIndex;
    TrackGroupArray trackGroups;

    VIDEO_QUALITY quality;
    private Movie mSelectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectedMovie = (Movie) this.getIntent().getSerializableExtra(DetailsActivityNew.MOVIE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        init_object();

//        //http://playertest.longtailvideo.com/adaptive/bbbfull/bbbfull.m3u8
        videoUri = "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4";
//
//        String LOW = "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4";
//        String MEDIUM = "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4";
//        String HIGH = "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4";
//
        url = "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4";
        videoType = "hlsd";
        category = "movie";
//        url = getIntent().getStringExtra("streamUrl");
//        videoType = getIntent().getStringExtra("videoType");
//        category = getIntent().getStringExtra("category");
//        video = (Video) getIntent().getSerializableExtra("video");
        intiViews();
       // url=mSelectedMovie.getAccessUrls().getHls_High();

        initVideoPlayer(url, videoType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quality_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
// Apply the adapter to the spinner
        videoQuality.setAdapter(adapter);
        videoQuality.setOnItemSelectedListener(this);
        videoQuality.setSelection(3);

    }



    public void init_object() {
        quality = new VIDEO_QUALITY("http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4", "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4", "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4", "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4");
        //quality=new VIDEO_QUALITY();
        //quality.setZero(mSelectedMovie.getAccessUrls().getHls_High());
        //quality.setOne(mSelectedMovie.getAccessUrls().getHls_Low());
        //quality.setTwo(mSelectedMovie.getAccessUrls().getHls_Med());
        //quality.setThree(mSelectedMovie.getAccessUrls().getHls_High());
    }//

    private void buildMediaSource(Uri mUri) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);
        MediaSource videoSource2 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUri));
        ConcatenatingMediaSource concatenatedSource =
                new ConcatenatingMediaSource(videoSource, videoSource2);
        // Prepare the player with the source.
        player.prepare(concatenatedSource);
        player.setPlayWhenReady(true);
//        player.addListener(this);
    }

//    private MediaSource hlsMediaSource(Uri uri, Context context) {
//
//        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
//                Util.getUserAgent(context, R.string.app_name), bandwidthMeter);
//
//        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(uri);
//
//        return videoSource;
//
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        //((TextView) view).setTextColor(Color.RED); //Change selected text color
        Log.d("Video Quality", pos + "");
        long time = player.getCurrentPosition();
        switch (pos) {
            case 0:
                buildMediaSource(Uri.parse(quality.getZero()));
                mediaSource = mediaSource(Uri.parse(quality.getZero()), this);
                player.seekTo(0, time);
                break;
            case 1:
                buildMediaSource(Uri.parse(quality.getOne()));
                mediaSource = mediaSource(Uri.parse(quality.getOne()), this);
                player.seekTo(0, time);
                break;
            case 2:
                buildMediaSource(Uri.parse(quality.getTwo()));
                mediaSource = mediaSource(Uri.parse(quality.getTwo()), this);
                player.seekTo(0, time);
                System.out.println("#############################three***********************");
                break;
            case 3:
                buildMediaSource(Uri.parse(quality.getThree()));
                mediaSource = mediaSource(Uri.parse(quality.getThree()), PlayerActivity.this);
                player.seekTo(0, time);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void initVideoPlayer(String url, String type) {
        if (player != null) {
            player.release();
        }

        progressBar.setVisibility(VISIBLE);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new
                AdaptiveTrackSelection.Factory(bandwidthMeter);


        TrackSelector trackSelector = new
                DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance((Context) this, trackSelector);
        player.setPlayWhenReady(true);
        exoPlayerView.setPlayer(player);

        Uri uri = Uri.parse(url);

        if (type.equals("hls")) {
            mediaSource = hlsMediaSource(uri, this);
        }
//        else if (type.equals("mp3")) {
//            mediaSource = mp3MediaSource(uri);
//            RenderersFactory renderersFactory = new DefaultRenderersFactory(getApplicationContext());
//            LoadControl loadControl = new DefaultLoadControl();
//            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
//            player.prepare(mediaSource);
//        }
        else {
            mediaSource = mediaSource(uri, this);
        }


//        player.prepare(mediaSource, true, false);

        player.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    isPlaying = true;
                    progressBar.setVisibility(View.GONE);
                } else if (playbackState == Player.STATE_READY) {
                    isPlaying = false;
                    progressBar.setVisibility(View.GONE);
                } else if (playbackState == Player.STATE_BUFFERING) {
                    isPlaying = false;
                    progressBar.setVisibility(VISIBLE);
                    player.setPlayWhenReady(true);
                } else {
                    // player paused in any state
                    isPlaying = false;
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        exoPlayerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                visible = visibility;
            }
        });

    }

    private void intiViews() {
        progressBar = findViewById(R.id.progress_bar);
        exoPlayerView = findViewById(R.id.player_view);
        rootLayout = findViewById(R.id.root_layout);
        serverButton = findViewById(R.id.img_settings);
        subtitleButton = findViewById(R.id.img_subtitle);
        rewindLayout = findViewById(R.id.rewind_layout);
        ffLayout = findViewById(R.id.forward_layout);
        seekBarLayout = findViewById(R.id.seekbar_layout);
        videoQuality = findViewById(R.id.video_qualitySP);
    }

    @Override
    protected void onStart() {
        super.onStart();

        subtitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open subtitle dialog
//                openSubtitleDialog();
            }
        });

        // Video Quality
        serverButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                videoQuality.setDropDownWidth(300);
                videoQuality.setDropDownVerticalOffset(-30);
                videoQuality.performClick();
                //videoQuality.setFocusable(true);
                //videoQuality.setForegroundTintList(getResources().getColorStateList(R.drawable.color_selector));
            }


        });
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!exoPlayerView.isControllerVisible()) {
            exoPlayerView.showController();
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!exoPlayerView.isControllerVisible()) {
                exoPlayerView.showController();
            }else {
                releasePlayer();
                finish();
            }
        } else if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            //Toast.makeText(this, "escape button", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void openServerDialog() {

    }

    private void openSubtitleDialog() {
//        if (video != null) {
//            if (!video.getSubtitle().isEmpty()) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivity.this);
//                View view = LayoutInflater.from(PlayerActivity.this).inflate(R.layout.layout_subtitle_dialog, null);
//                RecyclerView serverRv = view.findViewById(R.id.serverRv);
//                SubtitleListAdapter adapter = new SubtitleListAdapter(PlayerActivity.this, video.getSubtitle());
//                serverRv.setLayoutManager(new LinearLayoutManager(PlayerActivity.this));
//                serverRv.setHasFixedSize(true);
//                serverRv.setAdapter(adapter);
//
//                Button closeBt = view.findViewById(R.id.close_bt);
//
//                builder.setView(view);
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//
//                closeBt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                //click event
//                adapter.setListener(new SubtitleListAdapter.OnSubtitleItemClickListener() {
//                    @Override
//                    public void onSubtitleItemClick(View view, Subtitle subtitle, int position, SubtitleListAdapter.SubtitleViewHolder holder) {
//                        setSelectedSubtitle(mediaSource, subtitle.getUrl());
//                        dialog.dismiss();
//                    }
//                });
//
//            } else {
//                new ToastMsg(this).toastIconError(getResources().getString(R.string.no_subtitle_found));
//            }
//        } else {
//            new ToastMsg(this).toastIconError(getResources().getString(R.string.no_subtitle_found));
//        }
    }

//    private void setSelectedSubtitle(MediaSource mediaSource, String url) {
//        MergingMediaSource mergedSource;
//        if (url != null) {
//            Uri subtitleUri = Uri.parse(url);
//
//            Format subtitleFormat = Format.createTextSampleFormat(
//                    null, // An identifier for the track. May be null.
//                    MimeTypes.TEXT_VTT, // The mime type. Must be set correctly.
//                    Format.NO_VALUE, // Selection flags for the track.
//                    "en"); // The subtitle language. May be null.
//
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(PlayerActivity.this,
//                    Util.getUserAgent(PlayerActivity.this, CLASS_NAME), new DefaultBandwidthMeter());
//
//
//            MediaSource subtitleSource = new SingleSampleMediaSource
//                    .Factory(dataSourceFactory)
//                    .createMediaSource(subtitleUri, subtitleFormat, C.TIME_UNSET);
//
//
//            mergedSource = new MergingMediaSource(mediaSource, subtitleSource);
//            player.prepare(mergedSource, false, false);
//            player.setPlayWhenReady(true);
//            //resumePlayer();
//
//        } else {
//            Toast.makeText(PlayerActivity.this, "there is no subtitle", Toast.LENGTH_SHORT).show();
//        }
//    }



    private MediaSource mp3MediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), "ExoplayerDemo");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Handler mainHandler = new Handler();
        return new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, mainHandler, null);
    }

    private MediaSource mediaSource(Uri uri, Context context) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }

    private MediaSource hlsMediaSource(Uri uri, Context context) {

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "oxoo"), bandwidthMeter);

        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        return videoSource;

    }

    @Override
    public void onBackPressed() {
        if (visible == View.GONE) {
            exoPlayerView.showController();
        } else {
            releasePlayer();
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
            player = null;
            exoPlayerView.setPlayer(null);
        }
    }
}
