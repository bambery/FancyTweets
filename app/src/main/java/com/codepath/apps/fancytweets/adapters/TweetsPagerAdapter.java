package com.codepath.apps.fancytweets.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.fancytweets.fragments.HomeTimelineFragment;
import com.codepath.apps.fancytweets.fragments.MentionsTimelineFragment;

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

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
