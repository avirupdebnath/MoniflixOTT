package com.example.myottapp.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.app.VerticalGridFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;

import com.example.myottapp.R;
import com.example.myottapp.extras.Movie;

public class BannerFragment extends RowsFragment {

    private static final String TAG = "BannerFragment";
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        //gridPresenter.setNumberOfColumns(5);
        //setGridPresenter(gridPresenter);
        loadBanner();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //workaroundFocus();
    }

    private void loadBanner() {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.Search));
        gridRowAdapter.add(getResources().getString(R.string.Home));
        gridRowAdapter.add(getResources().getString(R.string.Movies));
        gridRowAdapter.add(getResources().getString(R.string.Series));
        gridRowAdapter.add(getResources().getString(R.string.Kids));
        //gridRowAdapter.add(getResources().getString(R.string.Shorts));
        gridRowAdapter.add(getResources().getString(R.string.MyLists));
        gridRowAdapter.add(getResources().getString(R.string.Settings));
        //setAdapter(gridRowAdapter);
        rowsAdapter.add(new ListRow(gridRowAdapter));
        setAdapter(rowsAdapter);

        setOnItemViewClickedListener(new BannerFragment.ItemViewClickedListener());
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());


        mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.text_black_to_transparent_shade);
        mBackgroundManager.setDrawable(mDefaultBackground);
        }

    /*
     rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            CardPresenter cardPresenter = new CardPresenter();
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);

                    listRowAdapter.add(new Movie());

                HeaderItem header = new HeaderItem(0, "01234");

                rowsAdapter.add(new ListRow(header, listRowAdapter));

            setAdapter(rowsAdapter);
     */
  /*  public void workaroundFocus(){
        if(getView() != null) {
            View viewToFocus  = getView().findViewById(R.id.row_content);
            BrowseFrameLayout browseFrameLayout = getView().findViewById(androidx.leanback.R.id.grid_frame);
            browseFrameLayout.setOnFocusSearchListener((focused, direction) -> {
                if (direction == View.FOCUS_DOWN) {
                    return viewToFocus;
                }
                else {
                    return null;
                }
            });
        }
    }*/
    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.error_fragment))) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    if(((String) item).contains(getString(R.string.Home))) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
                    }
                    else if(((String) item).contains(getString(R.string.Settings))) {
                        Intent intent = new Intent(getActivity(), SettingActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}