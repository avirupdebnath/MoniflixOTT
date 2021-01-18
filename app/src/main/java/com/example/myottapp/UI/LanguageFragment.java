package com.example.myottapp.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.Service.VolleyCallback;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Language;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;

import org.json.JSONObject;

import java.util.List;

public class LanguageFragment extends RowsFragment {
    private static int position = 1;
    private static final String TAG = "BannerFragment";
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter rowsAdapter;
    private int languageId;
    private String languageName;
    private static boolean anyRequestReceivedFlag;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        //gridPresenter.setNumberOfColumns(3);
        //setGridPresenter(gridPresenter);
        CustomListRowPresenter listRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_SMALL, position);
        listRowPresenter.setShadowEnabled(false);
        anyRequestReceivedFlag=false;
        rowsAdapter = new ArrayObjectAdapter(listRowPresenter);
        // loadBanner();
        setupEventListeners();
        languageId=((LanguageActivity)getActivity()).getLanguageId();
        languageName=((LanguageActivity)getActivity()).getLanguageName();
        generateRequestsByContentType(languageId);

    }


    public void sendGetByLanguageRequest(int contentTypeId,String contentType, int filterValue,int pageNo) {
        String tag="";
        VolleyRequest volleyRequest=new VolleyRequest();
        JSONObject params= volleyRequest.paramsObjectBuilder(contentTypeId,2,filterValue,pageNo,10);
        volleyRequest.sendPostRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                MovieBasicInfoList movieBasicInfoList=MovieBasicInfoList.parseJSON("{movieBasicInfos:"+volleyRequest.getResponseString()+"}");
                List<MovieBasicInfo> list = movieBasicInfoList.getMovieBasicInfos();
                createRow(contentTypeId,contentType,list);
            }

            @Override
            public void onError() {

            }
        },DataModel.movieByfilterURL,params,tag);
    }

    public void generateRequestsByContentType(int filterValue){
        //Get Movies
        sendGetByLanguageRequest(1,"Movies",filterValue,0);
        //Get Series
        sendGetByLanguageRequest(2,"Series",filterValue,0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //workaroundFocus();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setTitle("Displaying content in \"");
    }

    public void createRow(int contentTypeId, String contentType, List<MovieBasicInfo> list){
        CardPresenter cardPresenter=new CardPresenter();
        ArrayObjectAdapter languageRowAdapter=new ArrayObjectAdapter(cardPresenter);
        languageRowAdapter.addAll(0,list);
        HeaderItem headerItem=new HeaderItem(contentTypeId,contentType);
        if(list.size()!=0) {
            anyRequestReceivedFlag=true;
            rowsAdapter.add(new ListRow(headerItem, languageRowAdapter));
            setAdapter(rowsAdapter);
            ((LanguageActivity)getActivity()).hideLoadFrame();
            ((LanguageActivity)getActivity()).hideNoResultsFrame();
            ((LanguageActivity)getActivity()).showResultsFrame();
        }
        else if(!anyRequestReceivedFlag){
            ((LanguageActivity)getActivity()).hideLoadFrame();
            ((LanguageActivity)getActivity()).hideResultsFrame();
            ((LanguageActivity)getActivity()).showNoResultsFrame();
        }
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new LanguageFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new LanguageFragment.ItemViewSelectedListener());
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());

        mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.text_black_to_transparent_shade);
        mBackgroundManager.setDrawable(mDefaultBackground);
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
            if (item instanceof MovieBasicInfo) {
                ((LanguageActivity)getActivity()).setMovieName(((MovieBasicInfo) item).getTitle());
                //((LanguageActivity)getActivity()).setMovieLanguage(((Movie) item).getLanguageName());
                String description=((MovieBasicInfo) item).getDescription();
                if ((description.length() <= 303)) {
                    ((LanguageActivity) getActivity()).setMovieDescription(description);
                } else {
                    ((LanguageActivity) getActivity()).setMovieDescription(description.substring(0, 300) + "...");
                }
                ((LanguageActivity)getActivity()).setMovieRuntime(((MovieBasicInfo) item).getRunTime());
                ((LanguageActivity)getActivity()).setMoviePoster(((MovieBasicInfo) item).getPosterUrl());
                ((LanguageActivity)getActivity()).setMovieAgeRestriction(((MovieBasicInfo) item).getAgeRestriction()+"+");

            }
            //if (item instanceof Movie) {
            //  mBackgroundUri = ((Movie) item).getBackgroundImageUrl();
            // startBackgroundTimer();
            //}
        }
    }


}
