package com.jt.base.utils;

import java.util.Locale;

/**
 * Created by Smith on 2017/7/17.
 */

public class TimeUtils  {

    public static String generateTime(int totalSeconds) {

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }

}
