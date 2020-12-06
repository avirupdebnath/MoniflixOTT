package com.example.myottapp.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myottapp.R;

public class SettingActivity extends Activity {

    Button membership, control, contact, notices;
    TextView header, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        membership = findViewById(R.id.membership_button);
        control = findViewById(R.id.parental_control_buttons);
        contact = findViewById(R.id.contact_us_button);
        notices = findViewById(R.id.legal_notices_button);
        header = findViewById(R.id.setting_header_text);
        description = findViewById(R.id.settings_description_text);

        membership.performClick();

        header.setText("Membership");
        description.setText(R.string.membershipDetails);

        membership.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                header.setText("Membership");
                description.setText(R.string.membershipDetails);
            }
        });

        control.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                header.setText("Parental control");
                description.setText(R.string.parentalControlDetails);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                header.setText("Contact us");
                description.setText(R.string.contactUsDetails);
            }
        });
        notices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                header.setText("Legal notices");
                description.setText(R.string.legalNoticeDetail);
            }
        });
    }
}