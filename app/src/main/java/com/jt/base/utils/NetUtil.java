package com.jt.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by m1762 on 2017/6/16.
 */

public class NetUtil {

    /**
     * 对网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) UIUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            //2.获取当前网络连接的类型信息
            int networkType = networkInfo.getType();
            if (ConnectivityManager.TYPE_WIFI == networkType) {

            } else if (ConnectivityManager.TYPE_MOBILE == networkType) {

            }
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }
}
