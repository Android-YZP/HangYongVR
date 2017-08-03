package com.hy.vrfrog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by m1762 on 2017/6/12.
 */

public class StringUtils {

    public static boolean isPhone(String value) {
        String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0,0-9])|(14[0,0-9])|(17[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.find();
    }
}
