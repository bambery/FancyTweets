# Project 4 - *FancyTweets*

**Fancy Tweets** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: 20? 30? hours spent in total

## Notes

I encountered a lot of problems during this assignment, but I finally got 99% of the required stories done. Infinite scrolling works.... until the user returns from posting a new tweet. Then the home timeline will no longer infinitely paginate. I probably spent 5 or 6 hours trying to figure out why with no luck. Something about the way fragments, ViewPager, and activities work I couldn't make communicate properly. I will talk to a TA later to resolve it. There were many moving pieces and complex concepts that I found particularly challenging in this assignment. At some point I managed to double install the app on my emulator and was having bizarre bugs! Didn't even know that was possible. Uninstalling and reinstalling fixed that issue.

## User Stories

The following **required** functionality is completed:

* [x] The app includes **all required user stories** from Week 3 Twitter Client
* [x] User can **switch between Timeline and Mention views using tabs**
* [x] User can view their home timeline tweets.
* [x] User can view the recent mentions of their username.
* [x] User can navigate to **view their own profile**
* [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] User can **click on the profile image** in any tweet to see **another user's** profile.
* [x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
* [x] Profile view includes that user's timeline
* [x] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews) any of these timelines (home, mentions, user) by scrolling to the bottom

The following **optional** features are implemented:

* [ ] User can view following / followers list through the profile
* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [ ] User can **"reply" to any tweet on their home timeline**
* [ ] The user that wrote the original tweet is automatically "@" replied in compose
* [ ] User can click on a tweet to be **taken to a "detail view"** of that tweet
* [ ] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ ] Improve the user interface and theme the app to feel twitter branded
* [ ] User can **search for tweets matching a particular query** and see results

The following **bonus** features are implemented:

* [ ] User can view their direct messages (or send new ones)

  The following **additional** features are implemented:

  * [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough 

  Here's a walkthrough of implemented use
<img src='https://cloud.githubusercontent.com/assets/161639/11219999/70ee3cc4-8d12-11e5-8109-ab97b46f01fc.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
