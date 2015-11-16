package com.codepath.apps.fancytweets.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.fancytweets.EndlessRecyclerOnScrollListener;
import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.adapters.TweetsAdapter;
import com.codepath.apps.fancytweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetListFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private TweetsAdapter aTweets;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Tweet> tweets;

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
                //getMoreTweets(getLastTweetId());
                //return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        return v;
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
