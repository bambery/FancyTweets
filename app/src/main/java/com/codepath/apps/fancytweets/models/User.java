package com.codepath.apps.fancytweets.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private static User currentUser;
    private Long followersCount; // # following user
    private Long friendsCount; // # of people user is following
    private Long statusesCount; // # of tweets posted
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

    public Long getFollowersCount() {
        return followersCount;
    }

    public Long getFriendsCount() {
        return friendsCount;
    }

    public Long getStatusesCount() {
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
            u.followersCount = jsonObject.getLong("followers_count");
            u.friendsCount = jsonObject.getLong("friends_count");
            if ((jsonObject.has("profile_banner_url"))){
                u.profileBannerUrl = jsonObject.getString("profile_banner_url");
            } else {
                u.profileBannerUrl = null;
            }
            u.statusesCount = jsonObject.getLong("statuses_count");
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

