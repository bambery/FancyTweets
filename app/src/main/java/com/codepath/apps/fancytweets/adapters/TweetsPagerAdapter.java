package com.codepath.apps.fancytweets.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.fancytweets.fragments.HomeTimelineFragment;
import com.codepath.apps.fancytweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.fancytweets.fragments.TweetListFragment;

public class TweetsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private HomeTimelineFragment homeTimelineFragment;
    private MentionsTimelineFragment mentionsTimelineFragment;

    // TODO get this into strings.xml
    private String tabTitles[] = new String[] { "Home", "Mentions" };
    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0 ) {
            if (homeTimelineFragment == null) {
                homeTimelineFragment = new HomeTimelineFragment();
            }
            return homeTimelineFragment;
        } else if (position == 1) {
            if (mentionsTimelineFragment == null) {
                mentionsTimelineFragment = new MentionsTimelineFragment();
            }
            return mentionsTimelineFragment;
        } else {
            return null;
        }
    }

    public void updateAfterPost(long myNewTweetId){
        TweetListFragment frag = (TweetListFragment) getItem(0);
        frag.refreshAfterNewTweet(myNewTweetId);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    /*
        @Override
    public void onNewTweetSubmitted(String tweetBody) {
        client.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // regardless of activity, switch to the home timeline view to see new tweet after posting
                Long newTweetId = Tweet.getPostedTweetId(response);
                // TODO is there a way to do this in the fragment itself? I was under the impression
                // we were to attempt to avoid putting references to the fragments in here
                tweetListFragment = (TweetListFragment) tweetsPagerAdapter.getItem(0);
                tweetListFragment.refreshAfterNewTweet(newTweetId);
                viewPager.setCurrentItem(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR", errorResponse.toString());
            }
        });
    }
     */
}
