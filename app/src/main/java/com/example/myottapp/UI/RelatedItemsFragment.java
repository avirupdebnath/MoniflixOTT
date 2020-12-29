package com.example.myottapp.UI;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
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
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.extras.MovieList;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import static com.example.myottapp.UI.DetailsActivityNew.fromPage;
import static com.example.myottapp.UI.DetailsActivityNew.player;
import static com.example.myottapp.UI.MainFragment.moviesList;

public class RelatedItemsFragment extends RowsFragment {
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter mRowsAdapter;
    private static int count = 0;
    private boolean hasReloaded = true;
    private static int typeId;
    public Movie movie;
    public static int languageId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int CategoryId=DetailsActivityNew.relatedContent;
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
        getMovieDetails();
        if(DetailsActivityNew.fromPage.equals("Main")) {
            updateUIByCategory(CategoryId);
        }
        setupEventListeners();
    }
    public void updateUIByCategory(int CategoryId){
        getContent(DetailsActivityNew.movieBasicInfo.getType(), 3, CategoryId, 0, 10);
    }

    public void updateUIByLanguage(int typeId, int languageId){
        getContent(typeId,2,languageId,0,10);
    }

    void getMovieDetails(){
        String tag="";
        VolleyRequest volleyRequest=new VolleyRequest();
        volleyRequest.sendJSONObjGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                Gson gson=new GsonBuilder().create();
                movie = gson.fromJson(volleyRequest.getResponseString(),Movie.class);
                ((DetailsActivityNew)getActivity()).loadDetailsPage(movie);
                if(DetailsActivityNew.fromPage.equals("Search")){
                    updateUIByLanguage(DetailsActivityNew.movieBasicInfo.getType(),movie.getLanguageId());
                }
            }
            @Override
            public void onError() {
                ((DetailsActivityNew)getActivity()).refreshToken();
            }
        }, DataModel.movieDetailsByIdURL+(DetailsActivityNew.movieBasicInfo.getId()),tag);
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

    public void getContent(int contentTypeId, int filterkey, int filterValue, int pageNo, int pageSize){
        String tag="";
        VolleyRequest volleyRequest=new VolleyRequest();
        JSONObject params= volleyRequest.paramsObjectBuilder(contentTypeId,filterkey,filterValue,pageNo,pageSize);
        volleyRequest.sendPostRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                MovieBasicInfoList movieBasicInfoList=MovieBasicInfoList.parseJSON("{movieBasicInfos:"+volleyRequest.getResponseString()+"}");
                List<MovieBasicInfo> list = movieBasicInfoList.getMovieBasicInfos();
                createRow(list);
            }

            @Override
            public void onError() {

            }
        },DataModel.movieByfilterURL,params,tag);
    }

    public void createRow(List<MovieBasicInfo> list){
        mRowsAdapter.clear();
        CardPresenter cardPresenter=new CardPresenter();
        ArrayObjectAdapter categoryRowAdapter=new ArrayObjectAdapter(cardPresenter);
        for(MovieBasicInfo m:list){
            if(m.getId()!=DetailsActivityNew.movieBasicInfo.getId()){
                categoryRowAdapter.add(m);
            }
        }
        //for(int i=0;i<list.size();i++)
        //categoryRowAdapter.add(list.get(i));
        HeaderItem headerItem=new HeaderItem(0,"More Like This");
        if(list.size()!=0) {
            mRowsAdapter.add(new ListRow(headerItem, categoryRowAdapter));
            setAdapter(mRowsAdapter);
        }
    }


    @Override
    public void setAlignment(int windowAlignOffsetFromTop) {
        super.setAlignment(100);
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.related_items_fragment);
        fragment.getView().setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.transparent_to_dark_reverse_shade));
        mBackgroundManager.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.default_background));
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new ItemViewClickedListener());
        //setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof MovieBasicInfo) {
                releasePlayer();
                DetailsActivityNew.movieBasicInfo= (MovieBasicInfo) item;
                ((DetailsActivityNew)getActivity()).showOnLoadPage();
                getMovieDetails();
                if(DetailsActivityNew.fromPage.equals("Main")) updateUIByCategory(DetailsActivityNew.relatedContent);
                System.out.println("Related Content Value: "+DataModel.getCategoryIdByName(row.getHeaderItem().getName()));

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
/*
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
