package com.codepath.apps.fancytweets;

import android.content.Context;

import com.codepath.apps.fancytweets.models.Constants;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1"; // base API URL
	public static final String REST_CONSUMER_KEY = Constants.REST_CONSUMER_KEY;
	public static final String REST_CONSUMER_SECRET = Constants.REST_CONSUMER_SECRET;
	public static final String REST_CALLBACK_URL = "x-oauthflow-twitter://cpfancytweets";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	private void getHomeTimeline(long lastTweetId, AsyncHttpResponseHandler handler ){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// specify params
		RequestParams params = new RequestParams();
		params.put("count", 25); // grab 25 tweets
		params.put("since_id", lastTweetId); // oldest id of tweet to grab
		//execute request
		getClient().get(apiUrl, params, handler);
	}

	public void getInitialHomeTimeline(AsyncHttpResponseHandler handler){
		getHomeTimeline(1, handler);
	}
/*
	public void getLatestTweetsSince( long lastTweetId, AsyncHttpResponseHandler handler){
		getHomeTimeline(lastTweetId, handler);
	}
*/
	public void getTweetsAfterMyTweet(long myTweetId, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// specify params
		RequestParams params = new RequestParams();
		params.put("count", 25); // grab 25 tweets
		params.put("max_id", myTweetId); // oldest id of tweet to grab
		//execute request
		getClient().get(apiUrl, params, handler);
	}

	// get user info about self
	public void getCurrentUser(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);
	}

	public void postTweet(String body, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", body);
		getClient().post(apiUrl, params, handler);
	}

	public void getMentionsTimeline(JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// specify params
		RequestParams params = new RequestParams();
		params.put("count", 25); // grab 25 tweets
		params.put("since_id", 1); // oldest id of tweet to grab
		//execute request
		getClient().get(apiUrl, params, handler);
	}

	public void getUserProfileInfo(Long uid, JsonHttpResponseHandler handler){
		String apiUrl = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("user_id", uid);
		getClient().get(apiUrl, params, handler);
	}

	public void getUserTimeline(Long uid, JsonHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("user_id", uid);
		params.put("count", 25); // grab 25 tweets
		params.put("since_id", 1); // oldest id of tweet to grab
		//execute request
		getClient().get(apiUrl, params, handler);
	}
    public void getTweetsAfterThisTweet(long myTweetId, long uid, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        // specify params
        RequestParams params = new RequestParams();
        params.put("user_id", uid); // whose timeline are we getting?
        params.put("count", 25); // grab 25 tweets
        params.put("max_id", myTweetId); // oldest id of tweet to grab
        //execute request
        getClient().get(apiUrl, params, handler);
    }

}