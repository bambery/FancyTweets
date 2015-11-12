package com.codepath.apps.fancytweets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.codepath.apps.fancytweets.R;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    private ArrayList<Tweet> aTweets;

    //public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvTimeSince;
        public ViewHolder(LinearLayout v){
            super(v);
            ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) v.findViewById(R.id.tvComposeUsername);
            tvBody = (TextView) v.findViewById(R.id.tvBody);
            tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
            tvTimeSince = (TextView) v.findViewById(R.id.tvTimeSince);
        }

        // Create new views (invoked by the layout manager)
        @Override
        public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                             .inflate(R.layout.item_tweet_card, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder((LinearLayout)v);
            return vh;
        }
        @Override
        public void onClick(View v) {
            // eventually handle clicks on list here
        }
    }
}