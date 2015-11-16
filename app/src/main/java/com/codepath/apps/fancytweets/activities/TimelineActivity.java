package com.codepath.apps.fancytweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.fancytweets.EndlessRecyclerOnScrollListener;
import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.TwitterApplication;
import com.codepath.apps.fancytweets.TwitterClient;
import com.codepath.apps.fancytweets.adapters.TweetsAdapter;
import com.codepath.apps.fancytweets.fragments.ComposeTweetDialog;
import com.codepath.apps.fancytweets.models.Tweet;
import com.codepath.apps.fancytweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.OnSubmitNewTweetListener  {
    private RecyclerView mRecyclerView;
    private TweetsAdapter aTweets;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar myToolbar;

    private TwitterClient client;
    private ArrayList<Tweet> tweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupMyToolbar();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_tweet_timeline);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int page) {
                getMoreTweets(getLastTweetId());
                //eturn true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        client = TwitterApplication.getRestClient(); // singleton client
        // set custom toolbar


        //create the arraylist from data source
        tweets = new ArrayList<>();
        //construct the adapter from data source

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);



        // specify an adapter
        aTweets = new com.codepath.apps.fancytweets.adapters.TweetsAdapter(this, tweets);
        mRecyclerView.setAdapter(aTweets);

        setCurrentUser();
        populateTimeline();
    }

    // need to have a reference to "me", the person using the app
    public void setCurrentUser(){
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
                    Toast.makeText(getApplicationContext(), "you clicked it", Toast.LENGTH_SHORT).show();
                    //showNewTweetDialog();
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

    @Override
    public void onNewTweetSubmitted(String tweetBody){
        client.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Long newTweetId = Tweet.getPostedTweetId(response);
                refreshAfterNewTweet(newTweetId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(TimelineActivity.this, "Failed posting tweet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //send api request to get the timeline json
    // fill listview by creating the tweet objects from the json
    private void populateTimeline() {
        client.getInitialHomeTimeline(new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //deserialize json
                //create models and add to adapter
                //load data model into listview
                //               ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                tweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE DEBUG", errorResponse.toString());
            }
        });
    }

    public void getMoreTweets(long lastTweetId){
        client.getTweetsAfterMyTweet(lastTweetId, new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //deserialize json
                //create models and add to adapter
                //load data model into listview
                //               ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                tweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();;
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE DEBUG", errorResponse.toString());
            }
        });
    }

    private void refreshAfterNewTweet(long myNewTweetId){
        tweets.clear();
        aTweets.notifyDataSetChanged();
        client.getTweetsAfterMyTweet(myNewTweetId, new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //deserialize json
                //create models and add to adapter
                //load data model into listview
                //               ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                // should only be one tweet here
                tweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE DEBUG", errorResponse.toString());
            }
        });
        aTweets.notifyDataSetChanged();
    }

    private long getLastTweetId(){
        Log.d("DEBUG", "last tweet id " + tweets.get(tweets.size() - 1).getUid());
        return (tweets.get(tweets.size() - 1).getUid());
    }
}
