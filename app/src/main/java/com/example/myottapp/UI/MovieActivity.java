package com.example.myottapp.UI;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myottapp.R;
import com.example.myottapp.models.AllCategoriesList;
import com.example.myottapp.models.AllLanguagesList;
import com.example.myottapp.models.DataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MovieActivity extends Activity {
    public Context mContext=this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        CarousalFragment carousalFragment=new CarousalFragment();
        carousalFragment.setConfigId(3);

        showCarousal();
        hideMovieDetails();
        //collapseLanguageRow();
    }

    public void hideCarousal(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.carousal_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }
    public void collapseLanguageRow(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.browse_frame);
        FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height=400;
        layoutParams.width= FrameLayout.LayoutParams.MATCH_PARENT;
        layoutParams.gravity=Gravity.BOTTOM;
    }
    public void expandLanguageRow(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.browse_frame);
        FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height=550;
        layoutParams.width= FrameLayout.LayoutParams.MATCH_PARENT;
        layoutParams.gravity=Gravity.BOTTOM;
    }
    public void showCarousal(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.carousal_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }
    public void hideMovieDetails(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.details_frame);
        frameLayout.setVisibility(View.INVISIBLE);
    }
    public void showMovieDetails(){
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.details_frame);
        frameLayout.setVisibility(View.VISIBLE);
    }

    void setMovieName(String s){
        TextView movieName=(TextView) findViewById(R.id.movie_name);
        movieName.setText(s);
    }
    void setMovieLanguage(String s){
        TextView movieLanguage=(TextView) findViewById(R.id.movie_language);
        movieLanguage.setText(s);
    }
    void setMovieAgeRestriction(String s){
        TextView movieAge=(TextView) findViewById(R.id.movie_age);
        movieAge.setText(s);
    }
    void setMovieDescription(String s){
        TextView movieDescription=(TextView) findViewById(R.id.movie_desrciption);
        movieDescription.setText(s);
    }
    void setMovieRuntime(int n){
        TextView movieTime=(TextView) findViewById(R.id.movie_time);
        int hr=n/60;
        int min=n%60;
        String runtime= hr+" hr "+min+" min";
        movieTime.setText(runtime);
    }
    void setMoviePoster(String url){
        ImageView moviePoster=(ImageView) findViewById(R.id.movie_poster);
        Glide.with(mContext)
                .load(Uri.parse(url))
                .centerCrop()
                .into(moviePoster);
    }


}