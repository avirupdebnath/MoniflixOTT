package com.example.myottapp;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.VerticalGridPresenter;

public class GridItemPresenter extends Presenter {
    public int GRID_ITEM_WIDTH;
    public  int GRID_ITEM_HEIGHT;
    private static int sSelectedBackgroundColor;
    private int sDefaultBackgroundColor;

    GridItemPresenter(){
        this.GRID_ITEM_HEIGHT=60;
        this.GRID_ITEM_WIDTH=200;
        sDefaultBackgroundColor = Color.TRANSPARENT;
    }

    GridItemPresenter(int GRID_ITEM_WIDTH,int GRID_ITEM_HEIGHT, int DefaultBackgroundColor){
        this.GRID_ITEM_WIDTH=GRID_ITEM_WIDTH;
        this.GRID_ITEM_HEIGHT=GRID_ITEM_HEIGHT;
        sDefaultBackgroundColor = DefaultBackgroundColor;
    }

    private void updateCardBackgroundColor(AppCompatTextView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : this.sDefaultBackgroundColor;
        // Both background colors should be set because the view"s background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        //view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sSelectedBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.selected_background);

        TextView view = new androidx.appcompat.widget.AppCompatTextView(parent.getContext()){
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        //view.setBackgroundColor(Color.TRANSPARENT);
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        this.updateCardBackgroundColor((AppCompatTextView) view, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ((TextView) viewHolder.view).setText((String) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }

    @Override
    public void setOnClickListener(ViewHolder holder, View.OnClickListener listener) {
        super.setOnClickListener(holder, listener);

    }
}