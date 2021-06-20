package com.example.myottapp.UI.Presenters;

import android.graphics.drawable.Drawable;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.UI.Activities.MainActivity;
import com.example.myottapp.models.MovieBasicInfo;

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class BigCardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    private static int CARD_WIDTH = 2000;
    private static int CARD_HEIGHT = 850;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    public BigCardPresenter(int CARD_WIDTH, int CARD_HEIGHT) {
        BigCardPresenter.CARD_WIDTH = CARD_WIDTH;
        BigCardPresenter.CARD_HEIGHT = CARD_HEIGHT;
        //BigCardPresenter.withTextFlag=withTextFlag;
    }

    public BigCardPresenter() {
        BigCardPresenter.CARD_WIDTH = 300;
        BigCardPresenter.CARD_HEIGHT = 200;
        //BigCardPresenter.withTextFlag=false;
    }

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view"s background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        //view.findViewById(R.id.info_field).setBackgroundColor(color);
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

        ImageCardView cardView =
                new ImageCardView(new ContextThemeWrapper(parent.getContext(),R.style.CustomImageCardTheme)) {//remove R.style for getting texts as well
                    @Override
                    public void setSelected(boolean selected) {
                        //updateCardBackgroundColor(this, selected);
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
        //Movie movie = (Movie) item;
        MovieBasicInfo movieBasicInfo =(MovieBasicInfo) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        Log.d(TAG, "onBindViewHolder");
        if (movieBasicInfo.getPosterUrl() != null) {
            //cardView.setTitleText(movie.getTitle());
            //cardView.setContentText(movie.getStudio());
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            Glide.with(viewHolder.view.getContext())
                    .load(movieBasicInfo.getPosterUrl())
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
