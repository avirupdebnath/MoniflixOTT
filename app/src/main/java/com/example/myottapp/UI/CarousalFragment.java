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
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.example.myottapp.models.MovieDetailsBanner;
import com.example.myottapp.models.MovieDetailsBannerList;

import org.json.JSONArray;

import java.util.List;

import static com.example.myottapp.UI.MainFragment.moviesList;

public class CarousalFragment extends RowsFragment {
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter mRowsAdapter;
    public static MovieBasicInfoList movieBasicInfoList;

    public static RequestQueue requestQueue;

    void callGetCarousalRequest(int configId) {
        String config=configId+"";
        VolleyRequest carousalRequest = new VolleyRequest();
        carousalRequest.sendGetRequest(() -> {
            DataModel.carousalItems= MovieDetailsBannerList.parseJSON("{movieDetailsBanners:"+carousalRequest.getResponseString()+"}");
            System.out.println("LOADING ROWS...............");
            System.out.println("CAROUSAL ITEMS:"+DataModel.carousalItems);
            loadRows();
            setupEventListeners();
        }, DataModel.carousalURL+config, DataModel.carousalTAG);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        callGetCarousalRequest(1);
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

    private void loadRows() {
        System.out.println(DataModel.carousalItems);
        List<MovieDetailsBanner> carousalList = DataModel.carousalItems.getMovieDetailsBanners();
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL));
        BigCardPresenter cardPresenter = new BigCardPresenter();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        listRowAdapter.addAll(0,carousalList);
        mRowsAdapter.add(0,new ListRow(listRowAdapter));
        setAdapter(mRowsAdapter);
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
                //Movie movie = (Movie) item;
                //com.example.myottapp.models.Movie movie = (com.example.myottapp.models.Movie) item;
                //Log.d(TAG, "Item: " + item.toString());
                //Intent intent = new Intent(getActivity(), DetailsActivityNew.class);
                //intent.putExtra(DetailsActivityNew.MOVIE, movie);

                //Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                //        getActivity(),
                //        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                //        DetailsActivity.SHARED_ELEMENT_NAME)
                //        .toBundle();
                //getActivity().startActivity(intent);

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

           /* if(item instanceof MovieDetailsBanner) {
                System.out.println("Inside carousal");
                ((MainActivity) getActivity()).collapseLanguageRow();
            }

            */
        }
    }

}

