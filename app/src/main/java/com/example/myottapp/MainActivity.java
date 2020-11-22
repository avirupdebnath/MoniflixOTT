package com.example.myottapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myottapp.models.AllCategoriesList;
import com.example.myottapp.models.AllLanguagesList;
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
public class MainActivity extends Activity {
    public static  RequestQueue requestQueue;
    public static AllCategoriesList allCategoriesList;
    public static AllLanguagesList allLanguagesList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        String urlCategories="https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllCategories";
        String urlLanguages= "https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllLanguages";

        requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlCategories,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        allCategoriesList=AllCategoriesList.parseJSON("{categories:"+String.valueOf(response)+"}");
                        Log.e("Rest Response",response.toString());
                        System.out.println(allCategoriesList);
                        MovieList.MOVIE_CATEGORY=allCategoriesList.getCategoryNameArray();
                        System.out.println("Updated Category Name: "+MovieList.MOVIE_CATEGORY[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        JsonArrayRequest jsonArrayRequest2=new JsonArrayRequest(
                Request.Method.GET,
                urlLanguages,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        allLanguagesList=AllLanguagesList.parseJSON("{languages:"+String.valueOf(response)+"}");
                        Log.e("Rest Response",response.toString());
                        System.out.println(allLanguagesList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        requestQueue.add(jsonArrayRequest2);
*/
        //if(allCategoriesList!=null){





        //initializeModels();
        //AllCategoriesList model = new ViewModelProvider(this).get(AllCategoriesList.class);
        //System.out.println(model.categories);
    }
    public void initializeModels(){
        //String apiKey = "YOUR-API-KEY-HERE";

        AsyncHttpClient client = new AsyncHttpClient();
        /*client.get(
                "https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllLanguages", //+ apiKey,
                new TextHttpResponseHandler(){

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        //super.onSuccess(statusCode, headers, response);
                        Gson gson = new GsonBuilder().create();
                        response = "{languages:"+response+"}";
                        System.out.println(response);
                        AllLanguagesList languageList = AllLanguagesList.parseJSON(response);
                        System.out.println(languageList);
                    }
                });*/
        client.get(
                "https://5fglc3ehn2.execute-api.ap-south-1.amazonaws.com/api/Master/GetAllCategories", //+ apiKey,
                new TextHttpResponseHandler(){

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        //super.onSuccess(statusCode, headers, response);
                        Gson gson = new GsonBuilder().create();
                        response = "{categories:"+response+"}";
                        System.out.println(response);
                        AllCategoriesList allCategoriesList=AllCategoriesList.parseJSON(response);
                        System.out.println(allCategoriesList);
                        //MovieList.MOVIE_CATEGORY=allCategoriesList.getCategoryNameArray();
                        //MovieList.MOVIE_CATEGORY= AllCategoriesList.getCategoryNameArray();
                    }
                });
    }



}