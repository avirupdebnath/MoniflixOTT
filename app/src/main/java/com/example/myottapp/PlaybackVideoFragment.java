package com.example.myottapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.MediaPlayerAdapter;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.PlaybackControlsRow;

import com.example.myottapp.extras.Movie;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {

    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;
    private PlaybackControlsRow.RepeatAction repeatAction;
    private PlaybackControlsRow.PictureInPictureAction pipAction;
    private PlaybackControlsRow.ThumbsUpAction thumbsUpAction;
    private PlaybackControlsRow.ThumbsDownAction thumbsDownAction;
    private PlaybackControlsRow.SkipPreviousAction skipPreviousAction;
    private PlaybackControlsRow.SkipNextAction skipNextAction;
    private PlaybackControlsRow.FastForwardAction fastForwardAction;
    private PlaybackControlsRow.RewindAction rewindAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Movie movie =
                (Movie) getActivity().getIntent().getSerializableExtra(DetailsActivity.MOVIE);

        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackVideoFragment.this);

        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getActivity());
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);

        mTransportControlGlue = new PlaybackTransportControlGlue<>(getActivity(), playerAdapter);
        mTransportControlGlue.setHost(glueHost);
        mTransportControlGlue.setTitle(movie.getTitle());
        mTransportControlGlue.setSubtitle(movie.getDescription());
        mTransportControlGlue.playWhenPrepared();
        playerAdapter.setDataSource(Uri.parse(movie.getVideoUrl()));

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTransportControlGlue != null) {
            mTransportControlGlue.pause();
        }
    }


}/*
    @Override
    protected void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        // Order matters, super.onCreatePrimaryActions() will create the play / pause action.
        // Will display as follows:
        // play/pause, previous, rewind, fast forward, next
        //   > /||      |<        <<        >>         >|
        super.onCreatePrimaryActions(primaryActionsAdapter);
        primaryActionsAdapter.add(skipPreviousAction);
        primaryActionsAdapter.add(rewindAction);
        primaryActionsAdapter.add(fastForwardAction);
        primaryActionsAdapter.add(skipNextAction);
    }

    @Override
    protected void onCreateSecondaryActions(ArrayObjectAdapter adapter) {
        super.onCreateSecondaryActions(adapter);
        adapter.add(thumbsDownAction);
        adapter.add(thumbsUpAction);
    }

    @Override
    public void onActionClicked(Action action) {
        if (action == rewindAction) {
            // Handle Rewind
        } else if (action == fastForwardAction ) {
            // Handle FastForward
        } else if (action == thumbsDownAction) {
            // Handle ThumbsDown
        } else if (action == thumbsUpAction) {
            // Handle ThumbsUp
        } else {
            // The superclass handles play/pause and delegates next/previous actions to abstract methods,
            // so those two methods should be overridden rather than handling the actions here.
            super.onActionClicked(action);
        }
    }

    @Override
    public void next() {
        // Skip to next item in playlist.
    }

    @Override
    public void previous() {
        // Skip to previous item in playlist.
    }
}*/