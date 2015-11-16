package com.codepath.apps.fancytweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.TwitterApplication;
import com.codepath.apps.fancytweets.TwitterClient;
import com.codepath.apps.fancytweets.adapters.TweetsPagerAdapter;
import com.codepath.apps.fancytweets.fragments.ComposeTweetDialog;
import com.codepath.apps.fancytweets.fragments.HomeTimelineFragment;
import com.codepath.apps.fancytweets.fragments.TweetListFragment;
import com.codepath.apps.fancytweets.models.Tweet;
import com.codepath.apps.fancytweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.OnSubmitNewTweetListener  {

    // sliding tab layout guide: https://guides.codepath.com/android/Google-Play-Style-Tabs-using-TabLayout


    private Toolbar myToolbar;
    private TwitterClient client;
    private TweetListFragment tweetListFragment;
    private User currentUser;
    private HomeTimelineFragment homeTimelineFragment;
    TabLayout tabLayout;
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient(); // singleton client
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(),
                                                           TimelineActivity.this));
        //if (savedInstanceState == null) {
        //    tweetListFragment = (TweetListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        //
        //}
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupMyToolbar();
        setCurrentUser();
    }

    // need to have a reference to "me", the person using the app
    // TODO: refactor to save current user in the activity? Or somewhere global
    public void setCurrentUser(){
        if (currentUser == null) {
            client.getCurrentUser(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User.setCurrentUser(User.fromJSON(response));
                }

                //failure
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("ERROR", errorResponse.toString());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_timeline_activity, menu);
        return true;
    }

    public void setupMyToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.title_activity_timeline);
        myToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        myToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.twitter_light_blue));
        myToolbar.inflateMenu(R.menu.toolbar_timeline_activity);
        // launch compose tweet activity if button is clicked
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_new_tweet) {
                    showNewTweetDialog();
                    return true;
                } else if (id == R.id.action_my_profile) {
                    launchMyProfile(User.getCurrentUser());
                    return true;
                }
                return false;
            }
        });
    }

    private void showNewTweetDialog(){
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetDialog composeTweetDialog = ComposeTweetDialog.newInstance(User.getCurrentUser());
        composeTweetDialog.show(fm, "fragment_compose_tweet");
    }

    private void launchMyProfile(User user){
        Intent i = new Intent(TimelineActivity.this, UserProfileActivity.class);
        i.putExtra("uid", user.getUid());
        i.putExtra("screenname", user.getScreenName());
        startActivity(i);
    }

    @Override
    public void onNewTweetSubmitted(String tweetBody) {
        client.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Long newTweetId = Tweet.getPostedTweetId(response);
                // TODO fix this
                /// /HomeTimelineFragment.refreshAfterNewTweet(newTweetId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(TimelineActivity.this, "Failed posting tweet", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getMoreTweets(long lastTweetId) {
        client.getTweetsAfterMyTweet(lastTweetId, new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //deserialize json
                //create models and add to adapter
                //load data model into listview
                //               ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                tweetListFragment.addAll(Tweet.fromJSONArray(response));
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE DEBUG", errorResponse.toString());
            }
        });
    }



    private long getLastTweetId(){
        //Log.d("DEBUG", "last tweet id " + tweets.get(tweets.size() - 1).getUid());
        //return (tweets.get(tweets.size() - 1).getUid());
        return 1;
    }
}
