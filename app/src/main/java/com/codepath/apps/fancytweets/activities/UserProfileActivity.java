package com.codepath.apps.fancytweets.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.TwitterApplication;
import com.codepath.apps.fancytweets.TwitterClient;
import com.codepath.apps.fancytweets.fragments.UserTimelineFragment;
import com.codepath.apps.fancytweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.Header;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity{
    User mUser;
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
        client.getUserProfileInfo(uid, new JsonHttpResponseHandler() {
                    // success
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        mUser = User.fromJSON(response);
                        populateProfileHeader();
                        // getSupportActionBar().setTitle(user.getScreenname());
                    }

                    //failure
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {
                        // Handle the failure and alert the user to retry
                        Log.e("ERROR", e.toString());
                    }
                });

        if(savedInstanceState == null) {
            //create the user timeline fragment
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(uid);
            // display user fragment dynamically
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUserProfileContainer, userTimelineFragment);
            ft.commit(); // changes the fragments
        }
    }

    private void populateProfileHeader(){
        final RelativeLayout rlUserProfileHeader = (RelativeLayout) findViewById(R.id.rlUserProfileHeader);
        // maybe if you used Twitter more, Lauren, this wouldn't be broken.
        if (mUser.getProfileBannerUrl() != null) {
            Picasso.with(this).load(mUser.getProfileBannerUrl()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    rlUserProfileHeader.setBackground(new BitmapDrawable(getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(final Drawable errorDrawable) {
                    Log.d("TAG", "FAILED");
                }

                @Override
                public void onPrepareLoad(final Drawable placeHolderDrawable) {
                    Log.d("TAG", "Prepare Load");
                }
            });
        } else {
            rlUserProfileHeader.setBackgroundColor(ContextCompat.getColor(this, R.color.twitter_mid_grey));
        }

        ImageView ivUserProfilePhoto = (ImageView) findViewById(R.id.ivUserProfilePhoto);
        Picasso.with(this)
                .load(mUser.getProfileImageUrl())
         //       .placeholder(R.drawable.placeholder)
                .into(ivUserProfilePhoto);

        TextView tvUserProfileName = (TextView) findViewById(R.id.tvUserProfileName);
        tvUserProfileName.setText(mUser.getName());

        TextView tvUserProfileScreenname = (TextView) findViewById(R.id.tvUserProfileScreenname);
        tvUserProfileScreenname.setText("@" + mUser.getScreenName());

        TextView tvUserTweetCount = (TextView) findViewById(R.id.tvUserTweetCount);
        tvUserTweetCount.setText(mUser.getStatusesCount().toString());

        TextView tvUserFollowingCount = (TextView) findViewById(R.id.tvUserFollowingCount);
        tvUserFollowingCount.setText(mUser.getFriendsCount().toString());

        TextView tvUserFollowersCount = (TextView) findViewById(R.id.tvUserFollowersCount);
        tvUserFollowersCount.setText(mUser.getFollowersCount().toString());
    }
}
