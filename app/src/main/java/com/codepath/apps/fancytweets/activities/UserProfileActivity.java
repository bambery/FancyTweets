package com.codepath.apps.fancytweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.apps.fancytweets.TwitterApplication;
import com.codepath.apps.fancytweets.TwitterClient;
import com.codepath.apps.fancytweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity{
    private User mUser;
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String screenname = getIntent().getStringExtra("screenname");
        client = TwitterApplication.getRestClient(); // singleton client
        Long uid = getIntent().getLongExtra("uid", User.getCurrentUser().getUid()); // second param is a default, in this case me
        client.getUserProfile(uid, screenname, new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mUser = User.fromJSON(response);
                // you are here
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE DEBUG", errorResponse.toString());
            }
        });
    }
}
