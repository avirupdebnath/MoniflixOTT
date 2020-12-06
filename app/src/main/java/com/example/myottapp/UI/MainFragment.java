package com.example.myottapp.UI;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.BrowseFragment;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.BrowseFrameLayout;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myottapp.R;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.extras.MovieList;
import com.example.myottapp.extras.Processor;
import com.example.myottapp.models.AllCategoriesList;
import com.example.myottapp.models.AllLanguagesList;
import com.example.myottapp.models.Language;
import com.example.myottapp.models.Movie;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class MainFragment extends RowsFragment {
    private static final String TAG = "MainFragment";

    //super.onActivityCreated(savedInstanceState);
//initializeModels();

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static int response_error_count=0;
    private static int NUM_ROWS = 5;
    private static int NUM_COLS = 15;

    static int count=0;

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
    public static List<Language> staticLanguageList=new ArrayList<Language>();
    public static boolean responseFlagCategories=false;
    public static boolean responseFlagMovies=false;
    public static RequestQueue requestQueue;
    public void getCategories(final VolleyCallback callback,RequestQueue requestQueue){

        System.out.println("API call For Categories...");
        //RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String urlCategories="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllCategories";

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlCategories,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("API response received For Categories...");

                        allCategoriesList=AllCategoriesList.parseJSON("{categories:"+response+"}");
                        Log.e("Rest Response",response.toString());
                        System.out.println(allCategoriesList);
                        MovieList.MOVIE_CATEGORY=allCategoriesList.getCategoryNameArray();
                        NUM_ROWS=MovieList.MOVIE_CATEGORY.length;
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("API response received For Categories...");
                    }
                }
        );
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }
    public void getLanguagesList(){
        String []languageNames={"Bengali","English","Hindi","Kannada","Korean","Marathi","Russian","Tamil","Telugu"};
        for (int i=0;i<languageNames.length;i++){
            staticLanguageList.add(new Language(i,languageNames[i]));
        }
    }
/*
    public void getLanguages(final VolleyCallback callback, RequestQueue requestQueue){
        //RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String urlLanguages= "https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllLanguages";
        JsonArrayRequest jsonArrayRequest2=new JsonArrayRequest(
                Request.Method.GET,
                urlLanguages,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        allLanguagesList= AllLanguagesList.parseJSON("{languages:"+String.valueOf(response)+"}");
                        Log.e("Rest Response",response.toString());
                        System.out.println(allLanguagesList);
                        languages=allLanguagesList.getLanguagesArray();
                        System.out.println(languages[0]);
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        jsonArrayRequest2.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest2);
    }
 */
    public void getMovies(final VolleyCallback callback, RequestQueue requestQueue){
        System.out.println("API call For All Movies...");

        //RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url= "https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Movie/GetAll";
        JsonArrayRequest jsonArrayRequest2=new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("API response received For All Movies...");
                        moviesList= com.example.myottapp.models.MovieList.parseJSON("{movies:"+String.valueOf(response)+"}");
                        Log.e("Rest Response",response.toString());
                        System.out.println(moviesList);
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        jsonArrayRequest2.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest2);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setHeadersState(HEADERS_DISABLED);
        Log.i(TAG, "onCreate");
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        prepareBackgroundManager();
        setupUIElements();
        getLanguagesList();
        callGetMoviesRequest();
        callGetCategoriesRequest();
        /*getCategories(() -> getMovies(()->{
            loadRows();
            setupEventListeners();
            //workaroundFocus();
        }));*/
        //getCategories(new VolleyCallback() {
        //    @Override
        //    public void onSuccess() {
        //        responseFlagCategories=true;
        //    }
        //},requestQueue);
        //getMovies(new VolleyCallback() {
        //    @Override
        //    public void onSuccess() {
        //        responseFlagMovies=true;
        //    }
        //},requestQueue);
        //renderUI();
        ////workaroundFocus();
    }

    void callGetCategoriesRequest() {
        requestQueue.cancelAll("Cancelling all requests");
        getCategories(new VolleyCallback() {
            @Override
            public void onSuccess() {
                if (allCategoriesList==null && responseFlagMovies) callGetCategoriesRequest();
                else if (allCategoriesList==null && !responseFlagMovies){
                    requestQueue.cancelAll("Cancelling all requests");
                    callGetMoviesRequest();
                    callGetCategoriesRequest();
                }else if (responseFlagMovies){
                    loadRows();
                    setupEventListeners();
                }
                else responseFlagCategories = true;
            }
        }, requestQueue);
    }
    void callGetMoviesRequest(){
        getMovies(new VolleyCallback() {
            @Override
            public void onSuccess() {
                if (moviesList==null && responseFlagCategories) callGetMoviesRequest();
                else if (moviesList==null && !responseFlagCategories){
                    requestQueue.cancelAll("Cancelling all requests");
                    callGetMoviesRequest();
                    callGetCategoriesRequest();
                }
                else if(responseFlagCategories){
                    loadRows();
                    setupEventListeners();
                }
                else responseFlagMovies=true;
            }
        },requestQueue);
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        FrameLayout containerDock = (FrameLayout) view.findViewById(R.id.main_browse_fragment);
        FrameLayout.MarginLayoutParams params = (FrameLayout.MarginLayoutParams) containerDock.getLayoutParams();
        Resources resources = inflater.getContext().getResources();
        int newHeaderMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, resources.getDisplayMetrics());
        int offsetToZero = 10;
        params.topMargin = offsetToZero+newHeaderMargin;
        containerDock.setLayoutParams(params);
        return view;
    }
