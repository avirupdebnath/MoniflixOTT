package com.example.myottapp.UI.Presenters;

import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.RowPresenter;

public class CustomListRowPresenter extends ListRowPresenter {
    private int mInitialSelectedPosition;

    public CustomListRowPresenter(int focusZoomIndicator, int position) {
        super(focusZoomIndicator);
        this.mInitialSelectedPosition = position;
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        ViewHolder vh = (ListRowPresenter.ViewHolder) holder;
        vh.getGridView().setSelectedPosition(mInitialSelectedPosition);
    }
}
