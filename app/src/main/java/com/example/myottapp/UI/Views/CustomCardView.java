package com.example.myottapp.UI.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.leanback.widget.BaseCardView;
import androidx.leanback.widget.ListRowHoverCardView;

import com.example.myottapp.R;

public class CustomCardView extends BaseCardView {

    private ImageView imageView;
    private TextView movieTitle;
    private ProgressBar progressBar;
    private ListRowHoverCardView card;

    public CustomCardView(Context context) {
        super(context);
        buildCardView();
    }

    protected void buildCardView(){
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_card_view_with_progressbar, this);

        imageView = (ImageView) findViewById(R.id.image_view);
        movieTitle = (TextView) findViewById(R.id.movie_name);
        progressBar =(ProgressBar) findViewById(R.id.progress_bar);
        card=(ListRowHoverCardView) findViewById(R.id.card);
    }

    public void setMainCardDimensions(int width, int height) {
        ViewGroup.LayoutParams lp = card.getLayoutParams();
        lp.width = width;
        lp.height = height;
        card.setLayoutParams(lp);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setMovieTitle(String movieName) {
        this.movieTitle.setText(movieName);
    }

    public void setProgress(int progress) {
        this.progressBar.setProgress(progress);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}

