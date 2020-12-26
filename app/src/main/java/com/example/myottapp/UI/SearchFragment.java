package com.example.myottapp.UI;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.VerticalGridFragment;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;

import com.example.myottapp.R;
import com.example.myottapp.Service.VolleyRequest;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.Movie;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.MovieBasicInfoList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SearchFragment extends VerticalGridFragment {
    private static int position = 1;
    private static final String TAG = "BannerFragment";
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter rowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(3);
        setGridPresenter(gridPresenter);
        //CustomListRowPresenter listRowPresenter = new CustomListRowPresenter(FocusHighlight.ZOOM_FACTOR_SMALL, position);
        //listRowPresenter.setShadowEnabled(false);
        //rowsAdapter = new ArrayObjectAdapter(listRowPresenter);
       // loadBanner();
        setupEventListeners();

    }
    public void sendSearchRequest(String key){
        String tag="";
        if(key.length()==0)createRow(getEmptyList());
        else {
            key = key.replace(" ", "%20");
            VolleyRequest volleyRequest = new VolleyRequest();
            volleyRequest.sendJSONObjGetRequest(new VolleyCallback() {
                @Override
                public void onSuccess() {
                    MovieBasicInfoList movieBasicInfoList = MovieBasicInfoList.parseJSON("{movieBasicInfos" + volleyRequest.getResponseString().substring(11));
                    List<MovieBasicInfo> movieBasicInfos = movieBasicInfoList.getMovieBasicInfos();
                    createRow(movieBasicInfos);
                }
            }, DataModel.searchURL + key, tag);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //workaroundFocus();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("Search Results");
        }

    public void createRow(List<MovieBasicInfo> list){
        SearchCardPresenter cardPresenter=new SearchCardPresenter(250,300,true);
        ArrayObjectAdapter categoryRowAdapter=new ArrayObjectAdapter(cardPresenter);
        if(list.size()!=0) {
            categoryRowAdapter.addAll(0,list);
            //for (int i = 0; i < list.size(); i++)
              //  categoryRowAdapter.add(list.get(i));
            setAdapter(categoryRowAdapter);
        }
        else{
            categoryRowAdapter.clear();
            setAdapter(categoryRowAdapter);
        }
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new SearchFragment.ItemViewClickedListener());
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
                getActivity().startActivity(intent);
            }
        }
    }

        public List<MovieBasicInfo> getEmptyList() {
            return new List<MovieBasicInfo>() {
                @Override
                public int size() { return 0; }

                @Override
                public boolean isEmpty() { return false; }

                @Override
                public boolean contains(@Nullable Object o) {
                    return false;
                }

                @NonNull
                @Override
                public Iterator<MovieBasicInfo> iterator() {
                    return null;
                }

                @NonNull
                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @NonNull
                @Override
                public <T> T[] toArray(@NonNull T[] ts) {
                    return null;
                }

                @Override
                public boolean add(MovieBasicInfo movieBasicInfo) {
                    return false;
                }

                @Override
                public boolean remove(@Nullable Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(@NonNull Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean addAll(@NonNull Collection<? extends MovieBasicInfo> collection) {
                    return false;
                }

                @Override
                public boolean addAll(int i, @NonNull Collection<? extends MovieBasicInfo> collection) {
                    return false;
                }

                @Override
                public boolean removeAll(@NonNull Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean retainAll(@NonNull Collection<?> collection) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public MovieBasicInfo get(int i) {
                    return null;
                }

                @Override
                public MovieBasicInfo set(int i, MovieBasicInfo movieBasicInfo) {
                    return null;
                }

                @Override
                public void add(int i, MovieBasicInfo movieBasicInfo) {

                }

                @Override
                public MovieBasicInfo remove(int i) {
                    return null;
                }

                @Override
                public int indexOf(@Nullable Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(@Nullable Object o) {
                    return 0;
                }

                @NonNull
                @Override
                public ListIterator<MovieBasicInfo> listIterator() {
                    return null;
                }

                @NonNull
                @Override
                public ListIterator<MovieBasicInfo> listIterator(int i) {
                    return null;
                }

                @NonNull
                @Override
                public List<MovieBasicInfo> subList(int i, int i1) {
                    return null;
                }
            };
        }
}