package com.example.myottapp.UI.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.Service.VolleyCallback;
import com.example.myottapp.UI.Activities.BrowseErrorActivity;
import com.example.myottapp.UI.Activities.DetailsActivity;
import com.example.myottapp.UI.Activities.DetailsActivitySeries;
import com.example.myottapp.UI.Presenters.BigCardPresenter;
import com.example.myottapp.UI.Presenters.EpisodesCardPresenter;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Episodes;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.Series;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import static com.example.myottapp.UI.Activities.DetailsActivity.player;

public class EpisodesFragment extends RowsFragment {
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter mRowsAdapter;
    private static int count = 0;
    private boolean hasReloaded = true;
    private static int typeId;
    public Series series;
    public static int languageId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
        getSeriesDetails();
        setupEventListeners();
    }

    void getSeriesDetails(){
        String tag="";
        VolleyRequest volleyRequest=new VolleyRequest();
        volleyRequest.sendJSONObjGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                Gson gson=new GsonBuilder().create();
                series = gson.fromJson(volleyRequest.getResponseString(),Series.class);
                ((DetailsActivitySeries)getActivity()).setSeasonSpinner(series);

            }
            @Override
            public void onError() {
                ((DetailsActivitySeries)getActivity()).refreshToken();
            }
        }, DataModel.seriesDetailsByIdURL+(DetailsActivitySeries.movieBasicInfo.getId()),tag);
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


    public void createRow(List<Episodes> list){
        mRowsAdapter.clear();
        int CARD_WIDTH=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1000, getResources().getDisplayMetrics());
        int CARD_HEIGHT=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420, getResources().getDisplayMetrics());
        EpisodesCardPresenter episodesCardPresenter =new EpisodesCardPresenter(CARD_WIDTH,CARD_HEIGHT,false);
        ArrayObjectAdapter categoryRowAdapter=new ArrayObjectAdapter(episodesCardPresenter);
        categoryRowAdapter.addAll(0,list);
        //for(int i=0;i<list.size();i++)
        //categoryRowAdapter.add(list.get(i));
        HeaderItem headerItem=new HeaderItem(0,"Episodes");
        if(list.size()!=0) {
            mRowsAdapter.add(new ListRow(headerItem, categoryRowAdapter));
            setAdapter(mRowsAdapter);
        }
    }


    @Override
    public void setAlignment(int windowAlignOffsetFromTop) {
        int dp= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        super.setAlignment(dp);
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.episodes_fragment);
        fragment.getView().setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.transparent_to_dark_reverse_shade));
        mBackgroundManager.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.default_background));
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }


    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof MovieBasicInfo) {
                releasePlayer();
                DetailsActivitySeries.movieBasicInfo= (MovieBasicInfo) item;
                ((DetailsActivity)getActivity()).showOnLoadPage();
                getSeriesDetails();

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

            if(item instanceof Episodes){
                ((DetailsActivitySeries)getActivity()).episodePage((Episodes) item);
            }
/*
            if (item instanceof Movie) {
                ((DetailsActivity)getActivity()).mSelectedMovie=(Movie)item;
                System.out.println(((DetailsActivity)getActivity()).mSelectedMovie.getTitle());
                ((DetailsActivity)getActivity()).setMovieName(((Movie) item).getTitle());
                ((DetailsActivity)getActivity()).setMovieLanguage(((Movie) item).getLanguageName());
                ((DetailsActivity)getActivity()).setMovieDescription(((Movie) item).getDescription());
                ((DetailsActivity)getActivity()).setMovieRuntime(((Movie) item).getRunTime());
                ((DetailsActivity)getActivity()).setMoviePoster(((Movie) item).getPoster().getUrl());
                ((DetailsActivity)getActivity()).setMovieAgeRestriction(((Movie) item).getAgeRestriction());
            }
        }
*/
        }

    }
    public void releasePlayer(){
        if(player!=null){
            player.stop();
            player.release();
        }
    }
}
