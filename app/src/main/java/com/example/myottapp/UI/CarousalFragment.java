package com.example.myottapp.UI;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.RowsFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myottapp.R;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.models.Movie;

import org.json.JSONArray;

import java.util.List;

import static com.example.myottapp.UI.MainFragment.moviesList;

public class CarousalFragment extends RowsFragment {
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter mRowsAdapter;
    private static int count=0;
    private boolean hasReloaded=true;
    public static boolean responseFlagCategories=false;
    public static boolean responseFlagMovies=false;
    public static RequestQueue requestQueue;
    private DisplayMetrics mMetrics;

    void callGetMoviesRequest(){
        getMovies(new VolleyCallback() {
            @Override
            public void onSuccess() {
                    loadRows();
                    setupEventListeners();
            }
        },requestQueue);
    }


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
                        System.out.println(String.valueOf(response));
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
        //loadRows();
        //setupEventListeners();
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        callGetMoviesRequest();
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
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_SMALL));
        BigCardPresenter cardPresenter = new BigCardPresenter();
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
        mRowsAdapter.add(new ListRow(listRowAdapter));
        setAdapter(mRowsAdapter);
        //rowsAdapter.add(new ListRow(listRowAdapter));
    }

    private void prepareBackgroundManager () {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.carousal_fragment);
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
        setOnItemViewClickedListener(new CarousalFragment.ItemViewClickedListener());
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

}

