package com.example.myottapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.myottapp.R;
import com.example.myottapp.models.SessionManager;
import com.example.myottapp.models.UserDetails;

public class LaunchActivity extends Activity {
    ProgressBar progressBar;
    SessionManager sessionManager;
    UserDetails userDetails;
    String email,password;

    private  static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        progressBar = findViewById(R.id.launch_progress);

        sessionManager = new SessionManager(this);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.checkLogin()){
                    email=sessionManager.getEMAIL();
                    password = sessionManager.getPASSWORD();
                    login(email, password);
                }
            }
        },SPLASH_TIME_OUT);



    }
    final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            progressBar.setVisibility(View.GONE);
            System.out.println("Login successfull");
            System.out.println(userSession.getAccessToken().getExpiration());
            CognitoSettings cognitoSettings = new CognitoSettings(LaunchActivity.this);
            System.out.println("JWT Access Token: "+String.valueOf(userSession.getAccessToken().getJWTToken()));
            userDetails = new UserDetails(userSession.getUsername(),userSession.getAccessToken().toString(),userSession.getRefreshToken().toString());
            sessionManager.createSession(email, password,userSession.getAccessToken().getJWTToken());
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            System.out.println("in getAuthentication");

            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, password,null);

            authenticationContinuation.setAuthenticationDetails(authenticationDetails);

            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
            System.out.println("in getMFACode");
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            System.out.println("in authenticationChallenge");
        }

        @Override
        public void onFailure(Exception exception) {
            System.out.println("Error : "+exception.getLocalizedMessage());
        }
    };

    private void login(String email,String password) {
        CognitoSettings cognitoSettings = new CognitoSettings(LaunchActivity.this);
        CognitoUser thisUser = cognitoSettings.getUserPool()
                .getUser(String.valueOf(email));
        thisUser.getSessionInBackground(authenticationHandler);
    }
}