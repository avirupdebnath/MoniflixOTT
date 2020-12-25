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
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.myottapp.R;
import com.example.myottapp.models.Movie;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
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
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class PlayerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private PlayerView exoPlayerView;
    private ExoPlayer player;
    private RelativeLayout rootLayout;
    private MediaSource mediaSource;
    private boolean isPlaying;
    private String url = "";
    private String videoType = "";
    private String category = "";
    private int visible;
    private ImageButton serverButton, subtitleButton;
    private LinearLayout rewindLayout, ffLayout, seekBarLayout;
    private ProgressBar progressBar;
    private Spinner videoQuality;
    private TextView player_name;
    private Spinner subList;
    String videoUri;
    DefaultTrackSelector trackSelector;

    public static final String MOVIE = "movie";

    int videoRendererIndex;
    TrackGroupArray trackGroups;

    VIDEO_QUALITY quality;
    private Movie mSelectedMovie;

    public static String subval, vidval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectedMovie = (Movie) this.getIntent().getSerializableExtra(DetailsActivityNew.MOVIE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mSelectedMovie =  (Movie) this.getIntent().getSerializableExtra(PlayerActivity.MOVIE);

        init_object();
        videoType = "hls";
        category = "movie";
        intiViews();
        initVideoPlayer(url, videoType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quality_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        videoQuality.setAdapter(adapter);
        videoQuality.setOnItemSelectedListener(this);
        videoQuality.setSelection(3);
        vidval = "https://d2s5cesslnh4wz.cloudfront.net/fb2cca61-145d-4fb6-8f61-46e78514b110/hls1/master_med.m3u8?Expires=1607794028&Signature=Jkd~naUTuz9HVkWZlxh2B0QFNuClGAuHwRk3mIatbuHEocLauYydS9f1weeANtLUCOuRFphxvOnSAxatyu74~Z7PDzJQ-oCnJpmxwVmoC77DSYYSZ5VcExL4of1Kp2DyOGC8sU2PmAXl0qjZb6~h64OTBjQuT3Q7-dmVnLaBpM1Siajk0tosO8eV2PB7prG5heXm2DcKbiVL8l~J3JPBaFpMfEi2WL-YUUVDgFhGbstpDZGZfUhuLVsFn-rM-~xvCMfXhOSCo6AoZt3FZBfRuF2dnj50sExwQSEvBVCOvLnD48FXIbz9FXeQfK9gSuHMZjlza97MMmApVONTBcWzPA__&Key-Pair-Id=APKAIKKEJUUNH5EV374Q";


        ArrayList textlist = new ArrayList();
        textlist.add(0,"OFF");
        textlist.add("https://dev-videovillage-content-essences.s3.ap-south-1.amazonaws.com/ala.vtt");
        textlist.add("https://dev-videovillage-content-essences.s3.ap-south-1.amazonaws.com/ala.vtt");

        ArrayAdapter<CharSequence> s_adapter = ArrayAdapter.createFromResource(this,R.array.subtitle_list,R.layout.subtitle_item);
        s_adapter.setDropDownViewResource(R.layout.subtitle_item);
        subList.setAdapter(s_adapter);
        subList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Video Quality", position + "");
                long time = player.getCurrentPosition();

                subval = textlist.get(position).toString();
                buildMediaSource(vidval, subval);
                player.seekTo(0, time);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void init_object() {
        quality=new VIDEO_QUALITY();
        quality.setZero(mSelectedMovie.getAccessUrls().getHls_High());
        quality.setOne(mSelectedMovie.getAccessUrls().getHls_Low());
        quality.setTwo(mSelectedMovie.getAccessUrls().getHls_Med());
        quality.setThree(mSelectedMovie.getAccessUrls().getHls_High());
    }

    private void buildMediaSource(String mUri, String captionUri) {

        Uri mmUri = Uri.parse(mUri);

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mmUri);
        MergingMediaSource mergedSource;

        if (captionUri.equals("OFF")){
            mergedSource = new MergingMediaSource(videoSource);
            player.prepare(mergedSource);
            player.setPlayWhenReady(true);
        }else{
            Uri cUri = Uri.parse(captionUri);
            Format captionFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, C.SELECTION_FLAG_DEFAULT, "en");
            MediaSource captionMediaSource = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(cUri, captionFormat, C.TIME_UNSET);
            mergedSource = new MergingMediaSource(videoSource,captionMediaSource);
            player.prepare(mergedSource);
            player.setPlayWhenReady(true);
        }

        //player.addListener(this);
    }

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
                vidval = quality.getZero();
                buildMediaSource(vidval, subval);
                player.seekTo(0, time);
                break;
            case 1:
                vidval = quality.getOne();
                buildMediaSource(vidval, subval);
                player.seekTo(0, time);
                break;
            case 2:
                vidval = quality.getTwo();
                buildMediaSource(vidval, subval);
                player.seekTo(0, time);
                break;
            case 3:
                vidval = quality.getThree();
                buildMediaSource(vidval, subval);
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
        player_name = findViewById(R.id.player_title);
        player_name.setText(mSelectedMovie.getTitle());

        serverButton = findViewById(R.id.img_settings);
        subtitleButton = findViewById(R.id.img_subtitle);
        rewindLayout = findViewById(R.id.rewind_layout);
        ffLayout = findViewById(R.id.forward_layout);
        seekBarLayout = findViewById(R.id.seekbar_layout);
        videoQuality = findViewById(R.id.video_qualitySP);
        subList = findViewById(R.id.subTitleRow);


    }

    @Override
    protected void onStart() {
        super.onStart();

        subtitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subList.setDropDownWidth(300);
                subList.setDropDownVerticalOffset(-30);
                subList.performClick();
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
