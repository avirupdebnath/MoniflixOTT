package com.example.myottapp.UI;

import android.app.Activity;
import android.app.DirectAction;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myottapp.R;

public class SearchActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void sendSearchKey(String searchKey){
        TextView searchText=(TextView)findViewById(R.id.search_key);
        if(searchKey.equals("")) searchText.setText(" "+"Search For Movies, Series...");
        else searchText.setText(" "+searchKey);
        SearchFragment searchfragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
        searchfragment.sendSearchRequest(searchKey);
    }

}
