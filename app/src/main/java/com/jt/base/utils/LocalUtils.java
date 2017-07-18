package com.jt.base.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jt.base.http.responsebean.SeeHistory;
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
        } else {
            mSearchHistory = new ArrayList<>();
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
        if (searchHistory.size() > 19) {
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


    /**
     * 讲历史数据储存本地
     */
    public static void addSeeHistory(Context context, SeeHistory seeHistory) {

        List<SeeHistory> searchHistory = getSeeHistory(context);

        if (searchHistory == null) {
            searchHistory = new ArrayList<>();
        }

        if (searchHistory.size() > 19) {
            searchHistory.remove(19);
        }

//        for (int i = 0; i < searchHistory.size(); i++) {
//            if (searchHistory.get(i).getName().equals(seeHistory.getName())) {//说明同一个视频
//                searchHistory.remove(searchHistory.get(i));
//            }
//        }

        searchHistory.add(0, seeHistory);

        SPUtil.put(context, VedioContants.See, new Gson().toJson(searchHistory));
    }

    /**
     * 删除历史数据储存本地
     */
    public static void deleteSeeHistory(Context context, int position) {
        List<SeeHistory> searchHistory = getSeeHistory(context);
        searchHistory.remove(position);
        SPUtil.put(context, VedioContants.See, new Gson().toJson(searchHistory));
    }

    /**
     * 从搜索本地获取历史数据
     *
     * @return
     */
    public static List<SeeHistory> getSeeHistory(Context context) {
        List<SeeHistory> mSearchHistory = null;
        String SearchHistory = (String) SPUtil.get(context, VedioContants.See, "");
        if (!TextUtils.isEmpty(SearchHistory)) {
            mSearchHistory = new Gson().fromJson(SearchHistory, new TypeToken<List<SeeHistory>>() {
            }.getType());
        } else {
            mSearchHistory = new ArrayList<>();
        }
        return mSearchHistory;
    }
}
