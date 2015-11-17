package com.codepath.apps.fancytweets.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.fancytweets.EndlessRecyclerOnScrollListener;
import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.TwitterApplication;
import com.codepath.apps.fancytweets.TwitterClient;
import com.codepath.apps.fancytweets.adapters.TweetsAdapter;
import com.codepath.apps.fancytweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TweetListFragment extends Fragment implements ComposeTweetDialog.OnSubmitNewTweetListener {
    private RecyclerView mRecyclerView;
    private TweetsAdapter aTweets;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Tweet> tweets;
    private TwitterClient client;

    //public static final String ARG_PAGE = "ARG_PAGE";
    //private int mPage;

    public static TweetListFragment newInstance(int page) {
        Bundle args = new Bundle();
        //args.putInt(ARG_PAGE, page);
        TweetListFragment fragment = new TweetListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPage = getArguments().getInt(ARG_PAGE);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //create the arraylist from data source
        tweets = new ArrayList<>();
        // specify an adapter
        aTweets = new com.codepath.apps.fancytweets.adapters.TweetsAdapter(getActivity(), tweets);
        client = TwitterApplication.getRestClient(); // singleton client
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_tweet_timeline);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(aTweets);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int page) {
                getMoreTweets();
                //return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        return v;
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
                Log.d("ERROR", errorResponse.toString());
            }
        });
    }

    public void refreshAfterNewTweet(long myNewTweetId){
        clearTweets();
        client.getTweetsAfterMyTweet(myNewTweetId, new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE DEBUG", errorResponse.toString());
            }
        });
    }

    public void getMoreTweets() {
        // I know Java has a way to enforce that child classes must implement this
        // all subclasses must implement this ... interface? Something.
        // TODO find how to enforce this ^^
    }

    public long getLastTweetId(){
        if (tweets.size() < 2){
            return 1;
        } else {
            return (tweets.get(tweets.size() - 1).getUid());
        }
    }

    public void addAll(List<Tweet> incomingTweets){
        tweets.addAll(incomingTweets);
        aTweets.notifyDataSetChanged();
    }

    public void clearTweets(){
        tweets.clear();
        aTweets.notifyDataSetChanged();
    }


}
