package com.codepath.apps.fancytweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

// 1) parse json + store data
// 2) encapsulate state or display logic

public class Tweet {
    private String body;
    private long uid; // unique db id for the tweet
    private User user;
    private String createdAt;

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return getRelativeTimeAgo(createdAt);
    }

    // Deserialize the JSON
    // Tweet.fromJson
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return tweet object
        return tweet;
    }

    public static Long getPostedTweetId(JSONObject jsonObject){
        Long postedTweetId = null;
        try {
            postedTweetId = jsonObject.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postedTweetId;
    }
    //input jsonarray of tweet items, output a list of tweets
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    // returns 12m if posted 12 minutes ago
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        TimeFormatter tf = new TimeFormatter();
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            long timeNow = System.currentTimeMillis();
            long timeElapsed = timeNow - dateMillis;
            relativeDate = tf.format(timeElapsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}

