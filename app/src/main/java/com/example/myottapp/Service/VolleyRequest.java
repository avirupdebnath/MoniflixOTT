package com.example.myottapp.Service;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myottapp.VolleyCallback;
import com.example.myottapp.models.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {

    private String responseString="";
    private static final int INITIAL_TIMEOUT=5*1000;
    private static final int MAX_RETRIES=2;
    private static final float BACKOFF_MULTIPLIER=1.0f;

    public  String getResponseString() {
        return responseString;
    }

    public void sendJSONObjGetRequest(final VolleyCallback callback,String url,String tag){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseString = response.toString();
                        System.out.println(responseString);
                        callback.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: "+ error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Bearer " + DataModel.accessToken);
                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT,MAX_RETRIES,BACKOFF_MULTIPLIER));
        ApplicationController.getInstance().addToRequestQueue(req,tag);
    }

    public  void sendGetRequest(final VolleyCallback callback,String url, String tag){
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                responseString = response.toString();
                System.out.println(responseString);
                callback.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: "+ error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Bearer " + DataModel.accessToken);
                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT,MAX_RETRIES,BACKOFF_MULTIPLIER));
        ApplicationController.getInstance().addToRequestQueue(req,tag);
    }

    public  void sendPostRequest(final VolleyCallback callback,String url, JSONObject params, String tag){
        CustomRequest req = new CustomRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseString=response.toString();
                        System.out.println(responseString);
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error: "+ error.getMessage());
                    }
                });
        req.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT,MAX_RETRIES,BACKOFF_MULTIPLIER));
        ApplicationController.getInstance().addToRequestQueue(req,tag);
    }

    public JSONObject paramsObjectBuilder(int contentTypeId, int filterKey,int filterValue, int pageNo, int pageSize){
        JSONObject params=new JSONObject();
        try {
            params.put("ContentTypeId",contentTypeId);
            params.put("FilterKey", filterKey);
            params.put("FilterValue", filterValue);
            params.put("PageNo", pageNo);
            params.put("PageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }
}