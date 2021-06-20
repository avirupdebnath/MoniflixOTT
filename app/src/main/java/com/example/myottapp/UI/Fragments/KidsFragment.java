package com.example.myottapp.UI.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.Service.VolleyCallback;
import com.example.myottapp.UI.Activities.BrowseErrorActivity;
import com.example.myottapp.UI.Activities.DetailsActivity;
import com.example.myottapp.UI.Activities.DetailsActivitySeries;
import com.example.myottapp.UI.Activities.KidsActivity;
import com.example.myottapp.UI.Activities.LanguageActivity;
import com.example.myottapp.UI.Presenters.CardPresenter;
import com.example.myottapp.UI.Presenters.LanguageCardPresenter;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Language;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KidsFragment extends RowsFragment {
    private static final String TAG = "MainFragment";
    public ArrayObjectAdapter rowsAdapter;
    private BackgroundManager mBackgroundManager;
    public  static List<Language> staticLanguageList=new ArrayList<Language>();

    private void loadLanguages() {
        int CARD_WIDTH=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
        int CARD_HEIGHT=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        LanguageCardPresenter languageCardPresenter=new LanguageCardPresenter(CARD_WIDTH,CARD_HEIGHT);

        ArrayObjectAdapter languagesRowAdapter = new ArrayObjectAdapter(languageCardPresenter);
        languagesRowAdapter.addAll(0,DataModel.staticLanguageList);
        HeaderItem headerItem=new HeaderItem(0,"Languages");
        rowsAdapter.add(0,new ListRow(headerItem, languagesRowAdapter));
        setAdapter(rowsAdapter);
    }


    public void getContent(int contentID,String categoryName,int filterKey, int filterValue, int pageNo, int pageSize){
        String tag="filter";
        VolleyRequest volleyRequest=new VolleyRequest();
        JSONObject params= volleyRequest.paramsObjectBuilder(contentID,filterKey,filterValue,pageNo,pageSize);
        volleyRequest.sendPostRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                MovieBasicInfoList movieBasicInfoList=MovieBasicInfoList.parseJSON("{movieBasicInfos:"+volleyRequest.getResponseString()+"}");
                List<MovieBasicInfo> list = movieBasicInfoList.getMovieBasicInfos();
                createRow(filterValue+5,categoryName,list);
            }

            @Override
            public void onError() {
            }
        },DataModel.movieByfilterURL,params,tag);
    }

    private void addInfiniteMovies(int contentTypeId,ArrayObjectAdapter adapter, int size){
        String tag="filter";
        int pageNo=(size)/10;
        //pageNo+=1;
        System.out.println("PageNo.: "+pageNo);
        VolleyRequest volleyRequest=new VolleyRequest();
        JSONObject params= volleyRequest.paramsObjectBuilder(contentTypeId,7,0,pageNo,10);
        volleyRequest.sendPostRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                MovieBasicInfoList movieBasicInfoList=MovieBasicInfoList.parseJSON("{movieBasicInfos:"+volleyRequest.getResponseString()+"}");
                List<MovieBasicInfo> list = movieBasicInfoList.getMovieBasicInfos();
                adapter.addAll(size+1,list);
            }

            @Override
            public void onError() {
            }
        },DataModel.movieByfilterURL,params,tag);
    }

    public void createRow(int categoryId, String headerName, List<MovieBasicInfo> list){
        int CARD_WIDTH=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int CARD_HEIGHT=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        CardPresenter cardPresenter =new CardPresenter(CARD_WIDTH,CARD_HEIGHT,false);
        ArrayObjectAdapter categoryRowAdapter=new ArrayObjectAdapter(cardPresenter);
        categoryRowAdapter.addAll(0,list);
        HeaderItem headerItem=new HeaderItem(categoryId,headerName);
        if(list.size()!=0) {
            rowsAdapter.add(new ListRow(headerItem, categoryRowAdapter));
            setAdapter(rowsAdapter);
        }
    }


    public void getKidsMovies(int pageNo){
        getContent(1,"Kids and Family Movies",7,0,pageNo,10);
    }
    public void getKidsSeries(int pageNo){
        getContent(2,"Kids and Family Series",7,0,pageNo,10);
    }
    public void getKidsShorts(int pageNo){
        getContent(8,"Kids and Family Shorts",7,0,pageNo,10);
    }
    public void getKidsTVShows(int pageNo){
        getContent(9,"Kids and Family TV Shows",7,0,pageNo,10);
    }
    public void getKidsClips(int pageNo){
        getContent(10,"Kids and Family Clips",7,0,pageNo,10);
    }
    public void getKidsWebSeries(int pageNo){
        getContent(11,"Kids and Family WebSeries",7,0,pageNo,10);
    }

    public void loadUIElements(){
        loadLanguages();
        getKidsMovies(1);
        getKidsSeries(1);
        getKidsShorts(1);
        getKidsTVShows(1);
        getKidsClips(1);
        getKidsWebSeries(1);
        //getWatchHistory();
        //getWatchlist();
        //getLatestMovies();
        //getTopRatedMovies();
        //getComingSoonMovies();
        //getCategories();  //loads movie rows on success of retrieving categories.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
        setAdapter(rowsAdapter);
        loadUIElements();
        setupEventListeners();
    }


    @Override
    public void setAlignment(int windowAlignOffsetFromTop) {
        int dp= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        super.setAlignment(dp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareBackgroundManager();
    }

    private void prepareBackgroundManager () {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.main_browse_fragment);
        fragment.getView().setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.transparent_to_dark_reverse_shade));
        mBackgroundManager.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.default_background));
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new KidsFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new KidsFragment.ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof MovieBasicInfo) {
                if(((MovieBasicInfo) item).getType()==1||((MovieBasicInfo) item).getType()==8) {
                    HeaderItem headerItem=row.getHeaderItem();
                    int categoryID =((int) headerItem.getId())-5;
                    String tag = ((MovieBasicInfo) item).getId() + "";
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(DetailsActivity.MOVIE, ((MovieBasicInfo) item));
                    intent.putExtra("relatedContent", categoryID);
                    intent.putExtra("fromPage", "Main");
                    //System.out.println("Related Content Value: " + DataModel.getCategoryIdByName(row.getHeaderItem().getName()));
                    getActivity().startActivity(intent);
                    System.out.println(tag);
                }else if(((MovieBasicInfo) item).getType()==2){
                    String tag=((MovieBasicInfo) item).getId()+"";
                    System.out.println(tag);
                    Intent intent = new Intent(getActivity(), DetailsActivitySeries.class);
                    intent.putExtra(DetailsActivitySeries.SERIES, ((MovieBasicInfo)item));
                    getActivity().startActivity(intent);
                }
            }
            else if(item instanceof Language){
                Intent intent=new Intent(getActivity(), LanguageActivity.class);
                intent.putExtra(LanguageActivity.LANGUAGE,((Language)item));
                getActivity().startActivity(intent);
            }
            else if (item instanceof String) {
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

            if(rowsAdapter.indexOf(row)==0){
                ((KidsActivity)getActivity()).showCarousal();
                ((KidsActivity)getActivity()).hideMovieDetails();
                ((KidsActivity) getActivity()).collapseLanguageRow();
            }
            if(rowsAdapter.indexOf(row)==1){
                ((KidsActivity)getActivity()).hideCarousal();
                ((KidsActivity)getActivity()).showMovieDetails();
                ((KidsActivity) getActivity()).expandLanguageRow();
            }

            if (item instanceof MovieBasicInfo) {

                //Code For Inifinite Scroll
                System.out.println("MOVIE ID: "+((MovieBasicInfo) item).getId());
                int index=rowsAdapter.indexOf(row);
                HeaderItem headerItem=row.getHeaderItem();
                ArrayObjectAdapter adapter= (ArrayObjectAdapter) ((ListRow) rowsAdapter.get(index)).getAdapter();
                if(adapter.get(adapter.size()-1).equals(item))
                    addInfiniteMovies(((MovieBasicInfo) item).getType(),adapter,adapter.size()-1);

                //Code for updating images and description
                ((KidsActivity) getActivity()).setMovieName(((MovieBasicInfo) item).getTitle());
                ((KidsActivity) getActivity()).setMoviePoster(((MovieBasicInfo) item).getPosterUrl());
                ((KidsActivity) getActivity()).setYearOfProduction(((MovieBasicInfo) item).getYearOfProduction());
                if(((MovieBasicInfo) item).getType()==8) {
                    //((MainActivity)getActivity()).setMovieLanguage(((MovieBasicInfo) item).getLanguageName());
                    String description = ((MovieBasicInfo) item).getDescription();
                    if ((description.length() <= 303)) {
                        ((KidsActivity) getActivity()).setMovieDescription(description);
                    } else {
                        ((KidsActivity) getActivity()).setMovieDescription(description.substring(0, 300) + "...");
                    }
                    ((KidsActivity) getActivity()).setMovieRuntime(((MovieBasicInfo) item).getRunTime());
                    ((KidsActivity) getActivity()).setMovieAgeRestriction(((MovieBasicInfo) item).getAgeRestriction() + "+");
                }
            }
            //if (item instanceof Movie) {
            //  mBackgroundUri = ((Movie) item).getBackgroundImageUrl();
            // startBackgroundTimer();
            //}
        }
    }
}