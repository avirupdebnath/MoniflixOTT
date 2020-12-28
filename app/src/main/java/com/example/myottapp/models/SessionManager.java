package com.example.myottapp.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.myottapp.UI.LaunchActivity;
import com.example.myottapp.UI.LoginActivity;
import com.example.myottapp.UI.MainActivity;
import com.example.myottapp.UI.SettingActivity;

import java.util.HashMap;

public class SessionManager {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
//    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String ACCESS_TOKEN = "JWT Token";
    public static final String WATCHLIST="WatchList";
//    public static final String ID = "ID";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String email, String password, String accessToken){

        editor.putBoolean(LOGIN, true);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD,password);
        editor.putString(ACCESS_TOKEN,accessToken);
        DataModel.accessToken=accessToken;
        editor.apply();

    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public boolean checkLogin(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((LaunchActivity) context).finish();
            return false;
        }else
        {
            DataModel.accessToken=sharedPreferences.getString(ACCESS_TOKEN,null);
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((LaunchActivity) context).finish();
            return true;
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
//        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
//        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        //((SettingActivity) context).finish();
    }
}
