package com.example.myottapp.UI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myottapp.R;
import com.example.myottapp.models.DataModel;

public class SearchActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        DataModel.refreshTokenCount=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void sendSearchKey(String searchKey){
        TextView searchText=(TextView)findViewById(R.id.search_key);
        TextView searchResultFor=(TextView)findViewById(R.id.SearchResultsFor);
        if(searchKey.equals("")){
            searchText.setText(" "+"Search For Movies, Series...");
            searchResultFor.setText("");
        }
        else {
            searchText.setText(" "+searchKey);
            searchResultFor.setText("Showing Results For \""+searchKey+"\"");
        }
        SearchFragment searchfragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
        searchfragment.sendSearchRequest(searchKey);
    }

}
