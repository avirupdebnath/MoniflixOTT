package com.example.myottapp.UI;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.BaseOnItemViewSelectedListener;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.myottapp.R;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.extras.MovieList;
import com.example.myottapp.models.Movie;

import java.util.Collections;
import java.util.List;

import static com.example.myottapp.UI.MainFragment.moviesList;

public class RelatedItemsFragment extends RowsFragment {
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter mRowsAdapter;
    private static int count=0;
    private boolean hasReloaded=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRows();
        setupEventListeners();
        hasReloaded=true;
        //setHeadersState(HEADERS_DISABLED);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareBackgroundManager();

    }

    @Override
    public void onResume() {
        super.onResume();
        //mBackgroundManager.setDrawable(mDefaultBackground);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //if (null != mBackgroundTimer) {
        //  Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
        //  mBackgroundTimer.cancel();
        //}
    }

    private void loadRows() {
        List<Movie> relatedMovieList = moviesList.getMovies();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
        CardPresenter cardPresenter = new CardPresenter(300,200,false);
        /*
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.Home));
        gridRowAdapter.add(getResources().getString(R.string.Movies));
        gridRowAdapter.add(getResources().getString(R.string.Series));
        gridRowAdapter.add(getResources().getString(R.string.Kids));
        gridRowAdapter.add(getResources().getString(R.string.Shorts));
        rowsAdapter.add(new ListRow(gridRowAdapter));
*/      ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        //listRowAdapter.add( ((DetailsActivityNew)getActivity()).mSelectedMovie);
        for (int j = 0; j < 10; j++) {
                listRowAdapter.add(relatedMovieList.get(j % 5));
        }
            HeaderItem header = new HeaderItem(0, "More Movies Like This");
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
            setAdapter(mRowsAdapter);
            //rowsAdapter.add(new ListRow(listRowAdapter));
        }
    @Override
    public void setAlignment(int windowAlignOffsetFromTop) {
        super.setAlignment(100);
    }

        private void prepareBackgroundManager () {
            mBackgroundManager = BackgroundManager.getInstance(getActivity());
            mBackgroundManager.attach(getActivity().getWindow());
            Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.related_items_fragment);
            mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.black_to_transparent_shade_horizontal);
            fragment.getView().setBackground(mDefaultBackground);
            mDefaultBackground=ContextCompat.getDrawable(getActivity(), R.drawable.default_background);
            mBackgroundManager.setDrawable(mDefaultBackground);
        }

    private void setupEventListeners () {
        /*
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });
        */
        setOnItemViewClickedListener(new ItemViewClickedListener());
        //setOnItemViewSelectedListener(new ItemViewSelectedListener());
        }
        private final class ItemViewClickedListener implements OnItemViewClickedListener {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                      RowPresenter.ViewHolder rowViewHolder, Row row) {

                if (item instanceof Movie) {
                    //Movie movie = (Movie) item;
                    com.example.myottapp.models.Movie movie = (com.example.myottapp.models.Movie) item;
                    //Log.d(TAG, "Item: " + item.toString());
                    Intent intent = new Intent(getActivity(), DetailsActivityNew.class);
                    intent.putExtra(DetailsActivityNew.MOVIE, movie);

                    //Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    //        getActivity(),
                    //        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                    //        DetailsActivity.SHARED_ELEMENT_NAME)
                    //        .toBundle();
                    getActivity().startActivity(intent);

                } else if (item instanceof String) {
                    if (((String) item).contains(getString(R.string.error_fragment))) {
                        Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    private class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {

            if (item instanceof Movie) {
                ((DetailsActivityNew)getActivity()).mSelectedMovie=(Movie)item;
                System.out.println(((DetailsActivityNew)getActivity()).mSelectedMovie.getTitle());
                ((DetailsActivityNew)getActivity()).setMovieName(((Movie) item).getTitle());
                ((DetailsActivityNew)getActivity()).setMovieLanguage(((Movie) item).getLanguageName());
                ((DetailsActivityNew)getActivity()).setMovieDescription(((Movie) item).getDescription());
                ((DetailsActivityNew)getActivity()).setMovieRuntime(((Movie) item).getRunTime());
                ((DetailsActivityNew)getActivity()).setMoviePoster(((Movie) item).getPoster().getUrl());
                ((DetailsActivityNew)getActivity()).setMovieAgeRestriction(((Movie) item).getAgeRestriction());
            }
        }

    }

    }
