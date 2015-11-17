package com.codepath.apps.fancytweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.activities.UserProfileActivity;
import com.codepath.apps.fancytweets.models.Tweet;
import com.codepath.apps.fancytweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    private ArrayList<Tweet> mTweets;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvTimeSince;

        public ViewHolder(View v) {
            super(v);
            ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) v.findViewById(R.id.tvComposeUsername);
            tvBody = (TextView) v.findViewById(R.id.tvBody);
            tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
            tvTimeSince = (TextView) v.findViewById(R.id.tvTimeSince);
        }

        public void bindTweet(Tweet tweet) {

            tvUsername.setText(tweet.getUser().getName());
            tvBody.setText(tweet.getBody());
            // I feel like the User class should handle its display logic but I don't know how to access
            // strings.xml without passing in a context, which seems hacky.
            // TODO revisit this
            tvScreenName.setText(mContext.getString(R.string.at_sign) + tweet.getUser().getScreenName());
            tvTimeSince.setText(tweet.getCreatedAt());
            ivProfileImage.setImageResource(android.R.color.transparent); // good place to set a placeholder while clearing out the old content
            Picasso.with(mContext)
                    .load(tweet.getUser().getProfileImageUrl())
                    .into(ivProfileImage);

            ivProfileImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO is it ok to launch activity from here?
            final Intent intent = new Intent(mContext, UserProfileActivity.class);
            int pos = getLayoutPosition();
            Tweet mTweet = mTweets.get(pos);
            User user = mTweet.getUser();
            intent.putExtra("uid", user.getUid());
            intent.putExtra("screenname", user.getScreenName());
            mContext.startActivity(intent);
        }
    }

    public TweetsAdapter(Context context, ArrayList<Tweet> tweets){
        this.mTweets = tweets;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                         .inflate(R.layout.item_tweet_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);
        viewHolder.bindTweet(tweet);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}