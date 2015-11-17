package com.codepath.apps.fancytweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.TwitterApplication;
import com.codepath.apps.fancytweets.TwitterClient;
import com.codepath.apps.fancytweets.fragments.UserTimelineFragment;
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
        setContentView(R.layout.activity_user_profile);
        client = TwitterApplication.getRestClient(); // singleton client

        // grab this from the activity that launches this
        String screenname = getIntent().getStringExtra("screenname");
        //Long uid = getIntent().getLongExtra("uid", 1); // second param is a default, in this case me
        Long uid = getIntent().getLongExtra("uid", User.getCurrentUser().getUid()); // second param is a default, in this case me
        mUser = getUser(uid, screenname);


        if(savedInstanceState == null) {
            //create the user timeline fragment
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(uid);
            // display user fragment dynamically
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUserProfileContainer, userTimelineFragment);
            ft.commit(); // changes the fragments
        }
    }

    private User getUser(Long uid, String screenname){
        client.getUserProfileInfo(uid, screenname, new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mUser = User.fromJSON(response);
                // getSupportActionBar().setTitle(user.getScreenname());
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE DEBUG", errorResponse.toString());
                mUser = null;
            }
        });

        return mUser;
    }
}
