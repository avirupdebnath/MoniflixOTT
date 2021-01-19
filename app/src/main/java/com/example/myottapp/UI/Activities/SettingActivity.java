package com.example.myottapp.UI.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myottapp.R;
import com.example.myottapp.models.CognitoSettings;
import com.example.myottapp.models.SessionManager;

public class SettingActivity extends Activity {

    Button membership, control, contact, notices,logout;
    TextView header, description;
    SessionManager sessionManager;

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sessionManager = new SessionManager(this);
        membership = findViewById(R.id.membership_button);
        control = findViewById(R.id.parental_control_buttons);
        contact = findViewById(R.id.contact_us_button);
        notices = findViewById(R.id.legal_notices_button);
        header = findViewById(R.id.setting_header_text);
        description = findViewById(R.id.settings_description_text);
        logout=findViewById(R.id.logout_button);

        membership.requestFocus();
        header.setText("Membership");
        description.setText(R.string.membershipDetails);


        membership.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    header.setText("Membership");
                    description.setText(R.string.membershipDetails);
                }
            }
        });

        control.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    header.setText("Parental control");
                    description.setText(R.string.parentalControlDetails);
                }
            }
        });

        contact.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    header.setText("Contact us");
                    description.setText(R.string.contactUsDetails);
                }
            }
        });

        notices.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    header.setText("Legal notices");
                    description.setText(R.string.legalNoticeDetail);
                }
            }
        });
        logout.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    header.setText("Logout");
                    description.setText("Are you sure want to logout?");
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CognitoSettings cognitoSettings = new CognitoSettings(SettingActivity.this);
                cognitoSettings.getUserPool().getCurrentUser().signOut();
                sessionManager.logout();
                finishAffinity();
            }
        });

    }
}