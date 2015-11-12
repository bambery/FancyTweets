package com.codepath.apps.fancytweets.models;

public class TimeFormatter {
    final static Integer SECONDS_IN_MINUTE = 60;
    final static Integer SECONDS_IN_HOUR = 3600;
    final static Integer SECONDS_IN_DAY = 86400;

    public String format(Long duration) {
        long inSeconds = (long) duration/1000;
        if (inSeconds < (SECONDS_IN_MINUTE - 1)) {
            return inSeconds + "s";
        } else if (inSeconds < (SECONDS_IN_HOUR - 1)) {
            return (inSeconds / SECONDS_IN_MINUTE) + "m";
        } else if (inSeconds < SECONDS_IN_DAY - 1) {
            return (inSeconds / SECONDS_IN_HOUR) + "h";
        } else {
            // TODO: handle posts from the distant past slightly better with dates if it's more than ~7 days
            return (inSeconds / SECONDS_IN_DAY) + "d";
        }
    }
}
