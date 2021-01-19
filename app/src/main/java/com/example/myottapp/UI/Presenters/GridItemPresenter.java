package com.example.myottapp.UI.Presenters;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.leanback.widget.Presenter;

import com.example.myottapp.R;

public class GridItemPresenter extends Presenter {
    public int GRID_ITEM_WIDTH;
    public  int GRID_ITEM_HEIGHT;
    private static int sSelectedBackgroundColor;
    private int sDefaultBackgroundColor;

    public GridItemPresenter(){
        this.GRID_ITEM_HEIGHT=70;
        this.GRID_ITEM_WIDTH=180;
        sDefaultBackgroundColor = Color.TRANSPARENT;
    }

    public GridItemPresenter(int GRID_ITEM_WIDTH, int GRID_ITEM_HEIGHT, int DefaultBackgroundColor){
        this.GRID_ITEM_WIDTH=GRID_ITEM_WIDTH;
        this.GRID_ITEM_HEIGHT=GRID_ITEM_HEIGHT;
        sDefaultBackgroundColor = DefaultBackgroundColor;
    }

    private void updateCardBackgroundColor(AppCompatTextView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : this.sDefaultBackgroundColor;
        int textColor= selected ? Color.WHITE:Color.LTGRAY;
        // Both background colors should be set because the view"s background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.setTextColor(textColor);
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
        //view.setTextColor(Color.DKGRAY);
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