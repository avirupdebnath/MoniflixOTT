package com.example.myottapp.UI.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

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

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.Service.VolleyCallback;
import com.example.myottapp.UI.Activities.BrowseErrorActivity;
import com.example.myottapp.UI.Activities.DetailsActivity;
import com.example.myottapp.UI.Activities.LanguageActivity;
import com.example.myottapp.UI.Activities.MovieActivity;
import com.example.myottapp.UI.Presenters.CardPresenter;
import com.example.myottapp.UI.Presenters.CustomCardViewPresenter;
import com.example.myottapp.UI.Presenters.LanguageCardPresenter;
import com.example.myottapp.models.AllCategoriesList;
import com.example.myottapp.models.AllLanguagesList;
import com.example.myottapp.models.Category;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Language;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.example.myottapp.models.SessionManager;
import com.example.myottapp.models.WatchHistoryContentDetails;
import com.example.myottapp.models.WatchHistoryContentDetailsList;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MovieFragment extends RowsFragment {
    private static final String TAG = "MainFragment";
    private static boolean expanded=false;

    //super.onActivityCreated(savedInstanceState);
//initializeModels();

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static int response_error_count=0;
    private static int NUM_ROWS = 5;
    private static int NUM_COLS = 15;


    static boolean reload=false;
    public ArrayObjectAdapter rowsAdapter;

    private static String[] languages;
    private ProgressBar progressBar;
    private final Handler mHandler = new Handler();
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;
    public static AllCategoriesList allCategoriesList;
    public static AllLanguagesList allLanguagesList;
    public static com.example.myottapp.models.MovieList moviesList;
    public  static List<Language> staticLanguageList=new ArrayList<Language>();
    public static boolean responseFlagCategories=false;
    public static boolean responseFlagMovies=false;


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

    public void createWatchHistoryRow(List<WatchHistoryContentDetails> watchHistory){
        int CARD_WIDTH=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int CARD_HEIGHT=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
        CustomCardViewPresenter cardViewPresenter=new CustomCardViewPresenter(CARD_WIDTH,CARD_HEIGHT);
        ArrayObjectAdapter adapter=new ArrayObjectAdapter(cardViewPresenter);
        for(WatchHistoryContentDetails w: watchHistory){
            if(w.getType()==1){
                adapter.add(w);
            }
        }
        if(adapter.size()!=0){
            HeaderItem headerItem=new HeaderItem(1,"Continue Watching");
            rowsAdapter.add(1,new ListRow(headerItem,adapter));
            setAdapter(rowsAdapter);
        }
    }
    public void getWatchHistory(){
        VolleyRequest volleyRequest=new VolleyRequest();
        volleyRequest.sendGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                WatchHistoryContentDetailsList watchHistoryContentDetailsList=WatchHistoryContentDetailsList.parseJSON("{watchHistoryContentDetails:"+volleyRequest.getResponseString()+"}");
                List<WatchHistoryContentDetails> list = watchHistoryContentDetailsList.getWatchHistoryContentDetails();
                createWatchHistoryRow(list);
            }
            @Override
            public void onError() {
            }
        },DataModel.trackUserWatchHistoryURL,"filter");
    }
    public void getWatchlist(){
        try {
            DataModel.watchlist= MovieBasicInfoList.parseJSON(SessionManager.sharedPreferences.getString("WATCHLIST",null)).getMovieBasicInfos();
            List<MovieBasicInfo> mylist=new ArrayList<>();
            for(MovieBasicInfo m:DataModel.watchlist){
                if(m.getType()==1) mylist.add(m);
            }
            createRow(2,"My List",mylist);
        }catch(Exception e){ }
    }
    public void getComingSoonMovies(){
        getMovies("Coming Soon",5,0,1,10,3);
    }
    public void getLatestMovies(){
        getMovies("Latest Movies",4,0,1,10,4);
    }
    public void getTopRatedMovies(){
        getMovies("Top Rated Movies",6,0,1,10,5);
    }


    public void getCategories(){
        VolleyRequest volleyRequest=new VolleyRequest();
        volleyRequest.sendGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                AllCategoriesList allCategoriesList=AllCategoriesList.parseJSON("{categories:"+volleyRequest.getResponseString()+"}");
                DataModel.CategoriesList=allCategoriesList.getCategories();
                System.out.println(DataModel.CategoriesList);
                loadMovieRows();
            }
            @Override
            public void onError() {
            }
        },DataModel.movieCategoriesURL,"");
    }

    public void getMovies(String categoryName, int filterValue, int pageNo, int pageSize){
        String tag=categoryName;
        VolleyRequest volleyRequest=new VolleyRequest();
        JSONObject params= volleyRequest.paramsObjectBuilder(1,3,filterValue,pageNo,pageSize);
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
    public void getMovies(String categoryName,int filterKey, int filterValue, int pageNo, int pageSize,int rowID){
        String tag="filter";
        VolleyRequest volleyRequest=new VolleyRequest();
        JSONObject params= volleyRequest.paramsObjectBuilder(1,filterKey,filterValue,pageNo,pageSize);
        volleyRequest.sendPostRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                MovieBasicInfoList movieBasicInfoList=MovieBasicInfoList.parseJSON("{movieBasicInfos:"+volleyRequest.getResponseString()+"}");
                List<MovieBasicInfo> list = movieBasicInfoList.getMovieBasicInfos();
                createRow(rowID,categoryName,list);
            }
            @Override
            public void onError() {
            }
        },DataModel.movieByfilterURL,params,tag);
    }
    private void addInfiniteMovies(ArrayObjectAdapter adapter, int categoryId, int size){
        String tag="";
        int pageNo=size/10;
        VolleyRequest volleyRequest=new VolleyRequest();
        JSONObject params= volleyRequest.paramsObjectBuilder(1,3,categoryId,pageNo,10);
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

    public void createRow(int categoryID, String categoryName, List<MovieBasicInfo> list){
        int CARD_WIDTH=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int CARD_HEIGHT=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        CardPresenter cardPresenter =new CardPresenter(CARD_WIDTH,CARD_HEIGHT,false);
        ArrayObjectAdapter categoryRowAdapter=new ArrayObjectAdapter(cardPresenter);
        //for(int i=0;i<list.size();i++)
        //categoryRowAdapter.add(list.get(i));
        categoryRowAdapter.addAll(0,list);
        HeaderItem headerItem=new HeaderItem(categoryID,categoryName);
        if(list.size()!=0) {
            rowsAdapter.add(new ListRow(headerItem, categoryRowAdapter));
            setAdapter(rowsAdapter);
        }
    }


    public void loadMovieRows(){
        for(Category c: DataModel.CategoriesList){
            getMovies(c.getName()+" Movies",c.getId(),0,10);
        }
    }

    public void loadUIElements(){
        getCategories();  //loads movie rows on success of retrieving categories.
        loadLanguages();
        getWatchHistory();
        getWatchlist();
        getLatestMovies();
        getTopRatedMovies();
        getComingSoonMovies();
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
    public void onResume() {
        super.onResume();
        mBackgroundManager.setDrawable(mDefaultBackground);
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
        setOnItemViewClickedListener(new MovieFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new MovieFragment.ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof MovieBasicInfo) {
                HeaderItem headerItem=row.getHeaderItem();
                int categoryID =((int) headerItem.getId())-5;
                String tag=((MovieBasicInfo) item).getId()+"";
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, ((MovieBasicInfo)item));
                intent.putExtra("relatedContent", categoryID);
                intent.putExtra("fromPage","Main");
                getActivity().startActivity(intent);
                System.out.println(tag);
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
                ((MovieActivity)getActivity()).showCarousal();
                ((MovieActivity)getActivity()).hideMovieDetails();
                ((MovieActivity) getActivity()).collapseLanguageRow();
            }
            if(rowsAdapter.indexOf(row)==1){
                ((MovieActivity)getActivity()).hideCarousal();
                ((MovieActivity)getActivity()).showMovieDetails();
                ((MovieActivity) getActivity()).expandLanguageRow();
            }
/*
            if(item instanceof Language){
                System.out.println("Inside Language");
                ((MainActivity) getActivity()).expandLanguageRow();
            }

 */
            if (item instanceof MovieBasicInfo) {

                //Code For Inifinite Scroll
                System.out.println("MOVIE ID: "+((MovieBasicInfo) item).getId());
                int index=rowsAdapter.indexOf(row);
                HeaderItem headerItem=row.getHeaderItem();
                int categoryID=0;
                categoryID =((int) headerItem.getId())-5;
                System.out.println(categoryID);
                ArrayObjectAdapter adapter= (ArrayObjectAdapter) ((ListRow) rowsAdapter.get(index)).getAdapter();
                System.out.println("Row No. : "+index+"Adapter size"+adapter.size());
                if(adapter.get(adapter.size()-1).equals(item))
                    addInfiniteMovies(adapter,categoryID,adapter.size()-1);


                //Code for updating images and description
                ((MovieActivity)getActivity()).setMovieName(((MovieBasicInfo) item).getTitle());
                //((MovieActivity)getActivity()).setMovieLanguage(((MovieBasicInfo) item).getLanguageName());
                String description=((MovieBasicInfo) item).getDescription();
                if ((description.length() <= 303)) {
                    ((MovieActivity) getActivity()).setMovieDescription(description);
                } else {
                    ((MovieActivity) getActivity()).setMovieDescription(description.substring(0, 300) + "...");
                }
                ((MovieActivity)getActivity()).setMovieRuntime(((MovieBasicInfo) item).getRunTime());
                ((MovieActivity)getActivity()).setMoviePoster(((MovieBasicInfo) item).getPosterUrl());
                ((MovieActivity)getActivity()).setYearOfProduction(((MovieBasicInfo) item).getYearOfProduction());
                ((MovieActivity)getActivity()).setMovieAgeRestriction(((MovieBasicInfo) item).getAgeRestriction());

            }
            //if (item instanceof Movie) {
            //  mBackgroundUri = ((Movie) item).getBackgroundImageUrl();
            // startBackgroundTimer();
            //}
        }
    }
}