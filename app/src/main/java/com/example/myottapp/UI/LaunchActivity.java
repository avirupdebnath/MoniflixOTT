package com.example.myottapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.example.myottapp.R;
import com.example.myottapp.models.SessionManager;

public class LaunchActivity extends Activity {
    ProgressBar progressBar;
    SessionManager sessionManager;
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
                sessionManager.checkLogin();
            }
        },SPLASH_TIME_OUT);
    }
}