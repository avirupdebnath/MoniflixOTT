package com.example.myottapp.UI.Presenters;

import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.example.myottapp.UI.Views.CustomCardView;
import com.example.myottapp.models.WatchHistoryContentDetails;

public class CustomCardViewPresenter extends Presenter {

    public static int CARD_WIDTH=300;
    public static int CARD_HEIGHT=200;

    public CustomCardViewPresenter(int CARD_WIDTH, int CARD_HEIGHT) {
        CustomCardViewPresenter.CARD_WIDTH = CARD_WIDTH;
        CustomCardViewPresenter.CARD_HEIGHT = CARD_HEIGHT;
        //CustomCardViewPresenter.withTextFlag=withTextFlag;
    }

    public CustomCardViewPresenter() {
        CustomCardViewPresenter.CARD_WIDTH = 300;
        CustomCardViewPresenter.CARD_HEIGHT = 200;
        //CustomCardViewPresenter.withTextFlag=false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        CustomCardView cardView = new CustomCardView(parent.getContext());
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        WatchHistoryContentDetails contentDetails= (WatchHistoryContentDetails) item;
        CustomCardView cardView = (CustomCardView) viewHolder.view;
        if (contentDetails.getPosterUrl() != null) {
                //cardView.setMovieTitle(movie.getTitle());
            cardView.setMovieTitle("");
            cardView.setProgress(contentDetails.getPercentage());
                //cardView.setContentText(movie.getStudio());
            cardView.setMainCardDimensions(CARD_WIDTH, CARD_HEIGHT);
            Glide.with(viewHolder.view.getContext())
                    .load(contentDetails.getPosterUrl())
                    .centerCrop()
                    .into(cardView.getImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        //CustomCardView cardView = (CustomCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        //cardView.setImageView(null);
    }
}
