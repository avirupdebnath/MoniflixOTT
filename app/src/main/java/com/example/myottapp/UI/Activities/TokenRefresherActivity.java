
//----------------------------------------------------
// Temporary Fix to Solve repeated Login Issues.
//----------------------------------------------------

package com.example.myottapp.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.myottapp.R;
import com.example.myottapp.models.CognitoSettings;
import com.example.myottapp.models.DataModel;
import com.example.myottapp.models.MovieBasicInfo;
import com.example.myottapp.models.SessionManager;

public class TokenRefresherActivity extends Activity {
    SessionManager sessionManager;
    public static String email="";
    public static String password= "";
    public static final String MOVIE = "Movie";
    public static MovieBasicInfo movieBasicInfo;
    public static int relatedContent;
    public static String fromPage="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_refresher);
        sessionManager=new SessionManager(this);
        fromPage=this.getIntent().getStringExtra("fromPage");
        movieBasicInfo = (MovieBasicInfo) this.getIntent().getSerializableExtra(TokenRefresherActivity.MOVIE);
        if(fromPage.equals("Main")){
            relatedContent=this.getIntent().getIntExtra("relatedContent",0);
        }
        email= DataModel.getEmail();
        password= DataModel.getPassword();
        login(email);
    }

    final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            System.out.println("Login successfull");
            sessionManager.createSession(email, password,userSession.getAccessToken().getJWTToken(),false);

            Intent intent;
            if(fromPage.equals("Series")){
                intent = new Intent(TokenRefresherActivity.this, DetailsActivitySeries.class);
                intent.putExtra(DetailsActivitySeries.SERIES, movieBasicInfo);

            }else {
                intent = new Intent(TokenRefresherActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movieBasicInfo);
                if (fromPage.equals("Main")) {
                    intent.putExtra("relatedContent", relatedContent);
                }
                intent.putExtra("fromPage", fromPage);
            }
            TokenRefresherActivity.this.startActivity(intent);
            TokenRefresherActivity.this.finish();
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

    private void login(String email) {
        CognitoSettings cognitoSettings = new CognitoSettings(TokenRefresherActivity.this);
        cognitoSettings.getUserPool().getCurrentUser().signOut();
        CognitoUser thisUser = cognitoSettings.getUserPool()
                .getUser(String.valueOf(email));
        thisUser.getSessionInBackground(authenticationHandler);
    }

}
