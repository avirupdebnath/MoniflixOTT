package com.example.myottapp.UI.Presenters;

import android.graphics.drawable.Drawable;

import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.models.Episodes;

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class EpisodesCardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    private static int CARD_WIDTH;
    private static int CARD_HEIGHT;
    private static boolean withTextFlag;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    private static String movieName="";

    public EpisodesCardPresenter(int CARD_WIDTH, int CARD_HEIGHT, boolean withTextFlag) {
        EpisodesCardPresenter.CARD_WIDTH = CARD_WIDTH;
        EpisodesCardPresenter.CARD_HEIGHT = CARD_HEIGHT;
        EpisodesCardPresenter.withTextFlag=withTextFlag;
    }

    public EpisodesCardPresenter() {
        EpisodesCardPresenter.CARD_WIDTH = 300;
        EpisodesCardPresenter.CARD_HEIGHT = 200;
        EpisodesCardPresenter.withTextFlag=false;
    }


    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view"s background is temporarily visible
        // during animations.
        //view.setBackgroundColor(color);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");

        sDefaultBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.default_background);
        sSelectedBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.selected_background);
        /*
         * This template uses a default image in res/drawable, but the general case for Android TV
         * will require your resources in xhdpi. For more information, see
         * https://developer.android.com/training/tv/start/layouts.html#density-resources
         */
        mDefaultCardImage = ContextCompat.getDrawable(parent.getContext(), R.drawable.movie);
        ImageCardView cardView;//remove R.style for getting texts as well
        cardView = new ImageCardView(parent.getContext(), R.style.CustomImageCardTheme) { //remove R.style for getting texts as well
                @Override
                public void setSelected(boolean selected) {
                    updateCardBackgroundColor(this, selected);
                    super.setSelected(selected);
                }
            };
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Episodes episode = (Episodes) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        Log.d(TAG, "onBindViewHolder");
        if (episode.getPoster().getUrl() != null) {
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            Glide.with(viewHolder.view.getContext())
                    .load(episode.getPoster().getUrl())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}