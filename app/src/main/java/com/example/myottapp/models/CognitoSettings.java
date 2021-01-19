package com.example.myottapp.models;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolId="us-east-2_eSMJ0pCjq";
    private String clientId="20prdt71a6if8mcn8b5jbn1ao";
    private String clientSecret="1p9gp11j7ko99kutmjv6km3hjpkivr85adrnlorkcqoprqmmpl51";
    //    private String cognitoRegion="us-east-2";
    private final Regions cognitoRegion = Regions.US_EAST_2;
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
