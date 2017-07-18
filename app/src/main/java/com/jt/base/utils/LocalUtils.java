package com.jt.base.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jt.base.videoDetails.VedioContants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smith on 2017/7/18.
 */

public class LocalUtils {

    /**
     * 从搜索本地获取历史数据
     *
     * @return
     */
    public static List<String> getSearchHistory(Context context) {
        List<String> mSearchHistory = null;
        String SearchHistory = (String) SPUtil.get(context, VedioContants.SearchHistory, "");
        if (!TextUtils.isEmpty(SearchHistory)) {
            mSearchHistory = new Gson().fromJson(SearchHistory, new TypeToken<List<String>>() {
            }.getType());
        }
        return mSearchHistory;
    }

    /**
     * 讲历史数据储存本地
     */
    public static void addSearchHistory(Context context, String SearchHistory) {
        List<String> searchHistory = getSearchHistory(context);
        if (searchHistory == null) {
            searchHistory = new ArrayList<>();
        }
        if (searchHistory.size() > 20) {
            searchHistory.remove(19);
        }
        searchHistory.add(0, SearchHistory);
        SPUtil.put(context, VedioContants.SearchHistory, new Gson().toJson(searchHistory));
    }


    /**
     * 删除历史数据储存本地
     */
    public static void deleteSearchHistory(Context context, int position) {
        List<String> searchHistory = getSearchHistory(context);
        searchHistory.remove(position);
        SPUtil.put(context, VedioContants.SearchHistory, new Gson().toJson(searchHistory));
    }

    /**
     * 删除历史数据储存本地
     */
    public static void deleteAllSearchHistory(Context context) {
        SPUtil.put(context, VedioContants.SearchHistory, "");
    }


}