*/
    @Override
    public void onResume() {
        super.onResume();
        mBackgroundManager.setDrawable(mDefaultBackground);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //if (null != mBackgroundTimer) {
          //  Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
          //  mBackgroundTimer.cancel();
        //}
    }

    private ArrayObjectAdapter loadLanguages() {
        //ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
        LanguageCardPresenter languageCardPresenter=new LanguageCardPresenter();
        ArrayObjectAdapter languagesRowAdapter = new ArrayObjectAdapter(languageCardPresenter);
        for(int n=0;n<staticLanguageList.size();n++){
            languagesRowAdapter.add(staticLanguageList.get(n));
        }
        //rowsAdapter.add(new ListRow(languagesRowAdapter));
        return languagesRowAdapter;
        /*
        GridItemPresenter mGridPresenter = new GridItemPresenter(200,200,ContextCompat.getColor(getActivity(),R.color.language_card));
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        if(languages.length!=0) {
            for (int i = 0; i < languages.length; i++) {
                gridRowAdapter.add(languages[i]);
                //setAdapter(gridRowAdapter);
            }
        }
            //rowsAdapter.add(new ListRow(gridRowAdapter));
        return gridRowAdapter;

         */
       // setOnItemViewClickedListener(new BannerFragment.ItemViewClickedListener());
    }

    private void loadRows() {
        List<com.example.myottapp.models.Movie> list = moviesList.getMovies();

        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
        CardPresenter cardPresenter = new CardPresenter();
        BigCardPresenter bigCardPresenter=new BigCardPresenter();
        /*
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.Home));
        gridRowAdapter.add(getResources().getString(R.string.Movies));
        gridRowAdapter.add(getResources().getString(R.string.Series));
        gridRowAdapter.add(getResources().getString(R.string.Kids));
        gridRowAdapter.add(getResources().getString(R.string.Shorts));
        rowsAdapter.add(new ListRow(gridRowAdapter));
*/
        ArrayObjectAdapter firstListRowAdapter = new ArrayObjectAdapter(bigCardPresenter);
        for(int m=0; m < NUM_COLS; m++){
            firstListRowAdapter.add(list.get(m % 5));
        }
        rowsAdapter.add(new ListRow(firstListRowAdapter));

        HeaderItem headerItem=new HeaderItem(1,"Languages");
        rowsAdapter.add(new ListRow(headerItem, loadLanguages()));

        int i;
        for (i = 0; i < NUM_ROWS; i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                listRowAdapter.add(list.get(j % 5));
            }
            HeaderItem header = new HeaderItem(i+1, MovieList.MOVIE_CATEGORY[i]);
            rowsAdapter.add(new ListRow(header, listRowAdapter));
            //rowsAdapter.add(new ListRow(listRowAdapter));
        }

       // HeaderItem gridHeader = new HeaderItem(i, "PREFERENCES");
        /*
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.grid_view));
        gridRowAdapter.add(getString(R.string.error_fragment));
        gridRowAdapter.add(getResources().getString(R.string.personal_settings));
        rowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));
        */
        setAdapter(rowsAdapter);
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());

        mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.default_background);
        mBackgroundManager.setDrawable(mDefaultBackground);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        //setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        //setHeadersState(HEADERS_DISABLED);
        //setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        //setBrandColor(ContextCompat.getColor(getActivity(), R.color.fastlane_background));
        // set search icon color
        //setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));
    }

    private void setupEventListeners() {
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
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }
/*
    private void updateBackground(String uri) {
            new MainActivity().updateImageView(uri);
        /*
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;

        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();

    }


    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        //mBackgroundTimer = new Timer();
        //mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }
*/
    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                //Movie movie = (Movie) item;
                com.example.myottapp.models.Movie movie=(com.example.myottapp.models.Movie) item;
                //Log.d(TAG, "Item: " + item.toString());
                /*
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);
    */
                Intent intent = new Intent(getActivity(), DetailsActivityNew.class);
                intent.putExtra(DetailsActivityNew.MOVIE, movie);

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
            //if (item instanceof Movie) {
              //  mBackgroundUri = ((Movie) item).getBackgroundImageUrl();
               // startBackgroundTimer();
            //}
        }
    }
   /*
    public void workaroundFocus(){
        if(getView() != null) {
            View viewToFocus  = getView().findViewById(R.id.grid_frame);
            BrowseFrameLayout browseFrameLayout = getView().findViewById(androidx.leanback.R.id.browse_frame);
            browseFrameLayout.setOnFocusSearchListener((focused, direction) -> {
                if (direction == View.FOCUS_UP) {
                    return viewToFocus;
                }
                else {
                    return null;
                }
            });
        }
    }

    */

/*
    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateBackground(mBackgroundUri);
                }
            });
        }
    }
*/
}