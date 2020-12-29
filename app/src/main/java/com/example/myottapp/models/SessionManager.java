package com.example.myottapp.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.myottapp.UI.CognitoSettings;
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
    public static final String WATCHLIST="WatchList";
    public static final String ACCESS_TOKEN="Access Token";
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
        DataModel.accessToken=accessToken;
        editor.putString(ACCESS_TOKEN,accessToken);
        System.out.println("Data Model Access Token"+DataModel.accessToken);
        editor.apply();

    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public static String getEMAIL() {
        return sharedPreferences.getString(EMAIL,null);
    }

    public static String getPASSWORD() {
        return sharedPreferences.getString(PASSWORD,null);
    }

    public static String getAccessToken(){ return sharedPreferences.getString(ACCESS_TOKEN,null);}

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
