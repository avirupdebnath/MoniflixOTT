package com.example.myottapp.UI;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
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
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.example.myottapp.models.Series;

import java.util.List;

public class CarousalFragment extends RowsFragment {
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter mRowsAdapter;


    public void getCarousal(int configId,String activityName){
        String config=configId+"";
        VolleyRequest volleyRequest=new VolleyRequest();
        volleyRequest.sendGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                MovieBasicInfoList movieBasicInfoList=MovieBasicInfoList.parseJSON("{movieBasicInfos:"+volleyRequest.getResponseString()+"}");
                List<MovieBasicInfo> list = movieBasicInfoList.getMovieBasicInfos();
                createRow(list);
                if(activityName.equals("Main"))((MainActivity)getActivity()).hideOnLoadPage();
                else if (activityName.equals("Movie"))((MovieActivity)getActivity()).hideOnLoadPage();
                else if (activityName.equals("Series"))((SeriesActivity)getActivity()).hideOnLoadPage();
            }
            @Override
            public void onError() {
            }
        },DataModel.carousalURL+config,DataModel.carousalTAG);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //getCarousal(DataModel.configId);
        setupEventListeners();
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
    }


    public void createRow(List<MovieBasicInfo> list){
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL));
        BigCardPresenter cardPresenter = new BigCardPresenter();
        ArrayObjectAdapter categoryRowAdapter=new ArrayObjectAdapter(cardPresenter);
        categoryRowAdapter.addAll(0,list);
        System.out.println(list);
        if(list.size()!=0) {
            mRowsAdapter.add(new ListRow(categoryRowAdapter));
            setAdapter(mRowsAdapter);
        }
    }

    private void prepareBackgroundManager () {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.carousal_fragment);
        mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.default_background);
        fragment.getView().setBackground(mDefaultBackground);
        mBackgroundManager.setDrawable(mDefaultBackground);
    }




    private void setupEventListeners () {
        setOnItemViewClickedListener(new CarousalFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new CarousalFragment.ItemViewSelectedListener());
    }
    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof MovieBasicInfo) {
                String tag=((MovieBasicInfo) item).getId()+"";
                Intent intent = new Intent(getActivity(), DetailsActivityNew.class);
                intent.putExtra(DetailsActivityNew.MOVIE, ((MovieBasicInfo)item));
                intent.putExtra("fromPage","Search");
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
    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {
            /*
            if(item instanceof MovieBasicInfo) {
                System.out.println("Inside carousal");
                ((SeriesActivity)getActivity()).collapseLanguageRow();
            }*/

        }
    }

}

