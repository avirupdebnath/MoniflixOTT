package com.example.myottapp.models;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolId="ap-south-1_aAvBM2dQE";
    private String clientId="1q04nn4g83kqqgp5f93dh0217f";
    private String clientSecret="rbbkvac1nshrk02ndb0n21qd15tq2sblt1oubu7hk8jbgdflhu8";
    //    private String cognitoRegion="us-east-2";
    private final Regions cognitoRegion = Regions.AP_SOUTH_1;
    public Regions getCognitoRegion() {
        return cognitoRegion;
    }
    private Context context;
    public CognitoSettings(Context context){
        this.context = context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }



    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolId, clientId,clientSecret, cognitoRegion);
    }
}
