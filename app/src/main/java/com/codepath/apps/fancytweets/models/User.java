package com.codepath.apps.fancytweets.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private static User currentUser;
    private int followersCount; // # following user
    private int friendsCount; // # of people user is following
    private int statusesCount; // # of tweets posted
    private String profileBannerUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public static User fromJSON(JSONObject jsonObject){
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.followersCount = jsonObject.getInt("followers_count");
            u.friendsCount = jsonObject.getInt("friends_count");
            if ((jsonObject.has("profile_banner_url"))){
                u.profileBannerUrl = jsonObject.getString("profile_banner_url");
            } else {
                u.profileBannerUrl = null;
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return u;

    }

    public static User getCurrentUser(){
        //TODO put this in the client
        return User.currentUser;
    }

    public static void setCurrentUser(User current){
        User.currentUser = current;
    }
}

