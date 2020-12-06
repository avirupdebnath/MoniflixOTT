package com.example.myottapp.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myottapp.R;

public class LoginActivity extends Activity {

    private EditText etEmail, etPass;
    private ProgressBar progressBar;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.emailLogin);
        etPass = findViewById(R.id.passwordLogin);
        progressBar = findViewById(R.id.progress_login);
        loginBtn = findViewById(R.id.validate_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginBtn.setVisibility(View.INVISIBLE);
                if (!isValidEmailAddress(etEmail.getText().toString())) {
                    loginBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this,"Please enter valid email", Toast.LENGTH_LONG).show();
                } else if (etPass.getText().toString().equals("")) {
                    loginBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this,"Please enter password", Toast.LENGTH_LONG).show();
                } else {
                    String email = etEmail.getText().toString();
                    String pass = etPass.getText().toString();
                    login(email, pass);
                }
            }
        });

    }

    private void login(String email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        progressBar.setVisibility(View.GONE);
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}