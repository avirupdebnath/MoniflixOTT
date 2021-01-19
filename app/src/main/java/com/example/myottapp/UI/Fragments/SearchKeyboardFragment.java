package com.example.myottapp.UI.Fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.VerticalGridFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;
import androidx.leanback.widget.VerticalGridView;

import com.example.myottapp.R;
import com.example.myottapp.UI.Presenters.GridItemPresenter;
import com.example.myottapp.UI.Activities.SearchActivity;

public class SearchKeyboardFragment extends VerticalGridFragment {
    private static int position = 1;
    private static final String TAG = "BannerFragment";
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter rowsAdapter;
    public static String searchKey="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter(){
            @Override
            protected void initializeGridViewHolder(ViewHolder vh) {
                super.initializeGridViewHolder(vh);
                VerticalGridView gridView = vh.getGridView();
                int top= 370;//this is the new value for top padding
                int bottom = 10;
                int right = 5;
                int left = 110;
                gridView.setPadding(left,top,right,bottom);
            }
        };
        gridPresenter.setNumberOfColumns(6);
        setGridPresenter(gridPresenter);
        searchKey="";
        //CustomListRowPresenter listRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_SMALL, position);
        //listRowPresenter.setShadowEnabled(false);
        //rowsAdapter = new ArrayObjectAdapter(listRowPresenter);
        loadBanner();
        setupEventListeners();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //workaroundFocus();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadBanner() {
        GridItemPresenter mGridPresenter = new GridItemPresenter(90,65, Color.TRANSPARENT);
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);

        for(char ch='A';ch<='Z';ch++)
            gridRowAdapter.add(ch+"");
        for(int i=0;i<=9;i++)
            gridRowAdapter.add(i+"");
        gridRowAdapter.add("Space");
        gridRowAdapter.add("Delete");
        setAdapter(gridRowAdapter);


    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new SearchKeyboardFragment.ItemViewClickedListener());
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
            if(item instanceof String){
                if(item.equals("Space")){
                    searchKey+=" ";
                }
                else if(item.equals("Delete")){
                    if(searchKey.length()!=0)
                        searchKey=searchKey.substring(0,(searchKey.length()-1));
                }
                else{
                    searchKey+=((String) item).toLowerCase();
                }
                ((SearchActivity)getActivity()).sendSearchKey(searchKey);
            }

            /*if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                getActivity().startActivity(intent, bundle);
            } else*/

        }
    }
}

