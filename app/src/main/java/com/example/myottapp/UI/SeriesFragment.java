package com.example.myottapp.UI;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.myottapp.models.AllCategoriesList;
import com.example.myottapp.models.AllLanguagesList;
import com.example.myottapp.models.Category;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Language;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.example.myottapp.models.Series;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class SeriesFragment extends RowsFragment {
    private static final String TAG = "MainFragment";

    //super.onActivityCreated(savedInstanceState);
//initializeModels();

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static int response_error_count=0;
    private static int NUM_ROWS = 5;
    private static int NUM_COLS = 15;

    static int count=0;
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


    public void getLanguagesList(){
        if(staticLanguageList.size()==0) {
            for (int i = 0; i < DataModel.languageNames.length; i++) {
                staticLanguageList.add(new Language(i, DataModel.languageNames[i]));
            }
        }
    }

    private void loadLanguages() {
        LanguageCardPresenter languageCardPresenter=new LanguageCardPresenter();
        ArrayObjectAdapter languagesRowAdapter = new ArrayObjectAdapter(languageCardPresenter);
        for(int n=0;n<staticLanguageList.size();n++){
            languagesRowAdapter.add(staticLanguageList.get(n));
        }
        HeaderItem headerItem=new HeaderItem(0,"Languages");
        rowsAdapter.add(new ListRow(headerItem, languagesRowAdapter));
        setAdapter(rowsAdapter);
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
        JSONObject params= volleyRequest.paramsObjectBuilder(2,3,filterValue,pageNo,pageSize);
        volleyRequest.sendPostRequest(new VolleyCallback() {
            @Override
            public void onSuccess() {
                MovieBasicInfoList movieBasicInfoList=MovieBasicInfoList.parseJSON("{movieBasicInfos:"+volleyRequest.getResponseString()+"}");
                List<MovieBasicInfo> list = movieBasicInfoList.getMovieBasicInfos();
                createRow(filterValue,categoryName,list);
            }

            @Override
            public void onError() {

            }
        },DataModel.movieByfilterURL,params,tag);
    }


    public void createRow(int categoryID, String categoryName, List<MovieBasicInfo> list){
        CardPresenter cardPresenter=new CardPresenter();
        ArrayObjectAdapter categoryRowAdapter=new ArrayObjectAdapter(cardPresenter);
        for(int i=0;i<list.size();i++)
            categoryRowAdapter.add(list.get(i));
        HeaderItem headerItem=new HeaderItem(categoryID,categoryName);
        if(list.size()!=0) {
            rowsAdapter.add(new ListRow(headerItem, categoryRowAdapter));
            setAdapter(rowsAdapter);
        }
        SeriesActivity.maxRow=rowsAdapter.size();
    }


    public void loadMovieRows(){
        for(Category c: DataModel.CategoriesList){
            getMovies(c.getName(),c.getId(),0,10);
        }
    }

    public void loadUIElements(){
        getLanguagesList();
        getCategories();  //loads movie rows on success of retrieving categories.
        loadLanguages();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setHeadersState(HEADERS_DISABLED);
        Log.i(TAG, "onCreate");
        responseFlagCategories=false;
        responseFlagMovies=false;

        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));

        //requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //setupUIElements();
        //getLanguagesList();
        //callGetMoviesRequest();
        //getCategories();
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
        super.setAlignment(100);
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
        setOnItemViewClickedListener(new SeriesFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new SeriesFragment.ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof MovieBasicInfo) {
                String tag=((MovieBasicInfo) item).getId()+"";
                System.out.println(tag);
                //Intent intent = new Intent(getActivity(), DetailsActivityNew.class);
                //intent.putExtra(DetailsActivityNew.MOVIE, ((MovieBasicInfo)item));
                //getActivity().startActivity(intent);
            }
            else if(item instanceof Language){
                Intent intent=new Intent(getActivity(),LanguageActivity.class);
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
                ((SeriesActivity)getActivity()).showCarousal();
                ((SeriesActivity)getActivity()).hideMovieDetails();
                ((SeriesActivity) getActivity()).collapseLanguageRow();
            }
            if(rowsAdapter.indexOf(row)==1){
                ((SeriesActivity)getActivity()).hideCarousal();
                ((SeriesActivity)getActivity()).showMovieDetails();
                ((SeriesActivity) getActivity()).expandLanguageRow();
            }
            if (item instanceof MovieBasicInfo) {
                ((SeriesActivity)getActivity()).setMovieName(((MovieBasicInfo) item).getTitle());
                //((MainActivity)getActivity()).setMovieLanguage(((Movie) item).getLanguageName());

                /*
                String description=((MovieBasicInfo) item).getDescription();
                if ((description.length() <= 303)) {
                    ((SeriesActivity) getActivity()).setMovieDescription(description);
                } else {
                    ((SeriesActivity) getActivity()).setMovieDescription(description.substring(0, 300) + "...");
                }
                */
                //((SeriesActivity)getActivity()).setMovieRuntime(((MovieBasicInfo) item).getRunTime());
                ((SeriesActivity)getActivity()).setMoviePoster(((MovieBasicInfo) item).getPosterUrl());

                //((SeriesActivity)getActivity()).setMovieAgeRestriction(((MovieBasicInfo) item).getAgeRestriction()+"+");
                ((SeriesActivity)getActivity()).setYearOfProduction(((MovieBasicInfo) item).getYearOfProduction());

            }
            //if (item instanceof Movie) {
            //  mBackgroundUri = ((Movie) item).getBackgroundImageUrl();
            // startBackgroundTimer();
            //}
        }
    }
}
