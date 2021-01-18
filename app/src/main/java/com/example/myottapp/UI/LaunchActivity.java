package com.example.myottapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myottapp.R;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.SessionManager;
import com.example.myottapp.models.UserDetails;

public class LaunchActivity extends Activity {
    ProgressBar progressBar;
    SessionManager sessionManager;
    UserDetails userDetails;
    String email, password;

    private static int SPLASH_TIME_OUT = 1000;

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
                if (sessionManager.isLoggin()) {
                    DataModel.accessToken=sessionManager.getAccessToken();
                    Intent i = new Intent(LaunchActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}
    /*
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
            Intent i = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(i);
            finish();
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
            Intent i = new Intent(LaunchActivity.this, LoginActivity.class);
            startActivity(i);
            Toast.makeText(LaunchActivity.this,"Please login again!",Toast.LENGTH_LONG);
            finish();
        }
    };

    private void login(String email,String password) {
        CognitoSettings cognitoSettings = new CognitoSettings(LaunchActivity.this);
        cognitoSettings.getUserPool().getCurrentUser().signOut();
        CognitoUser thisUser = cognitoSettings.getUserPool()
                .getUser(String.valueOf(email));
        thisUser.getSessionInBackground(authenticationHandler);
    }

}*/